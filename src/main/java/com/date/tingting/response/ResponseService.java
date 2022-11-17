package com.date.tingting.response;

import org.springframework.stereotype.Service;

@Service
public class ResponseService{

    public<T> Response<T> getResponse(T data){
        Response response = new Response();
        response.result=data;
        setSuccessResponse(response);
        return response;
    }

    void setSuccessResponse(CommonResponse commonResponse){
        commonResponse.code =200;
        commonResponse.success=true;
        commonResponse.message="SUCCESS";
    }
}
