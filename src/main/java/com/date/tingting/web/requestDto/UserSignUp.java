package com.date.tingting.web.requestDto;

import com.date.tingting.domain.user.Authority;
import com.date.tingting.domain.user.User;
import com.date.tingting.handler.exception.TingTingCommonException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Collections;

@Getter
@Setter
public class UserSignUp {
    private String uuid;
    @NotEmpty(message = "아이디는 필수 입력값입니다.")
    private String userId;
    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    private String password;
    private String userEmail;
    private String university;
    private String birthDay;
    private String gender;

    public UserSignUp() {
    }

    @Builder
    public UserSignUp(String uuid, String userId, String password, String userEmail, String university, String birthDay, String gender) {
        this.uuid = uuid;
        this.userId = userId;
        this.password = password;
        this.userEmail = userEmail;
        this.university = university;
        this.birthDay = birthDay;
        this.gender = gender;
    }

    public User toEntity() {
        return User.builder()
                .uuid(uuid)
                .userId(userId)
                .password(password)
                .userEmail(userEmail)
                .university(university)
                .birthDay(birthDay)
                .gender(gender)
                .isDel("0")
                .isActive("0")
                .roles(Authority.ROLE_USER.name())
                .build();
    }

    public void validate() {
        if (!userEmail.contains("@")) {
            throw new TingTingCommonException("올바른 이메일 형식이 아닙니다.");
        }
        if (birthDay.length() != 4){
            throw new TingTingCommonException("잘못된 생년월일 형식입니다.");
        }
    }

}
