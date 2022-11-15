package com.date.tingting.web.responseDto;

import com.date.tingting.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse {

    private Long userNo;
    private String userMail;
    private String password;
    private String university;
    private String major;
    private String nickName;
    //생성자에서 초기회 추천
    private String isDel = "0";
    private String registerDate;
    private String updateDate;


    public UserResponse(User entity) {
        this.userNo = entity.getUserNo();
        this.userMail = entity.getUserMail();
        this.password = entity.getPassword();
        this.university = entity.getUniversity();
        this.major = entity.getMajor();
        this.nickName = entity.getNickName();
    }

    public User toEntity() {
        return User.builder()
                .userMail(userMail)
                .password(password)
                .university(university)
                .major(major)
                .isDel(isDel)
                .nickName(nickName)
                .build();
    }

}
