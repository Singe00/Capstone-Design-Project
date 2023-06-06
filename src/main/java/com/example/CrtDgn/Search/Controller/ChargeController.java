package com.example.CrtDgn.Search.Controller;

import com.example.CrtDgn.Search.Domain.Charge;
import com.example.CrtDgn.Search.Domain.Charge2;
import com.example.CrtDgn.Search.Domain.Search3;
import com.example.CrtDgn.Search.Dto.ChargeDto;
import com.example.CrtDgn.Search.Recommand.Domain.Road2;
import com.example.CrtDgn.Search.Recommand.Repository.Road2Repository;
import com.example.CrtDgn.Search.Repository.ChargeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ChargeController {

    @Autowired
    private final ChargeRepository chargeRepository;

    @Autowired
    private final Road2Repository road2Repository;

    @PostMapping("/charge/coordinate")
    public List<Charge2> ChargePlace(@RequestBody List<ChargeDto> chargeDto){
        System.out.println("충전소 검색 요청");
        System.out.println(chargeDto.get(0).getLatitude());
        System.out.println(chargeDto.get(0).getLongitude());
/*        double latitude = chargeDto.get(0).getLatitude();
        double longitude = chargeDto.get(0).getLongitude();

        double[] distances = { 0.3, 0.6, 0.9, 1.2,1.5,1.8,2.1,355.0 }; // 주변 거리 범위 (단위: km)

        List<Charge> nearbyChargeStations = new ArrayList<>();

        for (double distance : distances) {
            // 현재 위치에서 인접한 충전소 검색을 위한 경계값 계산
            double minLatitude = latitude - (distance / 111.0); // 1도의 위도 차이는 약 111km
            double maxLatitude = latitude + (distance / 111.0);
            double minLongitude = longitude - (distance / (111.0 * Math.cos(Math.toRadians(latitude))));
            double maxLongitude = longitude + (distance / (111.0 * Math.cos(Math.toRadians(latitude))));

            // 리포지토리를 통해 인접한 충전소 검색
            List<Charge> chargeStations = chargeRepository
                    .findByLatitudeBetweenAndLongitudeBetween(minLatitude, maxLatitude, minLongitude, maxLongitude);

            if (!chargeStations.isEmpty()) {
                nearbyChargeStations.addAll(chargeStations);
                break; // 주변 충전소가 검색되면 반복문 종료
            }
        }*/

        List<Charge2> cl = new ArrayList<>();
/*        List<Charge2> cl2 = new ArrayList<>();*/
        List<Object[]> result = road2Repository.getChargeWithTraffic();

        for (Object[] row : result) {
            Charge2 charge2 = Charge2.builder()
                    .chargeid((Long) row[0])
                    .chargingplace((String) row[1])
                    .chargingplacedetail((String) row[2])
                    .address((String) row[3])
                    .latitude((double) row[4])
                    .longitude((double) row[5])
                    .chargingflag((String) row[6])
                    .quickchargingflag((String) row[7])
                    .chargercount((String) row[8])
                    .quickchargercount((String) row[9])
                    .parkingfeeflag((String) row[10])
                    .traffic(((Integer) row[11]).toString()) // 관심 여부를 문자열로 변환하여 설정
                    .build();
            cl.add(charge2);
        }

/*        for (Charge l1:nearbyChargeStations){
            for (Charge2 l2:cl){
                if (l1.getChargeid().equals(l2.getChargeid())){
                    cl2.add(l2);
                    break;
                }
            }
        }*/

        return cl;
    }

/*    @GetMapping("/charge/coordinate")
    public List<Charge2> ChargePlace(@RequestBody List<ChargeDto> chargeDto){
        System.out.println("충전소 검색 요청");
        System.out.println(chargeDto.get(0).getLatitude());
        System.out.println(chargeDto.get(0).getLongitude());
        double latitude = chargeDto.get(0).getLatitude();
        double longitude = chargeDto.get(0).getLongitude();

        double[] distances = { 0.3, 0.6, 0.9, 1.2,1.5,1.8,2.1,355.0 }; // 주변 거리 범위 (단위: km)

        List<Charge> nearbyChargeStations = new ArrayList<>();

        for (double distance : distances) {
            // 현재 위치에서 인접한 충전소 검색을 위한 경계값 계산
            double minLatitude = latitude - (distance / 111.0); // 1도의 위도 차이는 약 111km
            double maxLatitude = latitude + (distance / 111.0);
            double minLongitude = longitude - (distance / (111.0 * Math.cos(Math.toRadians(latitude))));
            double maxLongitude = longitude + (distance / (111.0 * Math.cos(Math.toRadians(latitude))));

            // 리포지토리를 통해 인접한 충전소 검색
            List<Charge> chargeStations = chargeRepository
                    .findByLatitudeBetweenAndLongitudeBetween(minLatitude, maxLatitude, minLongitude, maxLongitude);

            if (!chargeStations.isEmpty()) {
                nearbyChargeStations.addAll(chargeStations);
                break; // 주변 충전소가 검색되면 반복문 종료
            }
        }

        List<Charge2> cl = new ArrayList<>();
        List<Charge2> cl2 = new ArrayList<>();
        List<Object[]> result = road2Repository.getChargeWithTraffic();

        for (Object[] row : result) {
            Charge2 charge2 = Charge2.builder()
                    .chargeid((Long) row[0])
                    .chargingplace((String) row[1])
                    .chargingplacedetail((String) row[2])
                    .address((String) row[3])
                    .latitude((double) row[4])
                    .longitude((double) row[5])
                    .chargingflag((String) row[6])
                    .quickchargingflag((String) row[7])
                    .chargercount((String) row[8])
                    .quickchargercount((String) row[9])
                    .parkingfeeflag((String) row[10])
                    .traffic(((Integer) row[11]).toString()) // 관심 여부를 문자열로 변환하여 설정
                    .build();
            cl.add(charge2);
        }

        for (Charge l1:nearbyChargeStations){
            for (Charge2 l2:cl){
                if (l1.getChargeid().equals(l2.getChargeid())){
                    cl2.add(l2);
                    break;
                }
            }
        }

        return cl2;
    }*/
}
