package com.date.tingting.web.responseDto;

import com.date.tingting.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserResponse {

    private String uuid;
    private String userId;
    private String userEmail;
    private String password;
    private String university;
    private String gender;
    private String birthDay;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;


    public UserResponse(User entity) {
        this.uuid = entity.getUuid();
        this.userId = entity.getUserId();
        this.userEmail = entity.getUserEmail();
        this.password = entity.getPassword();
        this.university = entity.getUniversity();
        this.gender = entity.getGender();
        this.birthDay = entity.getBirthDay();
        this.registerDate = entity.getRegisterDate();
        this.updateDate = entity.getUpdateDate();
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class TokenInfo {
        private String grantType;
        private String accessToken;
        private String refreshToken;
        private Long accessTokenExpirationTime;
    }

}
