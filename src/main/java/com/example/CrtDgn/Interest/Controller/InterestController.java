package com.example.CrtDgn.Interest.Controller;

import com.example.CrtDgn.Interest.Dto.InterestDto;
import com.example.CrtDgn.Interest.Service.InterestService;
import com.example.CrtDgn.Search.Domain.Search;
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

    @PostMapping("/add")
    @ResponseBody
    public String addInterest(@RequestBody InterestDto request) {
        System.out.println("관심 추가 요청");
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
    public String returnInterest(@RequestBody InterestDto request) {
        System.out.println("관심 검사 요청");
        if (interestService.returnInterest(request)){
            return "success";
        }
        else {
            return "fail";
        }
    }

    @PostMapping("/check")
    @ResponseBody
    public String checkInterest(@RequestBody InterestDto request) {
        System.out.println("관심 확인 요청");
        String result = interestService.deleteInterest(request);

        if (result.equals("Success"))
        {
            return "success";
        }
        return "fail";
    }

/*    @PostMapping("/returnInterest2")
    @ResponseBody
    public List<String> returnInterest2(@RequestBody InterestDto request) {
        List<String> result = interestService.returnInterest2(request);

        return result;
    }*/
}
