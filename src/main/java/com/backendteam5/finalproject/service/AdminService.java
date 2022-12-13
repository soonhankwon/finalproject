package com.backendteam5.finalproject.service;

import com.backendteam5.finalproject.dto.*;
import com.backendteam5.finalproject.entity.Account;
import com.backendteam5.finalproject.entity.Courier;
import com.backendteam5.finalproject.entity.UserRoleEnum;
import com.backendteam5.finalproject.repository.AccountRepository;
import com.backendteam5.finalproject.repository.CourierRepository;
import com.backendteam5.finalproject.repository.DeliveryAssignmentRepository;
import com.backendteam5.finalproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final AccountRepository accountRepository;
    private final CourierRepository courierRepository;
    private final DeliveryAssignmentRepository deliveryAssignmentRepository;
    private final String defaultPerson = "ADMIN";

    public AdminMainDto getMainReport(UserDetailsImpl userDetails){
        String area = checkAdmin(userDetails);

        AdminMainDto adminMainDto = new AdminMainDto();

        List<Account> userlist = accountRepository.findByAreaAndRoleOrderByUsernameAsc(area, UserRoleEnum.USER);
        adminMainDto.setUserlist(userlist);

        LinkedList<Long> tempCount = new LinkedList<>();
        LinkedList<CountStateDto> directCount = new LinkedList<>();

        LinkedList<CountStateDto> directCase = new LinkedList<>();

        directCase.add(new CountStateDto("배송지연", 0L));
        directCase.add(new CountStateDto("배송중", 0L));
        directCase.add(new CountStateDto("배송완료", 0L));

        for(Account deliveryuser : userlist){
            Long aLong = courierRepository.countUsernameTemp(deliveryuser);
            List<CountStateDto> countDirects = courierRepository.countUsernameDirect(deliveryuser);

            tempCount.add(aLong);

            if(countDirects.isEmpty())  countDirects.addAll(directCase);

            else if(countDirects.size() == 1){
                if(countDirects.get(0).getState().equals("배송지연")){
                    countDirects.add(directCase.get(1));
                    countDirects.add(directCase.get(2));
                } else if (countDirects.get(0).getState().equals("배송중")) {
                    countDirects.add(0, directCase.get(0));
                    countDirects.add(2, directCase.get(2));
                } else{
                    countDirects.add(0, directCase.get(0));
                    countDirects.add(1, directCase.get(1));
                }
            }

            else if(countDirects.size() == 2) {
                if(countDirects.get(0).getState().equals("배송지연") && countDirects.get(1).getState().equals("배송중"))   countDirects.add(directCase.get(2));
                if(countDirects.get(0).getState().equals("배송지연") && countDirects.get(1).getState().equals("배송완료"))   countDirects.add(1, directCase.get(1));
                if(countDirects.get(0).getState().equals("배송중") && countDirects.get(1).getState().equals("배송완료"))   countDirects.add(0, directCase.get(0));
            }

            directCount.addAll(countDirects);
        }
        adminMainDto.setTempAssignment(tempCount);
        adminMainDto.setDirectAssignment(directCount);

        adminMainDto.setRouteCount(courierRepository.countRouteState(userDetails.getArea()));

        return adminMainDto;
    }

    public List<DeliveryAssignmentDto> selectRoute(UserDetailsImpl userDetails, String route){
        return deliveryAssignmentRepository.selectDelivery(userDetails.getArea(), route);
    }

    public String updateDelivery(UserDetailsImpl userDetails, UpdateDeliveryDto updateDeliveryDto){
        checkAdmin(userDetails);
        List<String> zipCode = updateDeliveryDto.getZipCode();
        List<String> username = updateDeliveryDto.getUsername();
        if(updateDeliveryDto.getUsername().size() != updateDeliveryDto.getZipCode().size()) throw  new RuntimeException("전달값에 이상이 있습니다.");
        for(int i=0; i<updateDeliveryDto.getZipCode().size(); i++)  deliveryAssignmentRepository.updateDelivery(zipCode.get(i), username.get(i));

        return "업데이트 성공";
    }

    public List<AdminCourierDto> searchByDetails(UserDetailsImpl userDetails, SearchReqDto reqDto){
        String area = checkAdmin(userDetails);

        return courierRepository.searchByDetail(userDetails.getUsername(), area, reqDto);
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
