package com.example.CrtDgn.Search.Controller;

import com.example.CrtDgn.Recommand.Domain.Road;
import com.example.CrtDgn.Recommand.Repository.RoadRepository;
import com.example.CrtDgn.Recommand.Service.PredictionService;
import com.example.CrtDgn.Search.Domain.Search;
import com.example.CrtDgn.Search.Domain.Search2;
import com.example.CrtDgn.Search.Dto.SearchDto;
import com.example.CrtDgn.Search.Dto.TagDto;
import com.example.CrtDgn.Search.Repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class SearchController {

    @Autowired
    private final SearchRepository searchRepository;

    @Autowired
    private final RoadRepository roadRepository;

    @Autowired
    private final PredictionService predictionService;

    @GetMapping("/main")
    public List<Search> BestPlace(){
        System.out.println("대표 관광지 정보 요청");
        List<Search> list = searchRepository.findRandomFiveTours();
        System.out.println(list);
        return list;
    }

    @GetMapping("/search/detail")
    public Search searchDetail(@RequestBody SearchDto searchDto){
        System.out.println("관광지 상세정보 요청");
        Search detail = searchRepository.findByTourid(searchDto.getTourKey().intValue());
        return detail;
    }

    @PostMapping("/search/title")
    public List<Search2> searchToursByTitle(@RequestBody List<SearchDto> searchDtoList) {
        System.out.println("관광지 검색 요청");
        String email = searchDtoList.get(0).getEmail();
        String title = searchDtoList.get(0).getTitle();

        List<Object[]> result = searchRepository.getTourWithInterestByTitle(email, title);
        List<Search2> searchList = new ArrayList<>();

        for (Object[] row : result) {
            Search2 search = Search2.builder()
                    .tourId((Long) row[0])
                    .title((String) row[1])
                    .roadaddress((String) row[2])
                    .latitude((double) row[3])
                    .longitude((double) row[4])
                    .phoneno((String) row[5])
                    .tag((String) row[6])
                    .introduction((String) row[7])
                    .imagepath((String) row[8])
                    .interested(((Integer) row[9]).toString()) // 관심 여부를 문자열로 변환하여 설정
                    .build();
            searchList.add(search);
        }

        // 현재 시간 가져오기
        LocalDateTime now = LocalDateTime.now();

        // 연월일 포맷 지정
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 시간 포맷 지정
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H");

        // 연월일과 시로 변환
        String date = now.format(dateFormatter);
        String hour = now.format(timeFormatter);

        List<String[]> predictData= predictionService.runPy(date,hour);

        predictionService.updateTraffic(predictData);

        // Join을 통해 road 테이블에서 traffic 값을 가져와서 정렬
        searchList.sort(Comparator.comparingInt(search -> getTrafficValueFromRoadTable(search.getTourId())));


        return searchList;
//        return searchRepository.findAllByTitleContaining(searchDtoList.get(0).getTitle());
    }

    @PostMapping("/search/tag")
    public List<Search2> searchToursByTag(@RequestBody List<TagDto> searchDtoList) {
        System.out.println("관광지 검색 요청");
        String email = searchDtoList.get(0).getEmail();
        String tag = searchDtoList.get(0).getTag();

        List<Object[]> result = searchRepository.getTourWithInterestByTag(email, tag);
        List<Search2> searchList = new ArrayList<>();

        for (Object[] row : result) {
            Search2 search = Search2.builder()
                    .tourId((Long) row[0])
                    .title((String) row[1])
                    .roadaddress((String) row[2])
                    .latitude((double) row[3])
                    .longitude((double) row[4])
                    .phoneno((String) row[5])
                    .tag((String) row[6])
                    .introduction((String) row[7])
                    .imagepath((String) row[8])
                    .interested(((Integer) row[9]).toString()) // 관심 여부를 문자열로 변환하여 설정
                    .build();
            searchList.add(search);
        }

        // 현재 시간 가져오기
        LocalDateTime now = LocalDateTime.now();

        // 연월일 포맷 지정
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 시간 포맷 지정
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H");

        // 연월일과 시로 변환
        String date = now.format(dateFormatter);
        String hour = now.format(timeFormatter);

        List<String[]> predictData= predictionService.runPy(date,hour);

        predictionService.updateTraffic(predictData);

        // Join을 통해 road 테이블에서 traffic 값을 가져와서 정렬
        searchList.sort(Comparator.comparingInt(search -> getTrafficValueFromRoadTable(search.getTourId())));

        return searchList;
//        return searchRepository.findAllByTitleContaining(searchDtoList.get(0).getTitle());
    }

    private int getTrafficValueFromRoadTable(Long tourId) {
        // road 테이블에서 tourId를 사용하여 traffic 값을 가져오는 로직을 구현해야 함
        // 예시로 직접 작성된 코드이며, 실제 데이터 액세스 방식에 따라 적절히 변경되어야 함
        Road road = roadRepository.findByTid(Integer.parseInt(tourId.toString()));
        if (road != null) {
            return road.getTraffic();
        }
        return 0; // 또는 기본값 설정
    }

}
