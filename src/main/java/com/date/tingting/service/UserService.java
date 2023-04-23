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
import com.date.tingting.web.requestDto.*;
import com.date.tingting.web.responseDto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
    @Autowired
    private final TingTingTokenRepository tingTingTokenRepository;



    @Transactional
    public void checkSignUpValue(UserSignUp userSignUp){

        //학교 메일의 도메인은 학교 마다 고유하니, 도메인을 university에 저장한다.
        String universityName = GetUniversityFromEmail.get(userSignUp.getUserEmail());
        if(universityName != null){
            userSignUp.setUniversity(universityName);
        }else{
            throw new TingTingCommonException("이메일 형식에서 학교 네임을 확인할 수 없습니다.");
        }

        //메일 인증을 완료하지 않은 상태에서 다시 회원가입 할 경우, 해당 유저 정보와 인증 토큰 정보를 삭제한다.
        if (userRepository.existsByUserId(userSignUp.getUserId())) {
            User target = userRepository.findByUserId(userSignUp.getUserId()).get();
            if (target.getIsActive().equals("0")) {
                try {
                    userRepository.deleteByUserId(target.getUserId());
                    emailAuthRepository.deleteByUserEmail(userSignUp.getUserEmail());
                }catch (Exception e){
                    e.getMessage();
                    throw new TingTingCommonException("기존에 존재하던 유저 삭제중 오류가 발생하였습니다.");
                }
            }else{
                throw new TingTingCommonException("이미 존재하는 아이디입니다.");
            }
        }else if(userRepository.existsByUserEmail(userSignUp.getUserEmail())){
            User target = userRepository.findByUserEmail(userSignUp.getUserEmail()).get();
            if (target.getIsActive().equals("0")) {
                userRepository.deleteByUserEmail(target.getUserEmail());
                emailAuthRepository.deleteByUserEmail(userSignUp.getUserEmail());
            }
        }
    }

    @Transactional
    public String signup(UserSignUp userSignUp) {

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

        //todo
        //Duplicate 이슈가 왜 예외처리 안걸리는지 확인하기
        try {
            uuid = userRepository.save(user).getUuid();
        }catch (Exception e){
            throw new TingTingCommonException(e.getMessage());
        }

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
        if (!userRepository.existsByUserIdAndIsActive(userLogin.getUserId(), "1")) throw new TingTingCommonException("존재하지 않은 유저입니다.");

        UsernamePasswordAuthenticationToken authenticationToken = userLogin.toAuthentication();

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        UserResponse.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        Optional<User> user = userRepository.findByUserId(userLogin.getUserId());

        if(user == null){
            throw new TingTingDataNotFoundException();
        }

        redisTemplate.opsForValue().set("RT:" + user.get().getUuid(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

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
