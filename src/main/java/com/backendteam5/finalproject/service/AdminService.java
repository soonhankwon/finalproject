package com.backendteam5.finalproject.service;

import com.backendteam5.finalproject.dto.*;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.entity.UserRoleEnum;
import com.backendteam5.finalproject.repository.*;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final AccountRepository accountRepository;
    private final CourierRepository courierRepository;
    private final DeliveryAssignmentRepository deliveryAssignmentRepository;
    private final String defaultPerson = "GUROADMIN";

    public AdminCountDto getMainReport(UserDetailsImpl userDetails){
        String area = checkAdmin(userDetails);

        List<String> userList = accountRepository.findByAreaAndRole(area, UserRoleEnum.USER);

        List<CountUserDto> tempDto = deliveryAssignmentRepository.findByTempCount(area, defaultPerson);

        List<CountUserDto> directDto = deliveryAssignmentRepository.findByDirectCount(area, defaultPerson);

        return new AdminCountDto(userList, tempDto, directDto);
    }

    public List<RouteCountDto> getRouteCount(UserDetailsImpl userDetails){
        String area = checkAdmin(userDetails);
        return courierRepository.countRouteState(area);
    }

    public List<DeliveryAssignmentDto> selectRoute(UserDetailsImpl userDetails, String route){
        return deliveryAssignmentRepository.selectDelivery(userDetails.getArea(), route);
    }

    public String updateDelivery(UserDetailsImpl userDetails, UpdateDeliveryDto updateDeliveryDto){
        checkAdmin(userDetails);

        List<Long> ids = updateDeliveryDto.getIds();
        List<String> username = updateDeliveryDto.getUsername();

        if(updateDeliveryDto.getUsername().size() != updateDeliveryDto.getIds().size()) throw  new RuntimeException("전달값에 이상이 있습니다.");

        HashMap<String, List<Long>> hashMap = new HashMap<>();

        for(String user : username.parallelStream().distinct().collect(Collectors.toList())){
            List<Integer> index = IntStream.range(0, username.size())
                    .filter(i -> Objects.equals(username.get(i), user))
                    .boxed().collect(Collectors.toList());
            hashMap.put(user, index.stream().map(ids::get).collect(Collectors.toList()));
        }

        if(hashMap.containsKey(""))   throw new NullPointerException("존재하지 않는 사용자 입니다." + hashMap.get(null));

        for(Map.Entry<String, List<Long>> pair : hashMap.entrySet()) {
            deliveryAssignmentRepository.updateDelivery(
                    pair.getValue(),
                    accountRepository.findIdByUsername(pair.getKey())
            );
        }
        return "업데이트 성공";
    }

    public List<AdminCourierDto> searchByDetails(UserDetailsImpl userDetails, SearchReqDto reqDto){
        String area = checkAdmin(userDetails);

        return courierRepository.searchByDetail(defaultPerson, area, reqDto);
    }

    public List<AdminCourierDto> searchByCouriers(List<Long> courierId) {
        return courierRepository.searchByCouriers(courierId);
    }

    public String setStateDelay(UserDetailsImpl userDetails, List<Long> couriers){
        checkAdmin(userDetails);

        courierRepository.setUpdateStateDelay(couriers);
        return "업데이트 성공";
    }

    public String setDeliveryPerson(UserDetailsImpl userDetails, SetDeliveryPersonReqDto reqDto){
        checkAdmin(userDetails);

        courierRepository.setDeliveryPerson(reqDto.getCouriers(), reqDto.getUsername());
        return "업데이트 성공";
    }

    public CourierResUpdateDto updateCourier(Long courierId, UserDetailsImpl userDetails,
                                             CourierReqUpdateDto courierReqUpdateDto) {
        courierReqUpdateDto.checkState();
        checkAdmin(userDetails);

        if(!accountRepository.existsByUsername(courierReqUpdateDto.getDeliveryPerson()))  throw new IllegalArgumentException("해당 유저는 없습니다.");

        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new NullPointerException("해당 운송장이 존재하지 않습니다"));
        courier.update(courierReqUpdateDto);
        courierRepository.save(courier);
        return new CourierResUpdateDto("운송장 할당완료");
    }

    public String checkAdmin(UserDetailsImpl userDetails){
        if(userDetails.getUser().getRole() == UserRoleEnum.USER)    throw new IllegalArgumentException("관리자가 아닙니다.");
        return userDetails.getArea();
    }

    public void updateArrivalDateAndDeliveryPerson(){
        courierRepository.setReady();
    }
}
