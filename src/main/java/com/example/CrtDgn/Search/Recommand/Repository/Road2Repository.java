package com.example.CrtDgn.Search.Recommand.Repository;

import com.example.CrtDgn.Search.Domain.Charge;
import com.example.CrtDgn.Search.Domain.Charge2;
import com.example.CrtDgn.Search.Recommand.Domain.Road2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Road2Repository extends JpaRepository<Road2,Long> {
    List<Road2> findAllByRoadid(int roadid);

    @Query("SELECT c.chargeid, c.chargingplace, c.chargingplacedetail, c.address,c.latitude, c.longitude, c.chargingflag, c.quickchargingflag,c.chargercount,c.quickchargercount,c.parkingfeeflag, r.traffic " +
            "FROM Charge2 c " +
            "JOIN Road2 r ON c.chargeid = r.cid")
    List<Object[]> getChargeWithTraffic();
}
