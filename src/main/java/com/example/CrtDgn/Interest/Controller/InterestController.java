package com.example.CrtDgn.Interest.Controller;

import com.example.CrtDgn.Interest.Dto.InterestDto;
import com.example.CrtDgn.Interest.Service.InterestService;
import com.example.CrtDgn.Search.Domain.Search;
import com.example.CrtDgn.Search.Domain.Search2;
import com.example.CrtDgn.Security.Jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/interest")
public class InterestController {
    @Autowired
    private final InterestService interestService;

    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/add")
    @ResponseBody
    public String addInterest(@RequestBody InterestDto request) {

        System.out.println("관심 추가 요청");
        System.out.println("토큰ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ"+request.getToken());
        String platform = jwtTokenProvider.getPlatform(request.getToken());

        System.out.println("email : "+request.getEmail());
        System.out.println("tourid"+request.getTourid());

        String result = interestService.addInterest(request);
        if (result.equals("Success"))
        {
            return "success";
        }
        return "fail";
    }

    @PostMapping("/delete")
    @ResponseBody
    public String deleteInterest(@RequestBody InterestDto request) {
        System.out.println("관심 삭제 요청");
        System.out.println("토큰ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ"+request.getToken());
        System.out.println("email : "+request.getEmail());
        System.out.println("tourid"+request.getTourid());
        String result = interestService.deleteInterest(request);

        if (result.equals("Success"))
        {
            return "success";
        }
        return "fail";
    }

    @PostMapping("/return")
    @ResponseBody
    public List<Search2> returnInterest(@RequestBody List<InterestDto> request) {
        System.out.println("관심 리스트 요청");
        System.out.println("토큰ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ"+request.get(0).getToken());
        System.out.println("email : "+request.get(0).getEmail());
        List<Search2> rs = interestService.returnInterest(request.get(0));
        if (rs != null){
            return rs;
        }else {
            throw new RuntimeException("Failed to retrieve interest list.");
        }

    }



/*    @PostMapping("/returnInterest2")
    @ResponseBody
    public List<String> returnInterest2(@RequestBody InterestDto request) {
        List<String> result = interestService.returnInterest2(request);

        return result;
    }*/
}
