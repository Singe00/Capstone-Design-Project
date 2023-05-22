package com.example.CrtDgn.Recommand.Repository;

import com.example.CrtDgn.Recommand.Domain.Road;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoadRepository extends JpaRepository<Road,Long> {

    List<Road> findAllByRoadid(int roadid);

    List<Road> findAllByTraffic(int traffic);

    List<Road> findByTrafficEquals(int traffic);

    Road findByTid(int tid);
}
