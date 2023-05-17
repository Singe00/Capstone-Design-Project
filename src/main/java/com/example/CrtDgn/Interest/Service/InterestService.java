package com.example.CrtDgn.Interest.Service;


import com.example.CrtDgn.Interest.Dto.InterestDto;
import com.example.CrtDgn.Interest.Domain.Interest;
import com.example.CrtDgn.Interest.Repository.InterestRepository;
import com.example.CrtDgn.Login.Domain.Member;
import com.example.CrtDgn.Login.Repository.MemberRepository;
import com.example.CrtDgn.Search.Domain.Search;
import com.example.CrtDgn.Search.Domain.Search2;
import com.example.CrtDgn.Search.Repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class InterestService {
    @Autowired
    private final InterestRepository interestRepository;

    @Autowired
    private final SearchRepository searchRepository;

    @Autowired
    private final MemberRepository memberRepository;


    Interest interest = new Interest();

    public String addInterest(InterestDto request) {
        Optional<Member> member = memberRepository.findByEmail(request.getEmail());

        if (interestRepository.findByUserIdAndTourkey(member.get().getId(),request.getTourid()).isPresent())
        {
            log.info("이미 목록에 있는 종목입니다.");
            return "Fail";
        }
        else {
            interest.setId(null);
            interest.setUserId(member.get().getId());
            interest.setTourkey(request.getTourid());

            interestRepository.save(interest);

            return "Success";
        }
    }

    public String deleteInterest(InterestDto request) {
        Optional<Member> member = memberRepository.findByEmail(request.getEmail());
        Optional<Interest> inter = interestRepository.findByUserIdAndTourkey(member.get().getId(),request.getTourid());

        if (inter.isPresent())
        {
            interestRepository.deleteById(inter.get().getId());

            return "Success";
        }
        log.info("목록에 없는 종목입니다.");
        return "Fail";
    }

    public List<Search2> returnInterest(String email) {
        Member member = memberRepository.findMByEmail(email);

        List<Long> tourKey = interestRepository.findAllByUserId(member.getId()).stream()
                .map(Interest::getTourkey).toList();

        List<Object[]> result = searchRepository.getTourWithInterestByTourIds(member.getId(), tourKey);
        List<Search2> interestList = new ArrayList<>();

        for (Object[] row : result) {
            Search2 search = Search2.builder()
                    .tourId((Long) row[0])
                    .title((String) row[1])
                    .roadaddress((String) row[2])
                    .latitude((double) row[3])
                    .longitude((double) row[4])
                    .phoneno((String) row[5])
                    .tag((String) row[6])
                    .introduction((String) row[7])
                    .imagepath((String) row[8])
                    .interested(((Integer) row[9]).toString()) // 관심 여부를 문자열로 변환하여 설정
                    .build();
            interestList.add(search);
        }

        return interestList;

    }


/*    public List<Long> returnInterest2(InterestDto request) {
        Optional<Member> member = memberRepository.findByEmail(request.getEmail());

        List<Interest> inters = interestRepository.findAllByUserId(member.get().getId());
        List<Long> interList = new ArrayList<>();

        for(Interest inter : inters){
            Long interestDto = inter.getTourkey();

            interList.add(interestDto);
        }
        log.info("{}",interList);
        return interList;
    }*/
}