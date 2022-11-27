package com.date.tingting.web.requestDto;

import com.date.tingting.domain.emailAuth.EmailAuth;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailAuthRequest {
    private String userEmail;
    private String authToken;
    private Boolean expired;

    public EmailAuth toEntity() {
        return EmailAuth.builder()
                .userEmail(userEmail)
                .authToken(authToken)
                .expired(expired)
                .build();
    }
}
