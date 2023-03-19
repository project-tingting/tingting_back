package com.date.tingting.web.responseDto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PartyUserResponse {
    private String userId;
    private String invitationState;
}
