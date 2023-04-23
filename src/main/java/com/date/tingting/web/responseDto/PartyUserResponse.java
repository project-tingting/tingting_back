package com.date.tingting.web.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PartyUserResponse {
    private String userId;
    private String invitationState;
}
