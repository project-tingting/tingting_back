package com.date.tingting.web.responseDto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class MeetingRoomResponse {

    private String roomKey;

    private String type;

    private int manCount;

    private int womanCount;

    private String watingExpireDate;

}


