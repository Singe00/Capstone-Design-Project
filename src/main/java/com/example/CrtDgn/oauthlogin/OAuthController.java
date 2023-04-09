package com.example.CrtDgn.oauthlogin;

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


    /**
     * 카카오 callback
     * [GET] /oauth/kakao/callback
     */
    @ResponseBody
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code) {
        oAuthService.CreateKakaoUser(oAuthService.getKakaoAccessToken(code));
    }

    @RequestMapping(value="/login")
    public String login(@RequestParam("code") String code, HttpSession session) {
        String access_Token = oAuthService.getKakaoAccessToken(code);
        HashMap<String, Object> userInfo = oAuthService.CreateKakaoUser(access_Token);
        System.out.println("login Controller : " + userInfo);

        //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("access_Token", access_Token);
        }
        return "index";
    }

    @RequestMapping(value="/logout")
    public String logout(HttpSession session) {
        oAuthService.kakaoLogout((String)session.getAttribute("access_Token"));
        session.removeAttribute("access_Token");
        session.removeAttribute("userId");
        session.invalidate();
        return "index";
    }

}