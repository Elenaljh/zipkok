package com.ssafy.hw.model.dto;

public class SchoolDto {
    public String schoolName;
    public String schoolType;
    public String privatePublic;
    public String bjdAddress;
    public String drmAddress;
    public double lat;
    public double lng;

    public SchoolDto() {
    }

    public SchoolDto(String schoolName, String schoolType, String privatePublic, String bjdAddress, String drmAddress,
                     double lat, double lng) {
        this.schoolName = schoolName;
        this.schoolType = schoolType;
        this.privatePublic = privatePublic;
        this.bjdAddress = bjdAddress;
        this.drmAddress = drmAddress;
        this.lat = lat;
        this.lng = lng;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public String getPrivatePublic() {
        return privatePublic;
    }

    public void setPrivatePublic(String privatePublic) {
        this.privatePublic = privatePublic;
    }

    public String getBjdAddress() {
        return bjdAddress;
    }

    public void setBjdAddress(String bjdAddress) {
        this.bjdAddress = bjdAddress;
    }

    public String getDrmAddress() {
        return drmAddress;
    }

    public void setDrmAddress(String drmAddress) {
        this.drmAddress = drmAddress;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
