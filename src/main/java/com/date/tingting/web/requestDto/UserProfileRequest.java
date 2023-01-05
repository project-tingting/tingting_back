package com.date.tingting.web.requestDto;

import com.date.tingting.domain.userProfile.UserProfile;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserProfileRequest {
    private String uuid;
    private String topic;
    private List<String> valueList;
    }


