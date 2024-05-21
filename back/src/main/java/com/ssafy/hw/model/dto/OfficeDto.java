package com.ssafy.hw.model.dto;

public class OfficeDto {
    public String officeType;
    public String officeName;
    public String drmAddress;
    public String tel;
    public String dongCode;
    public double latitude;
    public double longitude;

    public OfficeDto() {
    }

    public OfficeDto(String officeType, String officeName, String drmAddress, String tel, String dongCode,
                     double latitude,
                     double longitude) {
        this.officeType = officeType;
        this.officeName = officeName;
        this.drmAddress = drmAddress;
        this.tel = tel;
        this.dongCode = dongCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getOfficeType() {
        return officeType;
    }

    public void setOfficeType(String officeType) {
        this.officeType = officeType;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getDrmAddress() {
        return drmAddress;
    }

    public void setDrmAddress(String drmAddress) {
        this.drmAddress = drmAddress;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDongCode() {
        return dongCode;
    }

    public void setDongCode(String dongCode) {
        this.dongCode = dongCode;
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
}
