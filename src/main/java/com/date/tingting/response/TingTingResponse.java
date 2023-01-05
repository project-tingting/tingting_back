package com.date.tingting.response;

import lombok.Getter;

@Getter
public class TingTingResponse<T> extends CommonResponse {
    T data;
}
