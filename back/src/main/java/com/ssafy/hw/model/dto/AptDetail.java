package com.ssafy.hw.model.dto;

public class AptDetail {
    private String aptCode;
    private String aptName;
    private String drmAddress; //도로명주소
    private String bjdAddress; //법정동주소
    private String bjdCode; //법정동코드
    private int houseNum; //세대수
    private int buildYear; //건립년도
    private int dongNum; //동수
    private int carNum; //주차대수
    private int cctvNum; //cctv 대수
    private double lng; //경도
    private double lat; //위도
    private String aptType; //계단식, 복도식,..
    private String facility; //아파트 부대시설들
    private String aptAnotherCode; //다른 코드

    public AptDetail() {
    }

    public AptDetail(String aptCode, String aptName, String drmAddress, String bjdAddress, String bjdCode, int houseNum, int buildYear, int dongNum, int carNum, int cctvNum, double lng, double lat, String aptType, String facility, String aptAnotherCode) {
        this.aptCode = aptCode;
        this.aptName = aptName;
        this.drmAddress = drmAddress;
        this.bjdAddress = bjdAddress;
        this.bjdCode = bjdCode;
        this.houseNum = houseNum;
        this.buildYear = buildYear;
        this.dongNum = dongNum;
        this.carNum = carNum;
        this.cctvNum = cctvNum;
        this.lng = lng;
        this.lat = lat;
        this.aptType = aptType;
        this.facility = facility;
        this.aptAnotherCode = aptAnotherCode;
    }

    public String getAptCode() {
        return aptCode;
    }

    public void setAptCode(String aptCode) {
        this.aptCode = aptCode;
    }

    public String getAptName() {
        return aptName;
    }

    public void setAptName(String aptName) {
        this.aptName = aptName;
    }

    public String getDrmAddress() {
        return drmAddress;
    }

    public void setDrmAddress(String drmAddress) {
        this.drmAddress = drmAddress;
    }

    public String getBjdAddress() {
        return bjdAddress;
    }

    public void setBjdAddress(String bjdAddress) {
        this.bjdAddress = bjdAddress;
    }

    public String getBjdCode() {
        return bjdCode;
    }

    public void setBjdCode(String bjdCode) {
        this.bjdCode = bjdCode;
    }

    public int getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(int houseNum) {
        this.houseNum = houseNum;
    }

    public int getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(int buildYear) {
        this.buildYear = buildYear;
    }

    public int getDongNum() {
        return dongNum;
    }

    public void setDongNum(int dongNum) {
        this.dongNum = dongNum;
    }

    public int getCarNum() {
        return carNum;
    }

    public void setCarNum(int carNum) {
        this.carNum = carNum;
    }

    public int getCctvNum() {
        return cctvNum;
    }

    public void setCctvNum(int cctvNum) {
        this.cctvNum = cctvNum;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getAptType() {
        return aptType;
    }

    public void setAptType(String aptType) {
        this.aptType = aptType;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getAptAnotherCode() {
        return aptAnotherCode;
    }

    public void setAptAnotherCode(String aptAnotherCode) {
        this.aptAnotherCode = aptAnotherCode;
    }
}
