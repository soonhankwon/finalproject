//package com.backendteam5.finalproject.repository.custom;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//@SpringBootTest
//public class autoTest {
//    @Test
//    public void auto() {
////        1. 쿼리로 가져오기 서브라우트 물량, 난이도 계수
//        // 난이도 개수
//        double[] difficulty = { 0.9, 1.2, 1, 1.3, 1, 1, 1, 1.2, 1.1 };
//        // 서브라우트 물량 카운트
//        int[] count = { 120, 124, 126, 101, 120, 120, 120, 128, 140 };
//
//        // 난이도 * 서브라우트 물량
//        ArrayList<Integer> correctionValue = new ArrayList<>();
//
//
////        2. 프론트에서 데이터 받아오기 user, 희망 물량, 현재 배정받은 물량
//        // 배송기사의 희망 택배 물량
//        ArrayList<Integer> capacity = new ArrayList<>(Arrays.asList(150, 300, 150, 200, 300, 200, 130, 300));
//
//        for (int i = 0; i < count.length; i++) {correctionValue.add((int) (difficulty[i] * count[i]));}
//
//        /*
//            제약 사항
//                1. 난이도 * 서브라우트 물량 < 택배기사 희망물량
//                2. 택태기사 희망물량 > 난이도 * 서브라우트 물량 + 난이도 * 서브라우트 물량 -> 하나이상 가능
//
//            리턴 값 : 서브라우트 물량 카운트의 index에 맞춰서 택배기사의 username 리턴 것.
//         */
//        System.out.println("correctionValue = " + correctionValue);
//        System.out.println("capacity = " + capacity);
//
//        ArrayList<Integer> realCap = new ArrayList<>(capacity);
//
//
//        for (int i = 0; i < count.length;i++) {
//            if (count[i] == 0){
//                count[i] = -1;
//                continue;
//            }
//
//            capacity = new ArrayList<>(realCap);
//
//            int tempCapacity = correctionValue.get(i);
//            int nearestCapacity = 10000;// 평균을 넣고
//            boolean fin = false;
//
//            for (int j = 0; j < capacity.size(); j++) {
//                int number = capacity.get(j);
//                if (number > tempCapacity) {
//                    int div = number-tempCapacity;
//                    if (nearestCapacity > div) {
//                        nearestCapacity = div;
//                        count[i] = j;
//                        capacity.set(j, nearestCapacity);
//
//                        fin = true;
//                    }
//                }
//            }
//
//
//            if (!fin) {
//                count[i] = -1;
//                continue;
//            }
//
//            realCap.set(count[i], capacity.get(count[i]));
//        }
//
//        ArrayList<Integer> accountId = new ArrayList<>();
//        for (int i : count) {
//            accountId.add(i);
//        }
//        System.out.println("배정 택배기사 리스트 = " + accountId);
//    }
//}
