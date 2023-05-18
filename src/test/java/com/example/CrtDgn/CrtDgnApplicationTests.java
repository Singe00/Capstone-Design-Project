package com.example.CrtDgn;

import com.example.CrtDgn.Login.Dto.MemberDto;
import com.example.CrtDgn.Login.Service.MemberService;
import com.example.CrtDgn.Recommand.Service.PredictionService;
import com.example.CrtDgn.Search.Controller.SearchController;
import com.example.CrtDgn.Search.Domain.Search;
import com.example.CrtDgn.Search.Dto.SearchDto;
import com.example.CrtDgn.Search.Dto.TagDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
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

	@Test
	void SearchTitle(){
		List<SearchDto> searchDto = new ArrayList<>();
		SearchDto sd = new SearchDto();

		sd.setEmail("singery00@naver.com");
		sd.setTitle("숲");

		searchDto.add(sd);

		searchController.searchToursByTitle(searchDto);


	}

	@Test
	void SearchTag(){
		List<TagDto> tagDtos = new ArrayList<>();
		TagDto td = new TagDto();

		td.setEmail("singery00@naver.com");
		td.setTag("중국집");

		tagDtos.add(td);

		searchController.searchToursByTag(tagDtos);


	}

}


