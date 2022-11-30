package com.date.tingting.web.requestDto;

import com.date.tingting.domain.userProfile.UserProfile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileRequest {
    private String uuid;
    private String topic;
    private String value;

    public UserProfile toEntity() {
        return UserProfile.builder()
                .uuid(uuid)
                .topic(topic)
                .value(value)
                .build();
    }

}
