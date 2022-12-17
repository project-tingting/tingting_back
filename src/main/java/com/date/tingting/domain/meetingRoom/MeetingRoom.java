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
    private int manCount;

    @Column
    private int womanCount;

    @Column
    private String watingExpireDate;

    @Column
    private String chattingRoomExpireDate;

    @Builder
    public MeetingRoom(String roomKey, String type, int manCount, int womanCount, String watingExpireDate) {
        this.roomKey = roomKey;
        this.type = type;
        this.manCount = manCount;
        this.womanCount = womanCount;
        this.watingExpireDate = watingExpireDate;
    }
}
