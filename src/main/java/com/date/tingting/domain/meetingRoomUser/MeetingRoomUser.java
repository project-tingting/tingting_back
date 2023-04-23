package com.date.tingting.domain.meetingRoomUser;

import com.date.tingting.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.time.LocalDateTime;


@ToString
@Getter
@Entity
@NoArgsConstructor
@IdClass(MeetingRoomUserId.class)
public class MeetingRoomUser extends BaseTimeEntity {

    @Id
    private String roomKey;

    @Id
    private String uuid;

    @Column
    private String gender;

    @Column
    private String status;


    @Builder
    public MeetingRoomUser(String roomKey, String uuid, String gender, String status) {
        this.roomKey = roomKey;
        this.uuid = uuid;
        this.gender = gender;
        this.status = status;
    }

    @Builder
    public MeetingRoomUser(String roomKey, String uuid, String status) {
        this.roomKey = roomKey;
        this.uuid = uuid;
        this.status = status;
    }


    public void updateMeetingRoomUserStatus(String status) {
        this.status = status;
    }


}
