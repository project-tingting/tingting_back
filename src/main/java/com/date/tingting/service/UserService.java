package com.date.tingting.service;

import com.date.tingting.domain.user.User;
import com.date.tingting.domain.user.UserRepository;
import com.date.tingting.handler.exception.InValidRequest;
import com.date.tingting.handler.exception.TingTingDataNotFoundException;
import com.date.tingting.web.responseDto.UserResponse;
import com.date.tingting.web.requestDto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    @Transactional
    public long save(UserRequest userRequest) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        userRequest.setUuid(uuid);
        return userRepository.save(userRequest.toEntity()).getUserNo();
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
