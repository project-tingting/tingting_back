package com.date.tingting.service;

import com.date.tingting.domain.user.User;
import com.date.tingting.domain.user.UserRepository;
import com.date.tingting.web.requestDto.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 - 정상")
    void 회원가입() {
        // given
        UserRequest userRequest = UserRequest.builder()
                .userMail("test@nate.com")
                .password("1234")
                .university("서울대학교")
                .major("컴퓨터공학과")
                .nickName("홍길동")
                .build();

        // when
        userService.save(userRequest);

        // then
        assertEquals(1, userRepository.count());
        User user = userRepository.findAll().get(0);
        assertEquals("test@nate.com", user.getUserMail());
        assertEquals("홍길동", user.getNickName());
    }

}