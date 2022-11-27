package com.date.tingting.web.requestDto;

import com.date.tingting.domain.user.User;
import com.date.tingting.handler.exception.TingTingCommonException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String uuid;
    private String userId;
    private String password;
    private String userEmail;
    private String university;
    private String major;
    private String birthDay;
    private String gender;

    public User toEntity() {
        return User.builder()
                .uuid(uuid)
                .userId(userId)
                .password(password)
                .userEmail(userEmail)
                .university(university)
                .major(major)
                .birthDay(birthDay)
                .gender(gender)
                .isDel("0")
                .isActive("0")
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
