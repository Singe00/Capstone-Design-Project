package com.example.CrtDgn.Search.Recommand.Domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "road2")
@Getter
@Setter
@Entity
@Builder
public class Road2 {
    @Column(name = "roadid")
    private int roadid;

    @Id
    @Column(name = "cid")
    private int cid;

    @Column(name = "traffic")
    private int traffic;

    public int getRoadid() {
        return roadid;
    }

    public void setRoadid(int roadid) {
        this.roadid = roadid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getTraffic() {
        return traffic;
    }

    public void setTraffic(int traffic) {
        this.traffic = traffic;
    }
}
