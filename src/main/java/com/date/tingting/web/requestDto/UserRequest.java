package com.date.tingting.web.requestDto;

import com.date.tingting.domain.user.User;
import com.date.tingting.handler.exception.TingTingCommonException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String uuid;
    private String userMail;
    private String password;
    private String university;
    private String major;
    private String nickName;
    private String birthDate;
    private String gender;

    public User toEntity() {
        return User.builder()
                .uuid(uuid)
                .userMail(userMail)
                .password(password)
                .university(university)
                .major(major)
                .nickName(nickName)
                .birthDate(birthDate)
                .gender(gender)
                .isDel("0")
                .build();
    }

    public void validate() {
        if (!userMail.contains("@")) {
            throw new TingTingCommonException("올바른 이메일 형식이 아닙니다.");
        }
        if (birthDate.length() != 7){
            throw new TingTingCommonException("잘못된 주민등록번호 형식입니다.");
        }
    }


}
