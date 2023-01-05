package com.date.tingting.domain.chattingMessage;

import com.date.tingting.domain.RegisterDateBaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
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

    @Transient
    private String userId;

    @Builder
    public ChattingMessage(String roomKey, String uuid, String message) {
        this.roomKey = roomKey;
        this.uuid = uuid;
        this.message = message;
    }
}
