package com.example.CrtDgn.Recommand.Controller;

import com.example.CrtDgn.Recommand.Dto.RecommandDto;
import com.example.CrtDgn.Recommand.Service.PredictionService;
import com.example.CrtDgn.Search.Domain.Search;
import com.example.CrtDgn.Search.Dto.ChargeDto;
import com.example.CrtDgn.Search.Repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class RecommandController {

    @Autowired
    private final PredictionService predictionService;

    @Autowired
    private final SearchRepository searchRepository;

    @PostMapping("/recommand")
    public List<Search> Recommand(@RequestBody List<RecommandDto> recommandDto) {

        List<String[]> predictData= predictionService.runPy(recommandDto.get(0).getBaseDate(),recommandDto.get(0).getBaseHour());
        predictionService.updateTraffic(predictData);

        return predictionService.recommandPlace();
    }
}