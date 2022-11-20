package com.date.tingting.response;

import org.springframework.stereotype.Service;

@Service
public class ResponseService{

    public<T> TingTingResponse<T> getTingTingResponse(T data){
        TingTingResponse tingTingResponse = new TingTingResponse();
        tingTingResponse.data=data;
        setSuccessResponse(tingTingResponse);
        return tingTingResponse;
    }

    void setSuccessResponse(CommonResponse commonResponse){
        commonResponse.code =200;
        commonResponse.success=true;
        commonResponse.message="SUCCESS";
    }
}
