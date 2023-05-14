package com.example.CrtDgn.Search.Controller;

import com.example.CrtDgn.Search.Domain.Search;
import com.example.CrtDgn.Search.Domain.Search2;
import com.example.CrtDgn.Search.Dto.SearchDto;
import com.example.CrtDgn.Search.Repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class SearchController {

    @Autowired
    private final SearchRepository searchRepository;

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
        Search detail = searchRepository.findByTourid(searchDto.getTourKey());
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

        return searchList;
//        return searchRepository.findAllByTitleContaining(searchDtoList.get(0).getTitle());
    }

}
