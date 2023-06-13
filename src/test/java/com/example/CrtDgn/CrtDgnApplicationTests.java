package com.example.CrtDgn;

import com.example.CrtDgn.Login.Service.MemberService;
import com.example.CrtDgn.Search.Controller.ChargeController;
import com.example.CrtDgn.Search.Domain.Charge;
import com.example.CrtDgn.Search.Domain.Charge2;
import com.example.CrtDgn.Search.Domain.Search;
import com.example.CrtDgn.Search.Domain.Search2;
import com.example.CrtDgn.Search.Dto.ChargeDto;
import com.example.CrtDgn.Search.Recommand.Domain.Road;
import com.example.CrtDgn.Search.Recommand.Domain.Road2;
import com.example.CrtDgn.Search.Recommand.Repository.Road2Repository;
import com.example.CrtDgn.Search.Recommand.Service.PredictionService;
import com.example.CrtDgn.Search.Controller.SearchController;
import com.example.CrtDgn.Search.Dto.SearchDto;
import com.example.CrtDgn.Search.Dto.TagDto;
import com.example.CrtDgn.Search.Repository.ChargeRepository;
import com.example.CrtDgn.Search.Repository.SearchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

	@Autowired
	private ChargeRepository chargeRepository;

	@Autowired
	private ChargeController chargeController;


	@Autowired
	private Road2Repository road2Repository;

	@Autowired
	private SearchRepository searchRepository;
	@Test
	void t (){


	}

}