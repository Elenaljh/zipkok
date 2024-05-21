package com.ssafy.hw.model.dto;

public class LoginOutputDto {
    private String name;
    private String email;
    private int memberId;

    public LoginOutputDto() {
    }

    public LoginOutputDto(String name, String email, int memberId) {
        this.name = name;
        this.email = email;
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
