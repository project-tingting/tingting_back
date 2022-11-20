package com.date.tingting.web.responseDto;

import com.date.tingting.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserResponse {

    private String uuid;
    private String userMail;
    private String password;
    private String university;
    private String major;
    private String nickName;
    private String gender;
    private String birthDate;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;


    public UserResponse(User entity) {
        this.uuid = entity.getUuid();
        this.userMail = entity.getUserMail();
        this.password = entity.getPassword();
        this.university = entity.getUniversity();
        this.major = entity.getMajor();
        this.nickName = entity.getNickName();
        this.gender = entity.getGender();
        this.birthDate = entity.getBirthDate();
        this.registerDate = entity.getRegisterDate();
        this.updateDate = entity.getUpdateDate();
    }

}
