package com.example.CrtDgn.Search.Recommand.Domain;

import com.example.CrtDgn.Search.Domain.Search;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "road")
@Getter
@Setter
@Entity
@Builder
public class Road {

    @Column(name = "roadid")
    private int roadid;

    @Id
    @Column(name = "tid")
    private int tid;

    @Column(name = "traffic")
    private int traffic;


    public int getRoadid() {
        return roadid;
    }

    public void setRoadid(int roadid) {
        this.roadid = roadid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getTraffic() {
        return traffic;
    }

    public void setTraffic(int traffic) {
        this.traffic = traffic;
    }
}
