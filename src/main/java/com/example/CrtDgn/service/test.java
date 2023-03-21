package com.example.CrtDgn.service;

import com.example.CrtDgn.Demain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class test {
    @PostMapping("/signUp")
    @ResponseBody
    public boolean signup(@RequestBody Member request) {
        log.info("id = {}, name = {}, email = {}, pw = {}"
                ,request.getId(),request.getName(),request.getEmail(),request.getPw());
        return true;
    }
}

