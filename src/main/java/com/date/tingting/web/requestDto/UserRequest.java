package com.date.tingting.web.requestDto;

import com.date.tingting.domain.user.User;
import com.date.tingting.handler.exception.TingTingCommonException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    private long userNo;
    private String uuid;
    private String userMail;
    private String password;
    private String university;
    private String major;
    private String nickName;
    private String gender;
    private String birthDate;


    @Builder
    public UserRequest(String userMail, String password, String university, String major, String nickName, String birthDate) {
        this.userMail = userMail;
        this.password = password;
        this.university = university;
        this.major = major;
        this.nickName = nickName;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public void validate() {
        if (!userMail.contains("@")) {
            throw new TingTingCommonException("올바른 이메일 형식이 아닙니다.");
        }
    }

    public User toEntity() {
        return User.builder()
                .userNo(userNo)
                .uuid(uuid)
                .userMail(userMail)
                .password(password)
                .university(university)
                .major(major)
                .nickName(nickName)
                .gender(gender)
                .birthDate(birthDate)
                .build();
    }

}
