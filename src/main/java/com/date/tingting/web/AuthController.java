package com.date.tingting.web;

import com.date.tingting.domain.user.UserRepository;
import com.date.tingting.response.ResponseService;
import com.date.tingting.response.TingTingResponse;
import com.date.tingting.web.requestDto.RegenerateTokenRequest;
import com.date.tingting.web.responseDto.UserResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RestController
@Api(tags = "Auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final ResponseService responseService;

//    @Autowired
//    private final AuthService authService;


    @Autowired
    UserRepository userRepository;

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;              // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;    // 7일


    @PostMapping("/regenerateToken")
    public UserResponse.TokenInfo regenerateToken(@AuthenticationPrincipal User user, RegenerateTokenRequest regenerateTokenRequest) {

        byte[] keyBytes = Decoders.BASE64.decode("VlwEyVBsYt9V7zq57TejMnVUyzblYcfPQye08f7MGVA9XkHN");
        Key key = Keys.hmacShaKeyFor(keyBytes);

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(user.getUsername())
                .claim(AUTHORITIES_KEY, "ROLE_USER")
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return UserResponse.TokenInfo.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpirationTime(REFRESH_TOKEN_EXPIRE_TIME)
                .uuid(user.getUsername())
                .build();
    }

}
