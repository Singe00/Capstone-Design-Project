package com.example.CrtDgn.Login.Controller;

import com.example.CrtDgn.Login.Dto.MemberDto;
import com.example.CrtDgn.Login.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {
    @Autowired
    private final MemberService memberService;

    // 로그인된 사용자 정보를 저장할 Set 컬렉션
    private static Set<String> loggedInUsers = new HashSet<>();

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

        // 이미 로그인된 경우 기존 세션을 무효화하고 제거
        String currentUserEmail = (String) session.getAttribute("userEmail");
        if (currentUserEmail != null && !currentUserEmail.equals(request.getEmail())) {
            session.invalidate();
            removeLoggedInUser(currentUserEmail);
        }

        if (memberService.login(request).equals("Success")) {
            log.info("email = {}", request.getEmail());

            // 중복 로그인 체크
            if (isLoggedIn(request.getEmail())) {
                log.info("userEmail = {} / 이미 로그인되어 있습니다.", request.getEmail());
                return false; // 이미 로그인된 사용자인 경우 로그인 실패
            }

            // 로그인 성공한 경우 세션에 사용자 정보 저장
            session.setAttribute("userEmail", request.getEmail());

            // 로그인된 사용자 정보 추가
            addLoggedInUser(request.getEmail());

            return true;
        }

        return false;
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public boolean logout(HttpSession session) {
        // 로그인된 사용자인지 확인
        String userEmail = (String) session.getAttribute("userEmail");
        if (isLoggedIn(userEmail)) {
            removeLoggedInUser(userEmail); // 로그인된 사용자 정보 제거
            session.removeAttribute("userEmail"); // "userEmail" 속성 제거
            session.invalidate(); // 세션 무효화
            log.info("로그아웃 성공");
            return true; // 로그아웃 성공
        }
        log.info("로그아웃 실패");
        return false; // 로그아웃 실패 (로그인되지 않은 사용자)
    }

    // 사용자가 이미 로그인되어 있는지 확인하는 메소드
    private boolean isLoggedIn(String userEmail) {
        return loggedInUsers.contains(userEmail);
    }

    // 로그인된 사용자 정보를 추가하는 메소드
    private void addLoggedInUser(String userEmail) {
        loggedInUsers.add(userEmail);
    }

    // 로그인된 사용자 정보를 제거하는 메소드
    private void removeLoggedInUser(String userEmail) {
        loggedInUsers.remove(userEmail);
    }
}
