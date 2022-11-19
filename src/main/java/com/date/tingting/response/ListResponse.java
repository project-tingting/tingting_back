package com.date.tingting.response;

import lombok.Getter;

@Getter
public class ListResponse<T> extends CommonResponse {
    T dataList;
}
