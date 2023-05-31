package com.example.CrtDgn.Security.Jwt;

import com.example.CrtDgn.Search.Domain.Charge;
import com.example.CrtDgn.Search.Dto.ChargeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class JwtController {

    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/tokenCheck")
    public String ChargePlace(@RequestBody JwtDto jwtDto){
        System.out.println("=====토큰 검사 요청=====");
        if (!jwtTokenProvider.validateToken(jwtDto.getLogout())){
            System.out.println("토큰이 만료되었습니다.");
            return "fail";
        }
        else {
            System.out.println("=====토큰 검사 완료=====");
            return "success";
        }
    }
}
