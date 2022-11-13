package com.date.tingting.web;

import com.date.tingting.domain.user.User;
import com.date.tingting.domain.user.UserRepository;
import com.date.tingting.web.dto.UserRegister;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("유저 정보 조회")
    void getUser() throws Exception {
        // given
        User user = User.builder()
                .userMail("test@nate.com")
                .password("1234")
                .university("서울대학교")
                .major("컴퓨터공학과")
                .userName("홍길동")
                .isDel("0")
                .build();
        userRepository.save(user);

        // expected
        mockMvc.perform(get("/user/{userMail}", "test@nate.com")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.university").value("서울대학교"))
                .andExpect(jsonPath("$.userName").value("홍길동"))
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 - 잘못된 이메일 형식")
    void 회원가입_실패_1() throws Exception {
        // given
        UserRegister userRegister = UserRegister.builder()
                .userMail("test#nate.com")
                .password("1234")
                .university("서울대학교")
                .major("컴퓨터공학과")
                .userName("홍길동")
                .build();
        String json = objectMapper.writeValueAsString(userRegister);
        // expected
        mockMvc.perform(post("/user")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }
}