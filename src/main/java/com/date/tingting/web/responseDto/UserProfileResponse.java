package com.date.tingting.web.responseDto;

import lombok.*;
import java.util.List;

@Data
public class UserProfileResponse {
    private String topic;
    private List<String> valueList;
}
