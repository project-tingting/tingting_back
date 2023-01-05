package com.date.tingting.domain.meetingRoomUser;

import com.date.tingting.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;


@Data
@Embeddable
public class MeetingRoomUserId implements Serializable {

    @Column
    private String roomKey;

    @Column
    private String uuid;
}
