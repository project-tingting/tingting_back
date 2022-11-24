package com.date.tingting.web.responseDto;

import com.date.tingting.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserResponse {

    private String uuid;
    private String userId;
    private String userMail;
    private String password;
    private String university;
    private String major;
    private String gender;
    private String birthDay;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;


    public UserResponse(User entity) {
        this.uuid = entity.getUuid();
        this.userId = entity.getUserId();
        this.userMail = entity.getUserMail();
        this.password = entity.getPassword();
        this.university = entity.getUniversity();
        this.major = entity.getMajor();
        this.gender = entity.getGender();
        this.birthDay = entity.getBirthDay();
        this.registerDate = entity.getRegisterDate();
        this.updateDate = entity.getUpdateDate();
    }

}
