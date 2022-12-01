package com.date.tingting.domain.userProfile;

import com.date.tingting.domain.BaseTimeEntity;
import com.date.tingting.web.requestDto.UserProfileRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.util.List;

import static java.util.stream.Collectors.toList;


@ToString
@Getter
@Entity
@NoArgsConstructor
public class UserProfile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userProfileId;
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

    public static List<UserProfile> of(List<UserProfileRequest> userProfileRequestList) {
        return userProfileRequestList.stream()
                .map(userProfile -> UserProfile.of(userProfile))
                .collect(toList());
    }

    private static UserProfile of(UserProfileRequest userProfileRequest) {
        return UserProfile.builder()
                .uuid(userProfileRequest.getUuid())
                .topic(userProfileRequest.getTopic())
                .value(userProfileRequest.getValue())
                .build();
    }


}
