package com.example.CrtDgn.Recommand.Repository;

import com.example.CrtDgn.Recommand.Domain.Road;
import com.example.CrtDgn.Search.Domain.Search3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoadRepository extends JpaRepository<Road,Long> {

    List<Road> findAllByRoadid(int roadid);

    List<Road> findAllByTraffic(int traffic);

    Road findByTid(int tid);

    @Query("SELECT t.tourId, t.title, t.roadaddress, t.latitude, t.longitude, t.phoneno, t.tag, t.introduction, t.imagepath, r.traffic " +
            "FROM Search3 t " +
            "JOIN Road r ON t.tourId = r.tid")
    List<Object[]> getTourWithTraffic();
}
