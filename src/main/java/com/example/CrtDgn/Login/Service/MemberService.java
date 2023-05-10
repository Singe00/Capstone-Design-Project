package com.example.CrtDgn.Login.Service;

import com.example.CrtDgn.Login.Domain.Member;
import com.example.CrtDgn.Login.Dto.MemberDto;
import com.example.CrtDgn.Login.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    @Autowired
    private final MemberRepository memberRepository;

    private static Set<String> loggedInUsers = new HashSet<>();

    Member member = new Member();
    MemberDto memberDto = new MemberDto();


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

    public boolean isLoggedIn(String userEmail) {
        return loggedInUsers.contains(userEmail);
    }

    // 로그인된 사용자 정보를 추가하는 메소드
    public void addLoggedInUser(String userEmail) {
        loggedInUsers.add(userEmail);
    }

    // 로그인된 사용자 정보를 제거하는 메소드
    public void removeLoggedInUser(String userEmail) {
        loggedInUsers.remove(userEmail);
    }

}
