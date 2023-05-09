package com.example.CrtDgn.OauthLogin;

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
    private final OAuthService oAuthService;
    /**
     * 카카오 callback
     * [GET] /oauth/kakao/callback
     */
    @ResponseBody
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code) {
        oAuthService.CreateKakaoUser(oAuthService.getKakaoAccessToken(code));
    }

    @ResponseBody
    @GetMapping("/naver")
    public void naverCallback(@RequestParam String code) {
        oAuthService.CreateNaverUser(oAuthService.getNaverAccessToken(code));
    }

    @RequestMapping(value="/kakao/login")
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
    }


    @RequestMapping(value="/logout")
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
    }

}