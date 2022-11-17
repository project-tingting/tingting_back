package com.date.tingting.response;

import lombok.Getter;

@Getter
public class Response<T> extends CommonResponse {
    T result;
}
