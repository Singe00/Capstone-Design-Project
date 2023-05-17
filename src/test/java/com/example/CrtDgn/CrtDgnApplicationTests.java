package com.example.CrtDgn;

import com.example.CrtDgn.Login.Dto.MemberDto;
import com.example.CrtDgn.Login.Service.MemberService;
import com.example.CrtDgn.Recommand.Service.PredictionService;
import com.example.CrtDgn.Search.Controller.SearchController;
import com.example.CrtDgn.Search.Domain.Search;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CrtDgnApplicationTests {

	@Autowired
	private MemberService memberService;

	@Autowired
	private SearchController searchController;

	@Autowired
	private PredictionService predictionService;



	@Test
	void Predict(){
        List<String[]> predictData= predictionService.runPy("20230517","5");

		predictionService.updateTraffic(predictData);


	}


}


