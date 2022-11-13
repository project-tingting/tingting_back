package com.date.tingting.service;

import com.date.tingting.domain.user.User;
import com.date.tingting.domain.user.UserRepository;
import com.date.tingting.web.dto.UserDto;
import com.date.tingting.web.dto.UserRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    @Transactional
    public long save(UserRegister userRegister) {
        return userRepository.save(userRegister.toEntity()).getUserNo();
    }

    @Transactional(readOnly = true)
    public UserDto findByUserMail(String userMail) {
        User entity = userRepository.findByUserMail(userMail);
        return new UserDto(entity);
    }

}
