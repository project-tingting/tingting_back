package com.date.tingting.domain.partyGroup;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity
public class PartyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String host;

    private String guest;

    private String isAccepted;

    @Builder
    public PartyGroup(Long id, String host, String guest, String isAccepted) {
        this.id = id;
        this.host = host;
        this.guest = guest;
        this.isAccepted = isAccepted;
    }

    public void changeIsAccepted(String isAccepted) {
        this.isAccepted = isAccepted;
    }

}
