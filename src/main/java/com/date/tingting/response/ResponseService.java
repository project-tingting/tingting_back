package com.date.tingting.response;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService{

    public<T> SingleResponse<T> getSingleResponse(T data){
        SingleResponse singleResponse = new SingleResponse();
        singleResponse.data=data;
        setSuccessResponse(singleResponse);
        return singleResponse;
    }

    public<T> ListResponse<T> getListResponse(List<T> dataList){
        ListResponse listResponse = new ListResponse();
        listResponse.dataList=dataList;
        setSuccessResponse(listResponse);
        return listResponse;
    }

    void setSuccessResponse(CommonResponse commonResponse){
        commonResponse.code =200;
        commonResponse.success=true;
        commonResponse.message="SUCCESS";
    }
}
