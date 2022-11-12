package com.date.tingting.web.dto;

import com.date.tingting.domain.user.User;
import lombok.Builder;
import lombok.Data;

@Data
public class UserDto {

    private Long userNo;
    private String userEmail;
    private String password;
    private String salt;
    private String university;
    private String major;
    private String userName;
    private String isDel;
    private String registerDate;
    private String updateDate;


    @Builder
    public UserDto(String userEmail, String password, String university, String major, String userName) {
        this.userEmail = userEmail;
        this.password = password;
        this.university = university;
        this.major = major;
        this.userName = userName;
    }

    public User toEntity() {
        return User.builder()
                .userEmail(userEmail)
                .password(password)
                .university(university)
                .major(major)
                .userName(userName)
                .build();
    }

}
