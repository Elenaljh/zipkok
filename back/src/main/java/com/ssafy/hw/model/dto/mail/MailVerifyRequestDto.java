package com.ssafy.hw.model.dto.mail;

public class MailVerifyRequestDto {
    private String email;

    public MailVerifyRequestDto() {
    }

    public MailVerifyRequestDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
