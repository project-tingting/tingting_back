package com.date.tingting.service;

import com.date.tingting.domain.emailAuth.EmailAuth;
import com.date.tingting.domain.user.User;
import com.date.tingting.handler.exception.EmailAuthTokenNotFountException;
import com.date.tingting.handler.exception.UserNotFoundException;
import com.date.tingting.web.requestDto.EmailAuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@EnableAsync
@RequiredArgsConstructor
public class EmailAuthService {

    private final JavaMailSender javaMailSender;
    @Async
    public void send(String email, String authToken) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(email);
        smm.setSubject("TING_TING 회원가입 이메일 인증");
        smm.setText("http://52.79.235.50:8080/tingting/user/confirm?userEmail="+email+"&authToken="+authToken);
        smm.setFrom("tingting7777@naver.com");

        javaMailSender.send(smm);
    }
}
