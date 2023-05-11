package com.example.CrtDgn.Login.Controller;

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

    // 로그인된 사용자 정보를 저장할 Set 컬렉션


    @PostMapping("/signUp")
    @ResponseBody
    public String signup(@RequestBody MemberDto request) {
        log.info("{}",request);
        log.info("userEmail = {}, password = {},passwordCheck = {}", request.getEmail(), request.getPassword(),request.getCheckPassword());
        if(memberService.signup(request).equals("Success")) {
            return "success";
        }
        return "fail";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody MemberDto request, HttpSession session) {
        log.info("userEmail = {}, password = {}", request.getEmail(), request.getPassword());

        // 이미 로그인된 경우 기존 세션을 무효화하고 제거
        String currentUserEmail = (String) session.getAttribute("userEmail");
        if (currentUserEmail != null && !currentUserEmail.equals(request.getEmail())) {
            session.invalidate();
            memberService.removeLoggedInUser(currentUserEmail);
        }

        if (memberService.login(request).equals("Success")) {
            log.info("email = {}", request.getEmail());

            // 중복 로그인 체크
            if (memberService.isLoggedIn(request.getEmail())) {
                log.info("userEmail = {} / 이미 로그인되어 있습니다.", request.getEmail());
                return "fail"; // 이미 로그인된 사용자인 경우 로그인 실패
            }


            // 로그인 성공한 경우 세션에 사용자 정보 저장
            session.setMaxInactiveInterval(30 * 60);
            session.setAttribute("platform", "none");
            session.setAttribute("userEmail", request.getEmail());
            session.setAttribute("access_Token", "none");


            // 로그인된 사용자 정보 추가
            memberService.addLoggedInUser(request.getEmail());

            return "success";
        }

        return "fail";
    }

    @PostMapping("/join")
    public String join(@Valid @RequestBody MemberDto memberDto) {
        if (memberService.join(memberDto)){
            return "success";
        }
        return "fail";
    }

    // 로그인 API
    @PostMapping("/login2")
    public String login(@RequestBody MemberDto memberDto) {
        return memberService.login2(memberDto);
    }


/*    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String logout(HttpSession session) {
        // 로그인된 사용자인지 확인
        String userEmail = (String) session.getAttribute("userEmail");
        if (memberService.isLoggedIn(userEmail)) {
            memberService.removeLoggedInUser(userEmail); // 로그인된 사용자 정보 제거
            session.removeAttribute("userEmail"); // "userEmail" 속성 제거
            session.invalidate(); // 세션 무효화
            log.info("로그아웃 성공");
            return "success"; // 로그아웃 성공
        }
        log.info("로그아웃 실패");
        return "fail"; // 로그아웃 실패 (로그인되지 않은 사용자)
    }*/

}
