package com.example.CrtDgn.Controller;

import com.example.CrtDgn.Dto.MemberDto;
import com.example.CrtDgn.Service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        log.info("userEmail = {}, password = {}", request.getEmail(), request.getPassword());
        if(memberService.signup(request).equals("Success")) {
            return true;
        }
        return false;
    }

    @PostMapping("/login")
    @ResponseBody
    public boolean login(@RequestBody MemberDto request) {
        log.info("userEmail = {}, password = {}", request.getEmail(), request.getPassword());
        if (memberService.login(request).equals("Success")){
            return true;
        }
        return false;
    }

}
