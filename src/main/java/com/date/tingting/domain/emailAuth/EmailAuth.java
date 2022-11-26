package com.date.tingting.domain.emailAuth;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAuth {

    private static final Long MAX_EXPIRE_TIME = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userEmailAuthId;

    private String userEmail;
    private String authToken;
    private Boolean expired;
    private LocalDateTime expireDate;

    @Builder
    public EmailAuth(String userEmail, String authToken, Boolean expired) {
        this.userEmail = userEmail;
        this.authToken = authToken;
        this.expired = expired;
        this.expireDate = LocalDateTime.now().plusMinutes(MAX_EXPIRE_TIME);
    }

    public void useToken() {
        this.expired = true;
    }
}