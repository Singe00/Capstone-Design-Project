package com.example.CrtDgn.Search.Controller;

import com.example.CrtDgn.Search.Domain.Search;
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
        List<Search> list = searchRepository.findFirst5ByOrderByScoreDesc();
        System.out.println(list);
        return list;
    }

    @GetMapping("/search/detail")
    public Search searchDetail(@RequestBody SearchDto searchDto){
        Search detail = searchRepository.findByTourid(searchDto.getTourKey());
        return detail;
    }

    @GetMapping("/search/title")
    public Page<Search> searchToursByTitle(@RequestBody SearchDto searchDto){
        PageRequest pageRequest = PageRequest.of(0,Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, "title"));
        return searchRepository.findAllByTitleContaining(searchDto.getTitle(),pageRequest);
    }


}
