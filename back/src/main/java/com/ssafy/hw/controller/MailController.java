package com.ssafy.hw.controller;

import com.ssafy.hw.model.dto.mail.CodeVerifyRequestDto;
import com.ssafy.hw.model.dto.mail.MailVerifyRequestDto;
import com.ssafy.hw.model.service.MailService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(originPatterns = {"*"}, allowCredentials = "true")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    //메일 보내기
    @PostMapping("/email")
    public String mailSend(@RequestBody MailVerifyRequestDto mailVerifyRequestDto) {
        return mailService.sendMail(mailVerifyRequestDto.getEmail());
    }

    //비번 바꾸기용 인증메일 보내기
    @PostMapping("/email/password")
    public String passwordMailSend(@RequestBody MailVerifyRequestDto mailVerifyRequestDto) {
        System.out.println("컨트롤러가 받은 이메일: " + mailVerifyRequestDto.getEmail());
        return mailService.sendMailForPassword(mailVerifyRequestDto.getEmail());
    }

    //코드 인증
    @PostMapping("/verifyCode")
    public String verifyCode(@RequestBody CodeVerifyRequestDto codeVerifyRequestDto) {
        boolean result = mailService.verifyCode(codeVerifyRequestDto.getEmail(), codeVerifyRequestDto.getCode());
        if (result) {
            return "인증되었습니다";
        } else {
            return "인증 실패";
        }
    }
}
