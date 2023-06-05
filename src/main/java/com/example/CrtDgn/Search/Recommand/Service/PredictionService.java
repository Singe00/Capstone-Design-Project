package com.example.CrtDgn.Search.Recommand.Service;

import com.example.CrtDgn.Search.Recommand.Domain.Road;
import com.example.CrtDgn.Search.Recommand.Domain.Road2;
import com.example.CrtDgn.Search.Recommand.Repository.Road2Repository;
import com.example.CrtDgn.Search.Recommand.Repository.RoadRepository;
import com.example.CrtDgn.Search.Domain.Search;
import com.example.CrtDgn.Search.Repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class PredictionService {

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private RoadRepository roadRepository;

    @Autowired
    private Road2Repository roadRepository2;

    public List<String[]> runPy(String base_date,String base_hour)  {
        System.out.println("Python Call");
        String[] command = new String[4];
        command[0] = "python";
        command[1] = "C:/Users/admin/Desktop/models/Demo4.py";
        command[2] = base_date;
        command[3] = base_hour;
        try {
            return execPython(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("예측값 생성 오류 발생!");
        return null;
    }


    public static List<String[]> execPython(String[] command) throws IOException, InterruptedException {
        CommandLine commandLine = CommandLine.parse(command[0]);
        for (int i = 1, n = command.length; i < n; i++) {
            commandLine.addArgument(command[i]);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        int result = executor.execute(commandLine);
        String outputString = outputStream.toString();

        File csvFile = ResourceUtils.getFile("file:C:/Users/admin/Desktop/models/output.csv");
        List<String> lines = Files.readAllLines(csvFile.toPath());
        List<String[]> data = new ArrayList<>();

        for (String line : lines) {
            String[] values = line.split(",");
            data.add(values);
        }

        return data;
    }


    public void findClosestTouristSpots(List<String[]> predictData) {

        List<Search> tourList = searchRepository.findAll();


        for (Search tour : tourList){

            Road closestRoad = null;
            double minDistance = Double.MAX_VALUE;


            double tourLa = tour.getLatitude();
            double tourLo = tour.getLongitude();

            for (int i = 1; i < predictData.size(); i++) {
                String[] data = predictData.get(i);

                double rlatitude = Double.parseDouble(data[1]);
                double rlongitude = Double.parseDouble(data[2]);

                double distance = calculateDistance(rlatitude,rlongitude,tourLa,tourLo);

                // 최소 거리 갱신
                if (distance <= minDistance) {
                    minDistance = distance;
                    Road road = new Road();

                    road.setRoadid(Integer.parseInt(data[0]));
                    road.setTid(tour.getTourid());
                    road.setTraffic(Integer.parseInt(data[3]));
                    closestRoad = road;
                }
            }

            if (closestRoad != null) {
                System.out.println(closestRoad.getTid()+"정보 저장중!");
                roadRepository.save(closestRoad);
            }

        }
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // 지구의 반지름 (단위: km)
        final double R = 6371.0;

        // 위도와 경도를 라디안 단위로 변환
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // 위도와 경도의 차이 계산
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        // Haversine 공식 적용
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 거리 계산 결과 (단위: km)
        double distance = R * c;

        return distance;
    }

    public void updateTraffic(List<String[]> predictData) {

        for (int i = 1; i < predictData.size(); i++)
        {
            String[] data = predictData.get(i);

            List<Road> roads = roadRepository.findAllByRoadid(Integer.parseInt(data[0]));
            List<Road2> road2s = roadRepository2.findAllByRoadid(Integer.parseInt(data[0]));

            if (roads != null){
                for (Road road : roads) {
                    road.setTraffic(Integer.parseInt(data[3]));
                    roadRepository.save(road);
                }

                for(Road2 road2:road2s){
                    road2.setTraffic(Integer.parseInt(data[3]));
                    roadRepository2.save(road2);
                }
            }

            if (road2s != null){
                for(Road2 road2:road2s){
                    road2.setTraffic(Integer.parseInt(data[3]));
                    roadRepository2.save(road2);
                }
            }

        }
    }

}