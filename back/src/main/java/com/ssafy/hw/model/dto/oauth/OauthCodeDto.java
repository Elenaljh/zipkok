package com.ssafy.hw.model.dto.oauth;

public class OauthCodeDto {
    private String code;

    public OauthCodeDto() {
    }

    public OauthCodeDto(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
