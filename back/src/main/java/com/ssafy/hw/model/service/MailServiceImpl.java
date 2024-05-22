package com.ssafy.hw.model.service;

import com.ssafy.hw.model.dao.MemberDao;
import com.ssafy.hw.model.dto.Member;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class MailServiceImpl implements MailService{

    private final JavaMailSender javaMailSender;
    private static final String senderEmail = "hye9807@g.skku.edu";
    private final MemberDao dao;
    private static Map<String, String> emailCodeMap = new HashMap<>();

    public MailServiceImpl(JavaMailSender javaMailSender, MemberDao dao) {
        this.javaMailSender = javaMailSender;
        this.dao = dao;
    }

    @Override
    @Transactional
    public void createNumber(String mail) {
        String number = String.valueOf((int) (Math.random() * (900000)) + 100000);
        emailCodeMap.put(mail, number);
    }

    @Override
    @Transactional
    public MimeMessage createMail(String mail) {
        createNumber(mail);
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("이메일 인증");
            String body = "<h3>요청하신 인증 번호입니다.</h3><h1>" + emailCodeMap.get(mail) + "</h1>";
            message.setText(body, "UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public String sendMail(String mail) {
        try {
            if(this.checkDuplicatedEmail(mail)) {
                MimeMessage message = createMail(mail);
                javaMailSender.send(message);
                return "인증메일 전송";
            }
            return "해당 이메일로 가입된 회원 존재";
        } catch (Exception e) {
            return "메일발송중 문제 발생";
        }
    }

    @Override
    @Transactional
    public String sendMailForPassword(String email) {
        System.out.println("sendMailForPassword email: " + email);
        try {
            if (checkDuplicatedEmail(email)) { //회원이 없으면
                return "회원 없음";
            }
            MimeMessage message = createMail(email);
            javaMailSender.send(message);
            return "인증메일 전송";
        } catch (Exception e) {
            e.printStackTrace();
            return "메일발송중 문제 발생";
        }
    }

    @Override
    @Transactional
    public boolean verifyCode(String mail, String code) {
        return emailCodeMap.containsKey(mail) && emailCodeMap.get(mail).equals(code);
    }

    @Transactional
    private boolean checkDuplicatedEmail(String email) {
        System.out.println("email: "+email);
        Member member = dao.selectMember(email);
        System.out.println("member: " + member);
        return member==null; //true면 중복 이메일 없음, false면 중복이메일 있음
    }
}
