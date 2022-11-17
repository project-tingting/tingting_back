package com.date.tingting.web.responseDto;

import com.date.tingting.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
public class UserResponse {

    private Long userNo;
    private String uuid;
    private String userMail;
    private String password;
    private String university;
    private String major;
    private String nickName;
    //생성자에서 초기회 추천
    private String isDel = "0";
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;


    public UserResponse(User entity) {
        this.uuid = entity.getUuid();
        this.userNo = entity.getUserNo();
        this.userMail = entity.getUserMail();
        this.password = entity.getPassword();
        this.university = entity.getUniversity();
        this.major = entity.getMajor();
        this.nickName = entity.getNickName();
        this.registerDate = entity.getRegisterDate();
        this.updateDate = entity.getUpdateDate();
    }

    public User toEntity() {
        return User.builder()
                .userMail(uuid)
                .userMail(userMail)
                .password(password)
                .university(university)
                .major(major)
                .isDel(isDel)
                .nickName(nickName)
                .registerDate(registerDate)
                .updateDate(updateDate)
                .build();
    }

}
