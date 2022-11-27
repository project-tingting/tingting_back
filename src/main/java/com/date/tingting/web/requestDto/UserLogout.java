package com.date.tingting.web.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLogout {
    private String accessToken;
    private String refreshToken;
}
