package com.example.CrtDgn.Login.Service;

import com.example.CrtDgn.Login.Domain.Member;
import com.example.CrtDgn.Login.Dto.ChangeDto;
import com.example.CrtDgn.Login.Dto.MemberDto;
import com.example.CrtDgn.Login.Repository.MemberRepository;
import com.example.CrtDgn.Security.Jwt.JwtDomain;
import com.example.CrtDgn.Security.Jwt.JwtRepository;
import com.example.CrtDgn.Security.Jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final JwtRepository jwtRepository;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;


    private static Set<String> loggedInUsers = new HashSet<>();

    Member member = new Member();
    MemberDto memberDto = new MemberDto();

/*
    public String signup(MemberDto request){
        if (memberRepository.findByEmail(request.getEmail()).isPresent())
        {
            return "이미 존재하는 이메일입니다.";
        }
        if (request.getPassword().equals(request.getCheckPassword()))
        {
            member.setEmail(request.getEmail());
            member.setPassword(request.getPassword());
            memberRepository.save(member);

            return "Success";
        }
        else {
            return "비밀번호를 확인해주세요.";
        }
    }

    public String login(MemberDto request) {
        if (memberRepository.findByEmail(request.getEmail()).isPresent())
        {
            Optional<Member> member = memberRepository.findByEmail(request.getEmail());
            log.info("db password = {}, input password = {}",member.get().getPassword(),request.getPassword());
            if (member.get().getPassword().equals(request.getPassword())) {
                return "Success";
            }
            else{
                return "비밀번호가 일치하지 않습니다.";
            }
        }
        else {
            return "존재하지 않는 아이디입니다.";
        }
    }
*/

    public boolean isLoggedIn(String userEmail) {
        return loggedInUsers.contains(userEmail);
    }

    @Transactional
    public String join(MemberDto memberDto){
        Member m = memberRepository.findMByEmail(memberDto.getEmail());
        if (m != null){
            if (m.getPlatform() == "kakao"){
                return "fail2";
            } else if (m.getPlatform() == "naver") {
                return "fail3";
            }
            return "fail1";
        }

        Member member = Member.builder()
                .email(memberDto.getEmail())
                .password(passwordEncoder.encode(memberDto.getPassword()))  //비밀번호 인코딩
                .platform("default")
                .roles(Collections.singletonList("ROLE_USER"))         //roles는 최초 USER로 설정
                .build();
        memberRepository.save(member);
        return "success";
    }

    @Transactional
    public String login2(MemberDto memberDto){

        Member m =memberRepository.findMByEmail(memberDto.getEmail());
        JwtDomain jwtd = jwtRepository.findByUserId(m.getId());

        Member member = memberRepository.findByEmail(memberDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));

        if (!passwordEncoder.matches(memberDto.getPassword(), member.getPassword())) {
            System.out.println("잘못된 비밀번호입니다.");
            return "fail";
        }

        if (jwtd != null){
            if (jwtTokenProvider.validateToken(jwtd.getToken())){
                return jwtd.getToken();
            }
        }


        List<JwtDomain> jwtL = jwtRepository.findAllByUserId(member.getId());
        if (!jwtL.isEmpty()){
            for (JwtDomain jd : jwtL){
                if (!jwtTokenProvider.validateToken(jd.getToken())){
                    jwtRepository.delete(jd);
                }
            }
        }

        // 로그인에 성공하면 email, roles 로 토큰 생성 후 반환
        String token = jwtTokenProvider.createToken(member.getUsername(), member.getRoles(),"default","default");

        JwtDomain jwtDomain = JwtDomain.builder()
                .userId(member.getId())
                .token(token)
                .build();

        jwtRepository.save(jwtDomain);

        return token;
    }

    public String changePw(ChangeDto changeDto){

        // 회원 비밀번호 업데이트 로직
        Member member = memberRepository.findMByEmail(changeDto.getEmail());

        if (member==null){
            System.out.println("가입되지 않은 회원입니다!");
            return "fail1";
        }

        if (!passwordEncoder.matches(changeDto.getPassword(), member.getPassword())) {
            System.out.println("현재 비밀번호가 다릅니다!");
            return "fail2";
        }

        if (changeDto.getNewPassword().equals(changeDto.getCheckPassword()))
        {
            System.out.println("새 비번 : "+changeDto.getNewPassword());
            System.out.println("리 비번 : "+changeDto.getCheckPassword());
            // 이미 등록된 사용자인 경우 Access Token 업데이트
            member.setPassword(passwordEncoder.encode(changeDto.getNewPassword()));
            memberRepository.save(member);
            return "success";
        }
        else {
            System.out.println("비번 확인 필요!");
            return "fail3";
        }
    }

    public String generateTemporaryPassword() {
        String SPECIAL_CHARACTERS = "!@#$%^&*_";
        int passwordLength = 8;

        // 대문자, 소문자, 특수문자를 조합한 문자열 생성
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz" + SPECIAL_CHARACTERS;

        // 임시 비밀번호 생성
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        // 대문자 1개 추가
        char uppercaseLetter = getRandomCharacter("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        password.append(uppercaseLetter);

        // 나머지 문자 추가
        for (int i = 1; i < passwordLength; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            password.append(randomChar);
        }

        return password.toString();
    }

    private char getRandomCharacter(String characters) {
        Random random = new Random();
        return characters.charAt(random.nextInt(characters.length()));
    }

    public boolean sendRandomPasswordByEmail(String email, String temporaryPassword) {
        // 회원 비밀번호 업데이트 로직
        Member member = memberRepository.findMByEmail(email);

        if (member==null){
            System.out.println("가입되지 않은 회원입니다!");
            return false;
        }

        // 이메일 전송
        String subject = "제주앱 임시 비밀번호 안내";
        String body = "<html>"
                + "<body style=\"font-family: Arial, sans-serif; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">안녕하세요, 제주앱입니다.</h2>"
                + "<p style=\"margin-bottom: 20px;\">임시 비밀번호가 발급되었습니다. 로그인 후 비밀번호를 변경해주세요.</p>"
                + "<div style=\"background-color: #f2f2f2; padding: 10px;\">"
                + "<p style=\"font-weight: bold;\">임시 비밀번호: <span style=\"color: #ff0000;\">" + temporaryPassword + "</span></p>"
                + "</div>"
                + "<p style=\"margin-top: 20px;\">감사합니다.</p>"
                + "</body>"
                + "</html>";

        try {
            if (sendEmail(senderEmail, email, subject, body)){

                System.out.println("새로운 비밀번호 : "+temporaryPassword.toString());
                // 이미 등록된 사용자인 경우 Access Token 업데이트
                member.setPassword(passwordEncoder.encode(temporaryPassword.toString()));
                memberRepository.save(member);
                return true;
            }else{
                return false;
            }
        } catch (MessagingException e) {
            // 이메일 전송 실패 시 예외 처리
            e.printStackTrace();
            return false;
        }
    }

    private boolean sendEmail(String from, String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);

        try {
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
