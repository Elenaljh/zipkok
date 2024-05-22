package com.ssafy.hw.model.service;

import jakarta.mail.internet.MimeMessage;

import java.util.HashMap;

public interface MailService {
    public void createNumber(String mail);
    public MimeMessage createMail(String mail);
    public String sendMail(String mail);
    public String sendMailForPassword(String email);
    public boolean verifyCode(String mail, String code);
}
