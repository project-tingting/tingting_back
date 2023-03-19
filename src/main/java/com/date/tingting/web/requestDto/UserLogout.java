package com.date.tingting.web.requestDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLogout {

    @ApiModelProperty(example = "jwt 토큰")
    private String accessToken;

    @ApiModelProperty(example = "리프레시 토큰")
    private String refreshToken;
}
