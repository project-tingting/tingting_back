package com.date.tingting.web.requestDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
public class ChattingMessageRequest {
    private String uuid;
    private String message;
}
