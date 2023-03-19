package com.date.tingting.service;

import com.date.tingting.config.jwt.JwtTokenProvider;
import com.date.tingting.handler.exception.JwtException;
import com.date.tingting.web.requestDto.RegenerateTokenRequest;
import com.date.tingting.web.responseDto.CodeResponse;
import com.date.tingting.web.responseDto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate redisTemplate;


    public UserResponse.TokenInfo regenerateToken(RegenerateTokenRequest regenerateTokenRequest) {

        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(regenerateTokenRequest.getRefreshToken())) {
            throw new JwtException("잘못된 형식이거나 만료된 리프레시 토큰입니다.");
        }

        // 2. Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(regenerateTokenRequest.getAccessToken());




        // 3. Redis 에서 User email 을 기반으로 저장된 Refresh Token 값을 가져옵니다.
        String refreshToken = (String)redisTemplate.opsForValue().get("RT:" + authentication.getName());

        // (추가) 로그아웃되어 Redis 에 RefreshToken 이 존재하지 않는 경우 처리
        if(ObjectUtils.isEmpty(refreshToken)) {
            throw new JwtException("잘못된 형식이거나 만료된 리프레시 토큰입니다.");
        }

        if(!refreshToken.equals(regenerateTokenRequest.getRefreshToken())) {
            throw new JwtException("Refresh Token 정보가 일치하지 않습니다.");
        }

        // 4. 새로운 토큰 생성
        UserResponse.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // 5. RefreshToken Redis 업데이트
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return tokenInfo;
    }
}
