package com.date.tingting.service;

import com.date.tingting.domain.emailAuth.EmailAuth;
import com.date.tingting.domain.emailAuth.EmailAuthRepository;
import com.date.tingting.domain.user.User;
import com.date.tingting.domain.user.UserRepository;
import com.date.tingting.handler.exception.TingTingDataNotFoundException;
import com.date.tingting.web.requestDto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final EmailAuthRepository emailAuthRepository;

    @Autowired
    private final EmailAuthService emailAuthService;

    @Transactional
    public String save(UserRequest userRequest){

        // 메일 인증 토큰 생성
        EmailAuth emailAuth = emailAuthRepository.save(
                EmailAuth.builder()
                        .userEmail(userRequest.getUserEmail())
                        .authToken(UUID.randomUUID().toString())
                        .expired(false)
                        .build());

        //유저 고유 식별 키 생성
        String uuid = UUID.randomUUID().toString().replace("-", "");
        userRequest.setUuid(uuid);

        //메일 발송
        emailAuthService.send(emailAuth.getUserEmail(), emailAuth.getAuthToken());

        return userRepository.save(userRequest.toEntity()).getUuid();
    }

    @Transactional(readOnly = true)
    public User findByUuid(String uuid) {
        User user = userRepository.findByUuid(uuid);

        if(user == null){
            throw new TingTingDataNotFoundException();
        }

        return user;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        List<User> user = userRepository.findAll();

        if(user == null){
            throw new TingTingDataNotFoundException();
        }

        return user;
    }

}
