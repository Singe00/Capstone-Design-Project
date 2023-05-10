package com.example.CrtDgn.OauthLogin;

import com.example.CrtDgn.Login.Service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private final MemberService memberService;

    @Autowired
    private final OAuthService oAuthService;

    @ResponseBody
    @PostMapping("/kakao")
    public String kakaoCallback(@RequestBody OAuthDto access_Token, HttpSession session) {
        System.out.println("Kakao accessT : "+access_Token.getAccessToken());
        HashMap<String, Object> userInfo = oAuthService.CreateKakaoUser(access_Token.getAccessToken());
        System.out.println("login Controller : " + userInfo);

        //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
            session.setAttribute("userEmail", userInfo.get("email"));
            session.setAttribute("access_Token", access_Token);
            session.setAttribute("platform", "kakao");
        }
        System.out.println("카카오 세션"+session.getAttribute("platform"));
        //return userInfo.get("email");
        if (userInfo.get("email") != null)
        {
            return "success";
        }
        return "fail";
    }

    @ResponseBody
    @PostMapping("/naver")
    public String naverCallback(@RequestBody OAuthDto access_Token, HttpSession session) {
        System.out.println("Naver accessT : "+access_Token.getAccessToken());
        HashMap<String, Object> userInfo = oAuthService.CreateNaverUser(access_Token.getAccessToken());
        System.out.println("login Controller : " + userInfo);

        //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
            session.setAttribute("userEmail", userInfo.get("email"));
            session.setAttribute("access_Token", access_Token);
            session.setAttribute("platform", "naver");
        }
        System.out.println("네이버 세션"+session.getAttribute("platform"));
        //return userInfo.get("email");
        if (userInfo.get("email") != null)
        {
            return "success";
        }
        return "fail";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        System.out.println("userEmail : "+session.getAttribute("userEmail"));
        System.out.println("access_Token : "+session.getAttribute("access_Token"));
        System.out.println("platform : "+session.getAttribute("platform"));

        String platform = (String) session.getAttribute("platform");
        if (platform == null || platform.equals("none")) {
            // 로그인된 사용자인지 확인
            String userEmail = (String) session.getAttribute("userEmail");
            if (memberService.isLoggedIn(userEmail)) {
                memberService.removeLoggedInUser(userEmail); // 로그인된 사용자 정보 제거
                session.removeAttribute("userEmail"); // "userEmail" 속성 제거
                session.invalidate(); // 세션 무효화
                System.out.println("로그아웃 성공");
                return "success"; // 로그아웃 성공
            }
            System.out.println("로그아웃 실패");
            return "fail"; // 로그아웃 실패 (로그인되지 않은 사용자)
        } else {
            if (platform.equals("kakao")) {
                oAuthService.kakaoLogout((String) session.getAttribute("access_Token"));
            } else if (platform.equals("naver")) {
                oAuthService.naverLogout((String) session.getAttribute("access_Token"));
            }
            session.removeAttribute("access_Token");
            session.removeAttribute("userEmail");
            session.invalidate();
            return "success";
        }
    }

/*    @RequestMapping(value = "/logout")
    public String logout(@RequestBody Dto dto,HttpSession session) {
        System.out.println(dto.getLogout());
        System.out.println("userEmail : "+session.getAttribute("userEmail"));
        System.out.println("access_Token : "+session.getAttribute("access_Token"));
        System.out.println("platform : "+session.getAttribute("platform"));

        if (dto.getLogout().equals("login")) {
            // 로그인된 사용자인지 확인
            String userEmail = (String) session.getAttribute("userEmail");
            if (memberService.isLoggedIn(userEmail)) {
                memberService.removeLoggedInUser(userEmail); // 로그인된 사용자 정보 제거

                session.removeAttribute("userEmail"); // "userEmail" 속성 제거
                session.removeAttribute("access_Token");
                session.removeAttribute("platform");

                session.invalidate(); // 세션 무효화
                System.out.println("일반 로그아웃 성공");
                return "success"; // 로그아웃 성공
            }
            System.out.println("일반 로그아웃 실패");
            return "fail"; // 로그아웃 실패 (로그인되지 않은 사용자)
        } else {
            if (dto.getLogout().equals("kakao")) {
                oAuthService.kakaoLogout((String) session.getAttribute("access_Token"));
                System.out.println("카카오 로그아웃 성공");
            } else if (dto.getLogout().equals("naver")) {
                oAuthService.naverLogout((String) session.getAttribute("access_Token"));
                System.out.println("네이버 로그아웃 성공");
            }
            session.removeAttribute("access_Token");
            session.removeAttribute("userEmail");
            session.removeAttribute("platform");
            session.invalidate();
            return "success";
        }
    }*/

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