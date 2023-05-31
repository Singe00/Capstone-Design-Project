package com.example.CrtDgn.Search.Recommand;

import com.example.CrtDgn.Search.Recommand.Service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class UpdateCode {

    @Autowired
    private PredictionService predictionService;

    @Scheduled(cron = "0 0 * * * *") // 매시간 00분마다 혼잡도 업데이트
    public void printCurrentTime() {
        
        System.out.println("혼잡도 업데이트 중");
        
        // 현재 시간 가져오기
        LocalDateTime now = LocalDateTime.now();

        // 연월일 포맷 지정
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 시간 포맷 지정
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H");


        // 연월일과 시로 변환
        String date = now.format(dateFormatter);
        String hour = now.format(timeFormatter);
        System.out.println("입력값 : " + date+" / "+hour);
        List<String[]> predictData= predictionService.runPy(date,hour);

        predictionService.updateTraffic(predictData);
        System.out.println("혼잡도 업데이트 완료");
    }
}
