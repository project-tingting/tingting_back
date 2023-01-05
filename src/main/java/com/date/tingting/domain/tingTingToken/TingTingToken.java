package com.date.tingting.domain.tingTingToken;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


@ToString
@Getter
@Entity
@NoArgsConstructor
public class TingTingToken {

    @Id
    @Column
    private String uuid;
    @Column
    private int tokenCount;


    @Builder
    public TingTingToken(String uuid, int tokenCount) {
        this.uuid = uuid;
        this.tokenCount = tokenCount;
    }
}
