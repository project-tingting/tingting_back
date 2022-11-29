package com.date.tingting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;


@org.springframework.stereotype.Service
@EnableAsync
@RequiredArgsConstructor
public class EmailAuthService {

    private final JavaMailSender javaMailSender;
    @Async
    public void send(String email, String authToken) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(email);
        smm.setSubject("회원가입 이메일 인증");
        smm.setText("http://localhost:8080/sign/confirm-email?email="+email+"&authToken="+authToken);
        smm.setFrom("tingting7777@naver.com");

        javaMailSender.send(smm);
    }
}
