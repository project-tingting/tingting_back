package com.date.tingting.service;

import com.date.tingting.domain.user.User;
import com.date.tingting.domain.user.UserRepository;
import com.date.tingting.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public long save(UserDto userDto) {
        return userRepository.save(userDto.toEntity()).getUserNo();
    }

    @Transactional(readOnly = true)
    public UserDto findByUserMail(String userMail) {
        User entity = userRepository.findByUserMail(userMail);
//                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. userMail=" + userMail));
        return new UserDto(entity);
    }

}
