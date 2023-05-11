package com.example.CrtDgn.OauthLogin;

import com.example.CrtDgn.Login.Domain.Member;
import com.example.CrtDgn.Login.Repository.MemberRepository;
import com.example.CrtDgn.Login.Service.MemberService;
import com.example.CrtDgn.Security.Jwt.JwtDomain;
import com.example.CrtDgn.Security.Jwt.JwtDto;
import com.example.CrtDgn.Security.Jwt.JwtRepository;
import com.example.CrtDgn.Security.Jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private final MemberService memberService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private final JwtRepository jwtRepository;

    @Autowired
    private final OAuthService oAuthService;

    @ResponseBody
    @PostMapping("/kakao")
    public String kakaoCallback(@RequestBody OAuthDto access_Token) {
        HashMap<String, Object> userInfo = oAuthService.CreateKakaoUser(access_Token.getAccessToken());
        System.out.println("Kakao accessT : "+access_Token.getAccessToken());
        System.out.println("login Controller : " + userInfo);

        if (userInfo.get("email") != null) {
            String email = userInfo.get("email").toString();

            Member member = memberRepository.findMByEmail(email);

            if (member == null) {
                // 새로운 사용자인 경우 회원 정보 저장
                member = Member.builder()
                        .email(email)
                        .password(passwordEncoder.encode(access_Token.getAccessToken()))
                        .roles(Collections.singletonList("ROLE_USER"))
                        .build();
            } else {
                // 이미 등록된 사용자인 경우 Access Token 업데이트
                member.setPassword(passwordEncoder.encode(access_Token.getAccessToken()));
            }

            memberRepository.save(member);

            // 로그인에 성공하면 email, roles 로 토큰 생성 후 반환
            String token = jwtTokenProvider.createToken(member.getUsername(), member.getRoles(),"kakao",access_Token.getAccessToken());

            return token;
        }

        return "fail";
    }

    @ResponseBody
    @PostMapping("/naver")
    public String naverCallback(@RequestBody OAuthDto access_Token) {
        HashMap<String, Object> userInfo = oAuthService.CreateNaverUser(access_Token.getAccessToken());
        System.out.println("Naver accessT : "+access_Token.getAccessToken());
        System.out.println("login Controller : " + userInfo);

        if (userInfo.get("email") != null) {
            String email = userInfo.get("email").toString();

            Member member = memberRepository.findMByEmail(email);

            if (member == null) {
                // 새로운 사용자인 경우 회원 정보 저장
                member = Member.builder()
                        .email(email)
                        .password(passwordEncoder.encode(access_Token.getAccessToken()))
                        .roles(Collections.singletonList("ROLE_USER"))
                        .build();
            } else {
                // 이미 등록된 사용자인 경우 Access Token 업데이트
                member.setPassword(passwordEncoder.encode(access_Token.getAccessToken()));
            }

            memberRepository.save(member);

            // 로그인에 성공하면 email, roles로 토큰 생성 후 반환
            String token = jwtTokenProvider.createToken(member.getUsername(), member.getRoles(), "naver", access_Token.getAccessToken());

            return token;
        }

        return "fail";
    }

/*    @RequestMapping(value="/kakao/login")
    public Object kakaoLogin(@RequestParam("code") String code, HttpSession session) {
        String access_Token = oAuthService.getKakaoAccessToken(code);
        HashMap<String, Object> userInfo = oAuthService.CreateKakaoUser(access_Token);
        System.out.println("login Controller : " + userInfo);

        //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("access_Token", access_Token);
            session.setAttribute("platform", "kakao");
        }
        System.out.println(userInfo.get("email"));
        return userInfo.get("email");
    }

    @RequestMapping(value="/naver/login")
    public Object naverLogin(@RequestParam("code") String code, HttpSession session) {
        String access_Token = oAuthService.getNaverAccessToken(code);
        HashMap<String, Object> userInfo = oAuthService.CreateNaverUser(access_Token);
        System.out.println("login Controller : " + userInfo);

        //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("access_Token", access_Token);
            session.setAttribute("platform", "naver");
        }
        System.out.println(userInfo.get("email"));
        return userInfo.get("email");
    }*/

    @RequestMapping(value = "/logout")
    public String logout(@RequestBody Dto dto,HttpSession session) {

        String platform = (String) session.getAttribute("platform");
        System.out.println("session : "+session);
        System.out.println("platform : "+platform);

        if (platform == null || platform.equals("none")) {
            // 로그인된 사용자인지 확인
            String userEmail = (String) session.getAttribute("userEmail");
            if (memberService.isLoggedIn(userEmail)) {
                memberService.removeLoggedInUser(userEmail); // 로그인된 사용자 정보 제거
                session.removeAttribute("userEmail"); // "userEmail" 속성 제거
                session.invalidate(); // 세션 무효화
                System.out.println("일반 로그아웃 성공");
                return "success"; // 로그아웃 성공
            }
            System.out.println("일반 로그아웃 실패");
            return "fail"; // 로그아웃 실패 (로그인되지 않은 사용자)
        } else {
            if (platform.equals("kakao")) {
                oAuthService.kakaoLogout((String) session.getAttribute("access_Token"));
                System.out.println("카카오 로그아웃 성공");
            } else if (platform.equals("naver")) {
                oAuthService.naverLogout((String) session.getAttribute("access_Token"));
                System.out.println("네이버 로그아웃 성공");
            }
            session.removeAttribute("access_Token");
            session.removeAttribute("userEmail");
            session.invalidate();
            return "success";
        }
    }

    @RequestMapping(value = "/logout2")
    public String logout2(@RequestBody JwtDto jwtDto) {

        Long uid = jwtRepository.findByToken(jwtDto.getToken()).getUserId();
        Optional<Member> op = memberRepository.findById(uid);

        Claims claims = jwtTokenProvider.getClaims(jwtDto.getToken());

        String platform = claims.get("platform", String.class);
        String ac = claims.get("accesstoken", String.class);
        System.out.println("p : "+platform);
        System.out.println("ac : "+ac);
        if (platform.equals("default")) {
            // 로그인된 사용자인지 확인
            String userEmail = op.get().getEmail();
            if (memberService.isLoggedIn(userEmail)) {
                memberService.removeLoggedInUser(userEmail); // 로그인된 사용자 정보 제거
                System.out.println("일반 로그아웃 성공");
                return "success"; // 로그아웃 성공
            }
            else {
                System.out.println("일반 로그아웃 실패");
                return "fail"; // 로그아웃 실패 (로그인되지 않은 사용자)
            }
        }
        else {
            if (platform.equals("kakao")) {
                oAuthService.kakaoLogout(ac);
                System.out.println("카카오 로그아웃 성공");
                return "success";
            }
            else if (platform.equals("naver")) {
                oAuthService.naverLogout(ac);
                System.out.println("네이버 로그아웃 성공");
                return "success";
            }
            return "fail";
        }
    }

/*    @RequestMapping(value="/logout")
    public String logout(HttpSession session) {
        if (session.getAttribute("platform").equals("kakao"))
        {
            oAuthService.kakaoLogout((String)session.getAttribute("access_Token"));
        }
        else if (session.getAttribute("platform").equals("naver"))
        {
            oAuthService.naverLogout((String)session.getAttribute("access_Token"));
        }
        session.removeAttribute("access_Token");
        session.removeAttribute("userId");
        session.invalidate();
        return "success";
    }*/

}