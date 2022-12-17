package com.date.tingting.web.responseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class MeetingRoomResponse {

    private String roomKey;

    private int manCount;

    private int womanCount;

    private String type;

    private String roomReadyStatus;

    private String watingExpireDate;

}


