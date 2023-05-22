package com.example.CrtDgn.Recommand.Controller;

import com.example.CrtDgn.Recommand.Domain.Road;
import com.example.CrtDgn.Recommand.Dto.RecommandDto;
import com.example.CrtDgn.Recommand.Repository.RoadRepository;
import com.example.CrtDgn.Recommand.Service.PredictionService;
import com.example.CrtDgn.Search.Domain.Search;
import com.example.CrtDgn.Search.Dto.ChargeDto;
import com.example.CrtDgn.Search.Repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class RecommandController {

    @Autowired
    private final PredictionService predictionService;

    @Autowired
    private final SearchRepository searchRepository;

    @Autowired
    private  final RoadRepository roadRepository;

    @PostMapping("/recommand")
    public List<Search> Recommand() {

        List<Road> roads = roadRepository.findByTrafficEquals(0);
        List<Integer> tourIds = roads.stream().map(Road::getTid).collect(Collectors.toList());

        return searchRepository.findAllById(tourIds);
    }
}