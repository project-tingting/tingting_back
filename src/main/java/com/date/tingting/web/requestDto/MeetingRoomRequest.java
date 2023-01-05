package com.date.tingting.web.requestDto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MeetingRoomRequest {

    private String roomKey;

    private String type;

    private int manCount;

    private int womanCount;
}


