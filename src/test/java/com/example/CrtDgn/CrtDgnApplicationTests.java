package com.example.CrtDgn;

import com.example.CrtDgn.Dto.MemberDto;
import com.example.CrtDgn.Service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CrtDgnApplicationTests {

	@Autowired
	private MemberService memberService;


	@Test
	void signup() {
		MemberDto request1 = new MemberDto();

		request1.setName("asdasdsa");
		request1.setEmail("idid");
		request1.setPassword("asdasd");
		request1.setCheckPassword("asdasd");

		memberService.signup(request1);
	}


}
