package com.date.tingting.domain.meetingRoom;

import com.date.tingting.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;


@ToString
@Getter
@Entity
@NoArgsConstructor
public class MeetingRoom extends BaseTimeEntity {

    @Id
    private String roomKey;
    @Column
    private String type;
    @Column
    private String watingExpireDate;

    @Column
    private String chattingRoomExpireDate;
    @Column
    private String isFull;
    @Column
    private String isStart;

    @Builder
    public MeetingRoom(String roomKey, String type, String watingExpireDate, String isFull, String isStart) {
        this.roomKey = roomKey;
        this.type = type;
        this.isFull = isFull;
        this.isStart = isStart;
        this.watingExpireDate = watingExpireDate;
    }
}
