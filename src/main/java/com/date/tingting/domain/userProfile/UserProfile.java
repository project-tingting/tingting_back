package com.date.tingting.domain.userProfile;

import com.date.tingting.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@ToString
@Getter
@Entity
public class UserProfile extends BaseTimeEntity {

    @Id
    @Column
    private Long UserProfileId;
    @Column
    private String uuid;

    @Column
    private String topic;

    @Column
    private String value;

    @Builder
    public UserProfile(String uuid, String topic, String value) {
        this.uuid = uuid;
        this.topic = topic;
        this.value = value;
    }


    public UserProfile() {
    }

}
