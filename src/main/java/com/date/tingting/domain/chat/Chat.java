package com.date.tingting.domain.chat;

import com.date.tingting.domain.RegisterDateBaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat extends RegisterDateBaseTimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int chatId;
    @Column
    private String roomKey;
    @Column
    private String uuid;
    @Column
    private String message;

    @Transient
    private String userId;

    @Builder
    public Chat(String roomKey, String uuid, String message) {
        this.roomKey = roomKey;
        this.uuid = uuid;
        this.message = message;
    }
}
