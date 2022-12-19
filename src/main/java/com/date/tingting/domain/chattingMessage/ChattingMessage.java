package com.date.tingting.domain.chattingMessage;

import com.date.tingting.domain.RegisterDateBaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChattingMessage extends RegisterDateBaseTimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column
    private String roomKey;
    @Column
    private String uuid;
    @Column
    private String message;

    @Builder
    public ChattingMessage(String roomKey, String uuid, String message) {
        this.roomKey = roomKey;
        this.uuid = uuid;
        this.message = message;
    }
}
