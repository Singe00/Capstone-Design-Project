package com.example.CrtDgn;

import com.example.CrtDgn.Login.Dto.MemberDto;
import com.example.CrtDgn.Login.Service.MemberService;
import com.example.CrtDgn.Search.Controller.SearchController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CrtDgnApplicationTests {

	@Autowired
	private MemberService memberService;

	@Autowired
	private SearchController searchController;


	@Test
	void signup() {
		MemberDto request1 = new MemberDto();

		request1.setEmail("testqefe");
		request1.setPassword("asdasd");
		request1.setCheckPassword("asdasd");

		memberService.signup(request1);
	}

	@Test
	void TourSearch(){

	}

}
