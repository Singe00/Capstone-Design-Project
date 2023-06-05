package com.example.CrtDgn.Security.Jwt;

import com.example.CrtDgn.Login.Domain.Member;
import com.example.CrtDgn.Login.Repository.MemberRepository;
import com.example.CrtDgn.Login.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
public class JwtCheck {

    @Autowired
    private JwtRepository jwtRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Scheduled(cron = "0 */15 * * * *") // 매 15분마다 토큰확인
    public void printCurrentTime() {
        System.out.println("====== 토큰 검사 시작 ======");
        List<JwtDomain> jwtL = jwtRepository.findAll();

        if (!jwtL.isEmpty()){
            for (JwtDomain jd : jwtL){
                if (!jwtTokenProvider.validateToken(jd.getToken())){
                    jwtRepository.delete(jd);

                }
            }
        }

        System.out.println("====== 토큰 검사 완료 ======");
    }
}
