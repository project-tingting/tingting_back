package com.date.tingting.web.dto;

import com.date.tingting.domain.user.User;
import com.date.tingting.handler.exception.InValidRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegister {
    private String userMail;
    private String password;
    private String university;
    private String major;
    private String userName;

    @Builder
    public UserRegister(String userMail, String password, String university, String major, String userName) {
        this.userMail = userMail;
        this.password = password;
        this.university = university;
        this.major = major;
        this.userName = userName;
    }

    public void validate() {
        if (!userMail.contains("@")) {
            throw new InValidRequest("userMail", "올바른 이메일 형식이 아닙니다.");
        }
    }

    public User toEntity() {
        return User.builder()
                .userMail(userMail)
                .password(password)
                .university(university)
                .major(major)
                .userName(userName)
                .build();
    }

}
