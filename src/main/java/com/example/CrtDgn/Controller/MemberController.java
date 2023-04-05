package com.example.CrtDgn.Controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index() {
        log.info("home controller");
        return "APIExamNaverLogin";
    }

    @RequestMapping(value = "login/aouth2/code/naver",method = RequestMethod.GET)
    public String loginPOSTNaver(HttpSession session) {
        log.info("Callback Controller");
        return "callback";
    }
}
