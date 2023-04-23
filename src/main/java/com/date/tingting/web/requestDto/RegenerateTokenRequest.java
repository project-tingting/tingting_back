package com.date.tingting.web.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegenerateTokenRequest {

    private String refreshToken;
    private String accessToken;

}


