package com.date.tingting.service;

import com.date.tingting.domain.user.User;
import com.date.tingting.domain.user.UserRepository;
import com.date.tingting.handler.exception.TingTingCommonException;
import com.date.tingting.handler.exception.TingTingDataNotFoundException;
import com.date.tingting.web.responseDto.UserResponse;
import com.date.tingting.web.requestDto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    @Transactional
    public HashMap save(UserRequest userRequest){

        //유저 고유 식별 키 생성
        String uuid = UUID.randomUUID().toString().replace("-", "");
        userRequest.setUuid(uuid);

        //주민등록번호를 기준으로 성별 분류
        if(userRequest.getBirthDate().length() != 7){
            //예외 발생
            throw new TingTingCommonException("잘못된 주민등록번호 형식입니다.");
        }
        char genderNum = userRequest.getBirthDate().charAt(6);
        String gender = genderNum%2 == 0 ? "w" : "m" ;
        userRequest.setGender(gender);

        uuid =  userRepository.save(userRequest.toEntity()).getUuid();

        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("uuid", uuid);

        return map;
    }

    @Transactional(readOnly = true)
    public UserResponse findByUuid(String uuid) {
        User entity = userRepository.findByUuid(uuid);

        if(entity == null){
            throw new TingTingDataNotFoundException();
        }
        return new UserResponse(entity);
    }

}
