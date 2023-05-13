package com.example.CrtDgn.Login.Controller;

import com.example.CrtDgn.Login.Domain.Member;
import com.example.CrtDgn.Login.Dto.ChangeDto;
import com.example.CrtDgn.Login.Dto.MemberDto;
import com.example.CrtDgn.Login.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {
    @Autowired
    private final MemberService memberService;


    @PostMapping("/join")
    public String join(@Valid @RequestBody MemberDto memberDto) {
        System.out.println("회원가입 요청");
        if (memberService.join(memberDto)){
            return "success";
        }
        return "fail";
    }

    // 로그인 API
    @PostMapping("/login2")
    public String login(@RequestBody MemberDto memberDto) {
        System.out.println("로그인 요청");
        if (memberService.isLoggedIn(memberDto.getEmail()))
        {
            System.out.println("이미 로그인 되어 있습니다.");
            return "fail";
        }

        return memberService.login2(memberDto);
    }

    @PostMapping("/find")
    public String findPw(@RequestBody MemberDto memberDto) {
        System.out.println("임시 비번 발급 요청");

        String tempPw = memberService.generateTemporaryPassword();

        if (memberService.sendRandomPasswordByEmail(memberDto.getEmail(),tempPw))
        {
            System.out.println("메일이 발송되었습니다.");
            return "success";
        }
        return "fail1";
    }

    @PostMapping("/change")
    public String findPw(@RequestBody ChangeDto changeDto) {
        System.out.println("비번 변경 요청");
        log.info("userEmail = {}, password = {},newPassword = {},checkPassword = {}", changeDto.getEmail(), changeDto.getPassword(),changeDto.getNewPassword(),changeDto.getCheckPassword());
        String re = memberService.changePw(changeDto);
        System.out.println("re : "+re);
        if(re.equals("success")) {
            return "success";
        }
        else if (re.equals("fail1")){
            return "fail1";
        }
        else if (re.equals("fail2")){
            return "fail2";
        }
        else if (re.equals("fail3")){
            return "fail3";
        }
        else {
            return "fail";
        }

    }


}
