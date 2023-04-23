package com.date.tingting.web.requestDto;

import com.date.tingting.domain.userProfile.UserProfile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserProfileRequest {

    @ApiModelProperty(example = "유저 uuid")
    private String uuid;

    @ApiModelProperty(example = "토픽")
    private String topic;

    @ApiModelProperty(example = "토픽에 대한 답변")
    private List<String> valueList;
    }


