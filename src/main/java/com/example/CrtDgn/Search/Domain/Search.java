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

    @Column(name = "title")
    private String title;

    @Column(name = "roadaddress")
    private String roadaddress;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "phoneno")
    private String phoneno;

    @Column(name = "tag")
    private String tag;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "imagepath")
    private String imagepath;

    public Long getTourid() {
        return tourid;
    }

    public void setTourid(Long tourid) {
        this.tourid = tourid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
}
