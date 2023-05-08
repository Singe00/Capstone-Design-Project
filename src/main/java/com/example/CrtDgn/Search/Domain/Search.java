package com.example.CrtDgn.Search.Domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tour")
@Getter
@Setter
@Entity
@Builder
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tourid")
    private Long tourid;

    @Column(name = "content")
    private String content;

    @Column(name = "title")
    private String title;

    @Column(name = "address")
    private String address;

    @Column(name = "roadaddress")
    private String roadaddress;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "score")
    private String score;

    @Column(name = "imageurl")
    private String imageurl;

    public Long getTourid() {
        return tourid;
    }

    public void setTourid(Long tourid) {
        this.tourid = tourid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoadaddress() {
        return roadaddress;
    }

    public void setRoadaddress(String roadaddress) {
        this.roadaddress = roadaddress;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
