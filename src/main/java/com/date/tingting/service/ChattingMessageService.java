package com.date.tingting.service;

import com.date.tingting.config.jwt.JwtTokenProvider;
import com.date.tingting.domain.emailAuth.EmailAuth;
import com.date.tingting.domain.emailAuth.EmailAuthRepository;
import com.date.tingting.domain.tingTingToken.TingTingTokenRepository;
import com.date.tingting.domain.user.Authority;
import com.date.tingting.domain.user.User;
import com.date.tingting.domain.user.UserRepository;
import com.date.tingting.handler.exception.EmailAuthTokenNotFountException;
import com.date.tingting.handler.exception.TingTingCommonException;
import com.date.tingting.handler.exception.TingTingDataNotFoundException;
import com.date.tingting.handler.exception.UserNotFoundException;
import com.date.tingting.utils.GetUniversityFromEmail;
import com.date.tingting.web.requestDto.EmailAuthRequest;
import com.date.tingting.web.requestDto.UserLogin;
import com.date.tingting.web.requestDto.UserLogout;
import com.date.tingting.web.requestDto.UserSignUp;
import com.date.tingting.web.responseDto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChattingMessageService {



}
