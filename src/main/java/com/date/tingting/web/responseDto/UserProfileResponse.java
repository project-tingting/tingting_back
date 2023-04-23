package com.date.tingting.web.responseDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.util.List;

@Data
public class UserProfileResponse {
    @ApiModelProperty(example = "토픽")
    private String topic;
    @ApiModelProperty(example = "토픽에 대한 답변")
    private List<String> valueList;
}
