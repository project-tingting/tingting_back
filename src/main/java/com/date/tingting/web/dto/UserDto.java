package com.date.tingting.web.dto;

import com.date.tingting.domain.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {

    private Long userNo;
    private String userMail;
    private String password;
    private String salt;
    private String university;
    private String major;
    private String userName;
    //생성자에서 초기회 추천
    private String isDel = "0";
    private String registerDate;
    private String updateDate;


    public UserDto(User entity) {
        this.userNo = entity.getUserNo();
        this.userMail = entity.getUserMail();
        this.password = entity.getPassword();
        this.university = entity.getUniversity();
        this.major = entity.getMajor();
        this.userName = entity.getUserName();
    }

    public User toEntity() {
        return User.builder()
                .userMail(userMail)
                .password(password)
                .university(university)
                .major(major)
                .isDel(isDel)
                .userName(userName)
                .build();
    }

}
