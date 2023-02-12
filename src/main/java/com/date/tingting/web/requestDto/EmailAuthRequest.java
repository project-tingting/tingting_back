package com.date.tingting.web.requestDto;

import com.date.tingting.domain.emailAuth.EmailAuth;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailAuthRequest {

    @ApiModelProperty(example = "유저 이메일")
    private String userEmail;
    @ApiModelProperty(example = "인증 토큰")
    private String authToken;
    @ApiModelProperty(example = "만료일")
    private Boolean expired;

    public EmailAuth toEntity() {
        return EmailAuth.builder()
                .userEmail(userEmail)
                .authToken(authToken)
                .expired(expired)
                .build();
    }
}
