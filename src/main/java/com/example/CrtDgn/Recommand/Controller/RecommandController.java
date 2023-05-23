package com.example.CrtDgn.Recommand.Controller;

import com.example.CrtDgn.Recommand.Domain.Road;
import com.example.CrtDgn.Recommand.Dto.RecommandDto;
import com.example.CrtDgn.Recommand.Repository.RoadRepository;
import com.example.CrtDgn.Recommand.Service.PredictionService;
import com.example.CrtDgn.Search.Domain.Search;
import com.example.CrtDgn.Search.Domain.Search2;
import com.example.CrtDgn.Search.Domain.Search3;
import com.example.CrtDgn.Search.Dto.ChargeDto;
import com.example.CrtDgn.Search.Repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class RecommandController {

    @Autowired
    private  final RoadRepository roadRepository;

    @PostMapping("/recommand")
    public List<Search3> Recommand() {

        List<Search3> tours = new ArrayList<>();

        List<Object[]> result = roadRepository.getTourWithTraffic();

        for (Object[] row : result) {
            Search3 search = Search3.builder()
                    .tourId((Long) row[0])
                    .title((String) row[1])
                    .roadaddress((String) row[2])
                    .latitude((double) row[3])
                    .longitude((double) row[4])
                    .phoneno((String) row[5])
                    .tag((String) row[6])
                    .introduction((String) row[7])
                    .imagepath((String) row[8])
                    .traffic(((Integer) row[9]).toString()) // 관심 여부를 문자열로 변환하여 설정
                    .build();
            tours.add(search);
        }

        // traffic 값을 기준으로 정렬
        tours.sort(Comparator.comparingInt(s -> Integer.parseInt(s.getTraffic())));


        return tours;
    }
}