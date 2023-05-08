package com.example.CrtDgn.Search.Controller;

import com.example.CrtDgn.Search.Domain.Charge;
import com.example.CrtDgn.Search.Repository.ChargeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ChargeController {

    @Autowired
    private final ChargeRepository chargeRepository;

    @GetMapping("/charge/coordinate")
    public List<Charge> ChargePlace(@RequestParam("latitude") double latitude,
                                    @RequestParam("longitude") double longitude){

        System.out.println(latitude);
        System.out.println(longitude);
        double[] distances = { 0.3, 0.6, 0.9, 1.2,1.5,1.8,2.1 }; // 주변 거리 범위 (단위: km)

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

        return nearbyChargeStations;
    }
}
