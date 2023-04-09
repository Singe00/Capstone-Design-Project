package com.example.CrtDgn.Login.Controller;

import com.example.CrtDgn.Login.Dto.MemberDto;
import com.example.CrtDgn.Login.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {
    @Autowired
    private final MemberService memberService;

    @PostMapping("/signUp")
    @ResponseBody
    public boolean signup(@RequestBody MemberDto request) {
        log.info("userEmail = {}, password = {},passwordCheck = {}", request.getEmail(), request.getPassword(),request.getCheckPassword());
        if(memberService.signup(request).equals("Success")) {
            return true;
        }
        return false;
    }

    @PostMapping("/login")
    @ResponseBody
    public boolean login(@RequestBody MemberDto request, HttpSession session) {
        log.info("userEmail = {}, password = {}", request.getEmail(), request.getPassword());
        if (memberService.login(request).equals("Success")){
            log.info("email={}",request.getEmail());
            return true;
        }
        return false;
    }
}
