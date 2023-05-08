package com.example.CrtDgn.Search.Domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "charge")
@Getter
@Setter
@Entity
@Builder
public class Charge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chargeid")
    private Long chargeid;

    @Column(name = "chargingplace")
    private String chargingplace;

    @Column(name = "chargingplacedetail")
    private String chargingplacedetail;

    @Column(name = "addressjibun")
    private String address;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "chargingflag")
    private String chargingflag;

    @Column(name = "quickchargingflag")
    private String quickchargingflag;

    public Long getChargeid() {
        return chargeid;
    }

    public void setChargeid(Long chargeid) {
        this.chargeid = chargeid;
    }

    public String getChargingplace() {
        return chargingplace;
    }

    public void setChargingplace(String chargingplace) {
        this.chargingplace = chargingplace;
    }

    public String getChargingplacedetail() {
        return chargingplacedetail;
    }

    public void setChargingplacedetail(String chargingplacedetail) {
        this.chargingplacedetail = chargingplacedetail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getChargingflag() {
        return chargingflag;
    }

    public void setChargingflag(String chargingflag) {
        this.chargingflag = chargingflag;
    }

    public String getQuickchargingflag() {
        return quickchargingflag;
    }

    public void setQuickchargingflag(String quickchargingflag) {
        this.quickchargingflag = quickchargingflag;
    }

    public String getChargercount() {
        return chargercount;
    }

    public void setChargercount(String chargercount) {
        this.chargercount = chargercount;
    }

    public String getQuickchargercount() {
        return quickchargercount;
    }

    public void setQuickchargercount(String quickchargercount) {
        this.quickchargercount = quickchargercount;
    }

    public String getParkingfeeflag() {
        return parkingfeeflag;
    }

    public void setParkingfeeflag(String parkingfeeflag) {
        this.parkingfeeflag = parkingfeeflag;
    }

    @Column(name = "chargercount")
    private String chargercount;

    @Column(name = "quickchargercount")
    private String quickchargercount;

    @Column(name = "parkingfeeflag")
    private String parkingfeeflag;
}
