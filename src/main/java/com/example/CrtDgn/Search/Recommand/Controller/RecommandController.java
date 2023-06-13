package com.example.CrtDgn.Search.Recommand.Controller;

import com.example.CrtDgn.Search.Domain.Search;
import com.example.CrtDgn.Search.Recommand.Domain.Road;
import com.example.CrtDgn.Search.Recommand.Dto.RecommandDto;
import com.example.CrtDgn.Search.Recommand.Repository.RoadRepository;
import com.example.CrtDgn.Search.Domain.Search3;
import com.example.CrtDgn.Search.Recommand.Service.PredictionService;
import com.example.CrtDgn.Search.Repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class RecommandController {

    @Autowired
    private  final RoadRepository roadRepository;

    @Autowired
    private final PredictionService predictionService;

    @Autowired
    private final SearchRepository searchRepository;

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

        String title = recommandDto.get(0).getTitle();
        String baseDate = recommandDto.get(0).getBaseDate();
        String baseHour = recommandDto.get(0).getBaseHour();

        System.out.println(title);
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


        List<Search> sl1 = searchRepository.findAllByTitleContaining(title);
        List<Search> sl2 = searchRepository.findAllByTagContaining(title);

        List<Integer> idL = new ArrayList<>();

        for (Search s :sl1){
            idL.add(s.getTourid());
        }

        for (Search s :sl2){
            idL.add(s.getTourid());
        }

        // 중복 제거
        List<Integer> newList = idL.stream().distinct().collect(Collectors.toList());
        Set<Integer> newSet = new HashSet<>(newList);
        List<Search3> filteredTours = new ArrayList<>();

        for (Search3 tour : tours) {
            long tourId = tour.getTourId();
            if (newSet.contains((int) tourId)) {
                filteredTours.add(tour);
            }
        }

        return filteredTours;
    }
}