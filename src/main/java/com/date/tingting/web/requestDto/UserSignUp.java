package com.date.tingting.web.requestDto;

import com.date.tingting.domain.user.Authority;
import com.date.tingting.domain.user.User;
import com.date.tingting.handler.exception.TingTingCommonException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Collections;

@Getter
@Setter
public class UserSignUp {

    @ApiModelProperty(example = "유저 uuid")
    private String uuid;
    @ApiModelProperty(example = "유저 아이디")
    @NotEmpty(message = "아이디는 필수 입력값입니다.")
    private String userId;
    @ApiModelProperty(example = "유저 비밀번호")
    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    private String password;
    @ApiModelProperty(example = "유저 이메일")
    private String userEmail;
    @ApiModelProperty(example = "유저 대학")
    private String university;
    @ApiModelProperty(example = "유저 생년원일")
    private String birthDay;
    @ApiModelProperty(example = "유저 성별")
    private String gender;

    public UserSignUp() {
    }

    @Builder
    public UserSignUp(String uuid, String userId, String password, String userEmail, String university, String birthDay, String gender) {
        this.uuid = uuid;
        this.userId = userId;
        this.password = password;
        this.userEmail = userEmail;
        this.university = university;
        this.birthDay = birthDay;
        this.gender = gender;
    }

    public User toEntity() {
        return User.builder()
                .uuid(uuid)
                .userId(userId)
                .password(password)
                .userEmail(userEmail)
                .university(university)
                .birthDay(birthDay)
                .gender(gender)
                .isDel("0")
                .isActive("0")
                .roles(Authority.ROLE_USER.name())
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
