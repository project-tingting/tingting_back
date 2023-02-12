package com.date.tingting.web.requestDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MeetingRoomRequest {

    @ApiModelProperty(example = "룸 고유키")
    private String roomKey;

    @ApiModelProperty(example = "매칭 인원 타입")
    private String type;

    @ApiModelProperty(example = "방에 입장한 남성 카운트")
    private int manCount;

    @ApiModelProperty(example = "방에 입장한 여상 카운트")
    private int womanCount;
}


