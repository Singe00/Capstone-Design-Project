package com.example.CrtDgn.Login.Service;

import com.example.CrtDgn.Login.Dto.MemberDto;
import com.example.CrtDgn.Login.Domain.Member;
import com.example.CrtDgn.Login.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;



    public String signup(MemberDto request){
        Member member = new Member();
        memberRepository.findByEmail(request.getEmail())
                .ifPresent(m->{
                    throw new IllegalStateException("이미 존재하는 아이디입니다.");
                });

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


}
