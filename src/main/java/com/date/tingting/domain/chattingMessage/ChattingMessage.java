package com.date.tingting.domain.chattingMessage;

import com.date.tingting.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChattingMessage extends BaseTimeEntity {
    @Id
    private String roomKey;

    private String uuid;

    private String message;

    private LocalDateTime registerDate;

    @Builder
    public ChattingMessage(String roomKey, String uuid, String message, LocalDateTime registerDate) {
        this.roomKey = roomKey;
        this.uuid = uuid;
        this.message = message;
        this.registerDate = registerDate;
    }
}
