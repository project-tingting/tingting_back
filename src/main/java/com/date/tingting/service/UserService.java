package com.date.tingting.service;

import com.date.tingting.config.jwt.JwtTokenProvider;
import com.date.tingting.domain.emailAuth.EmailAuth;
import com.date.tingting.domain.emailAuth.EmailAuthRepository;
import com.date.tingting.domain.user.Authority;
import com.date.tingting.domain.user.User;
import com.date.tingting.domain.user.UserRepository;
import com.date.tingting.handler.exception.EmailAuthTokenNotFountException;
import com.date.tingting.handler.exception.TingTingCommonException;
import com.date.tingting.handler.exception.TingTingDataNotFoundException;
import com.date.tingting.handler.exception.UserNotFoundException;
import com.date.tingting.web.requestDto.*;
import com.date.tingting.web.responseDto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final EmailAuthRepository emailAuthRepository;

    @Autowired
    private final EmailAuthService emailAuthService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisTemplate redisTemplate;

    @Transactional
    public String signup(UserSignUp userSignUp) {

        //메일 인증을 완료하지 않은 상태에서 다시 회원가입 할 경우 해당 유저를 삭제하고 재생성 한다.
        if (userRepository.existsByUserId(userSignUp.getUserId())) {
            User target = userRepository.findByUserId(userSignUp.getUserId()).get();
            if (target.getIsActive().equals("0")) {
                userRepository.deleteByUserId(target.getUserId());
            }
            throw new TingTingCommonException("기존에 존재하던 유저 삭제중 오류가 발생하였습니다.");
        }

        //유저 고유 식별 키 생성
        String uuid = UUID.randomUUID().toString().replace("-", "");
        User user = User.builder()
                .uuid(uuid)
                .userId(userSignUp.getUserId())
                .password(passwordEncoder.encode(userSignUp.getPassword()))
                .userEmail(userSignUp.getUserEmail())
                .university(userSignUp.getUniversity())
                .gender(userSignUp.getGender())
                .birthDay(userSignUp.getBirthDay())
                .isDel("0")
                .isActive("0")
                .roles(Authority.ROLE_USER.name())
                .build();

        uuid = userRepository.save(user).getUuid();

        // 메일 인증 토큰 생성
        EmailAuth emailAuth = emailAuthRepository.save(
                EmailAuth.builder()
                        .userEmail(userSignUp.getUserEmail())
                        .authToken(UUID.randomUUID().toString())
                        .expired(false)
                        .build());

        //메일 발송
        emailAuthService.send(emailAuth.getUserEmail(), emailAuth.getAuthToken());

        return uuid;
    }

    @Transactional
    public UserResponse.TokenInfo login(UserLogin userLogin) {
        if (!userRepository.existsByUserId(userLogin.getUserId())) throw new TingTingCommonException("존재하지 않은 유저입니다.");

        UsernamePasswordAuthenticationToken authenticationToken = userLogin.toAuthentication();

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        UserResponse.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        redisTemplate.opsForValue().set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getAccessTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return tokenInfo;
    }

    @Transactional
    public void logout(UserLogout userLogout) {
        if (!jwtTokenProvider.validateToken(userLogout.getAccessToken())) {
            throw new TingTingCommonException("잘못된 요청입니다.");
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(userLogout.getAccessToken());

        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        Long expiration = jwtTokenProvider.getExpiration(userLogout.getAccessToken());
        redisTemplate.opsForValue()
                .set(userLogout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
    }


    public User getUser(String uuid) {
        User user = userRepository.findByUuid(uuid);

        if(user == null){
            throw new TingTingDataNotFoundException();
        }

        return user;
    }

    public List<User> getUserList() {
        List<User> user = userRepository.findAll();

        if(user == null){
            throw new TingTingDataNotFoundException();
        }

        return user;
    }

    /**
     * 이메일 인증 성공
     * @param emailAuthRequest
     */
    @Transactional
    public void confirmEmail(EmailAuthRequest emailAuthRequest) {

        EmailAuth emailAuth = emailAuthRepository.findValidAuthByEmail(emailAuthRequest.getUserEmail(), emailAuthRequest.getAuthToken(), LocalDateTime.now())
                .orElseThrow(EmailAuthTokenNotFountException::new);

        User user = userRepository.findByUserEmail(emailAuthRequest.getUserEmail()).orElseThrow(UserNotFoundException::new);
        emailAuth.useToken();
        user.emailVerifiedSuccess();
    }


    public User confirmCheck(String userEmail) {
        return userRepository.findByUserEmailAndIsActive(userEmail,"1")
                .orElseThrow(() -> new TingTingCommonException("인증되지 않은 유저입니다."));
    }

}
