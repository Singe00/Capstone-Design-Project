package com.example.CrtDgn.Search.Controller;

import com.example.CrtDgn.Search.Recommand.Domain.Road;
import com.example.CrtDgn.Search.Recommand.Repository.RoadRepository;
import com.example.CrtDgn.Search.Recommand.Service.PredictionService;
import com.example.CrtDgn.Search.Domain.Search;
import com.example.CrtDgn.Search.Domain.Search2;
import com.example.CrtDgn.Search.Dto.SearchDto;
import com.example.CrtDgn.Search.Dto.TagDto;
import com.example.CrtDgn.Search.Repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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
        System.out.println(searchDtoList.get(0).getTitle());
        String email = searchDtoList.get(0).getEmail();
        String title = searchDtoList.get(0).getTitle();
        String tag = searchDtoList.get(0).getTitle();
        String option = searchDtoList.get(0).getOption();

        List<Object[]> titleResult = searchRepository.getTourWithInterestByTitle(email, title);
        List<Object[]> tagResult = searchRepository.getTourWithInterestByTag(email, tag);

        List<Search2> searchList = new ArrayList<>();

        for (Object[] row : titleResult) {
            Search2 search = createSearchFromRow(row);
            searchList.add(search);
        }

        for (Object[] row : tagResult) {
            Search2 search = createSearchFromRow(row);

            boolean isDuplicate = searchList.stream()
                    .anyMatch(s -> s.getTitle().equals(search.getTitle()));

            if (!isDuplicate) {
                searchList.add(search);
            }
        }

        if (option.equals("1")){ //혼잡도 순 정렬
            // traffic 값을 기준으로 searchList 오름차순 정렬
            searchList.sort(Comparator.comparingInt(search -> getTrafficValueFromRoadTable(search.getTourId())));
        }
        else if (option.equals("2")){ //거리순 정렬
            double latitude = Double.parseDouble(searchDtoList.get(0).getLatitude());
            double longitude = Double.parseDouble(searchDtoList.get(0).getLongitude());

            // 정렬을 위해 Comparator를 생성하고 해당 Comparator를 활용하여 리스트를 정렬합니다.
            Collections.sort(searchList, (attraction1, attraction2) -> {
                double distance1 = calculateDistance(latitude, longitude, attraction1.getLatitude(), attraction1.getLongitude());
                double distance2 = calculateDistance(latitude, longitude, attraction2.getLatitude(), attraction2.getLongitude());
                return Double.compare(distance1, distance2);
            });
        }

        return searchList;
//        return searchRepository.findAllByTitleContaining(searchDtoList.get(0).getTitle());
    }

    private Search2 createSearchFromRow(Object[] row) {
        return Search2.builder()
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
    }

/*
    @PostMapping("/search/tag")
    public List<Search2> searchToursByTag(@RequestBody List<TagDto> searchDtoList) {
        System.out.println("관광지 검색 요청");
        String email = searchDtoList.get(0).getEmail();
        String tag = searchDtoList.get(0).getTag();
        String option = searchDtoList.get(0).getOption();

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

        if (option.equals("1")){ //혼잡도 순 정렬
            // traffic 값을 기준으로 searchList 오름차순 정렬
            searchList.sort(Comparator.comparingInt(search -> getTrafficValueFromRoadTable(search.getTourId())));
        }
        else if (option.equals("2")){ //거리순 정렬
            double latitude = Double.parseDouble(searchDtoList.get(0).getLatitude());
            double longitude = Double.parseDouble(searchDtoList.get(0).getLongitude());

            // 정렬을 위해 Comparator를 생성하고 해당 Comparator를 활용하여 리스트를 정렬합니다.
            Collections.sort(searchList, (attraction1, attraction2) -> {
                double distance1 = calculateDistance(latitude, longitude, attraction1.getLatitude(), attraction1.getLongitude());
                double distance2 = calculateDistance(latitude, longitude, attraction2.getLatitude(), attraction2.getLongitude());
                return Double.compare(distance1, distance2);
            });
        }

        return searchList;
//        return searchRepository.findAllByTitleContaining(searchDtoList.get(0).getTitle());
    }
*/

    private int getTrafficValueFromRoadTable(Long tourId) {
        // road 테이블에서 tourId를 사용하여 traffic 값을 가져오는 로직을 구현해야 함
        // 예시로 직접 작성된 코드이며, 실제 데이터 액세스 방식에 따라 적절히 변경되어야 함
        Road road = roadRepository.findByTid(Integer.parseInt(tourId.toString()));
        if (road != null) {
            return road.getTraffic();
        }
        return 0; // 또는 기본값 설정
    }

    private static final double EARTH_RADIUS = 6371.0; // 지구의 반지름 (단위: km)

    private double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        double lat1Rad = Math.toRadians(latitude1);
        double lon1Rad = Math.toRadians(longitude1);
        double lat2Rad = Math.toRadians(latitude2);
        double lon2Rad = Math.toRadians(longitude2);

        double dlon = lon2Rad - lon1Rad;
        double dlat = lat2Rad - lat1Rad;

        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_RADIUS * c; // 두 지점 사이의 거리 (단위: km)
        return distance;
    }

}
