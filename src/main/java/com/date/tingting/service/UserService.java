package com.date.tingting.service;

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

    @Transactional
    public String save(UserRequest userRequest){

        //유저 고유 식별 키 생성
        String uuid = UUID.randomUUID().toString().replace("-", "");
        userRequest.setUuid(uuid);

        //주민번호로 성별 구분
        char genderNum = userRequest.getBirthDate().charAt(6);
        String gender = genderNum%2 == 0 ? "w" : "m" ;
        userRequest.setGender(gender);

        //성별 구분용으로 받은 주민번호 마지막 숫자 제거
        String birthDate = userRequest.getBirthDate();
        birthDate  = birthDate.substring(0, birthDate.length() - 1);
        userRequest.setBirthDate(birthDate);

        uuid = userRepository.save(userRequest.toEntity()).getUuid();

        return uuid;
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
