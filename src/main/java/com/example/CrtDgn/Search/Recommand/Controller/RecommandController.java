package com.example.CrtDgn.Search.Recommand.Controller;

import com.example.CrtDgn.Search.Recommand.Domain.Road;
import com.example.CrtDgn.Search.Recommand.Dto.RecommandDto;
import com.example.CrtDgn.Search.Recommand.Repository.RoadRepository;
import com.example.CrtDgn.Search.Domain.Search3;
import com.example.CrtDgn.Search.Recommand.Service.PredictionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class RecommandController {

    @Autowired
    private  final RoadRepository roadRepository;

    @Autowired
    private final PredictionService predictionService;

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

    @PostMapping("/recommand2")
    public List<Search3> Recommand2(@RequestBody List<RecommandDto> recommandDto) {

        String baseDate = recommandDto.get(0).getBaseDate();
        String baseHour = recommandDto.get(0).getBaseHour();

        System.out.println(baseDate);
        System.out.println(baseHour);

        List<String[]> predictData= predictionService.runPy(baseDate,baseHour);

        List<Search3> tours = new ArrayList<>();

        List<Object[]> result = roadRepository.getTourWithTraffic2();

        for (Object[] row : result) {
            for (int i = 1; i < predictData.size(); i++)
            {
                String[] data = predictData.get(i);

                if (data[0].equals(row[10].toString())){
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
                            .traffic(( data[3]))
                            .build();
                    tours.add(search);

                    break;
                }
            }
        }

        // traffic 값을 기준으로 정렬
        tours.sort(Comparator.comparingInt(s -> Integer.parseInt(s.getTraffic())));

        return tours;
    }
}