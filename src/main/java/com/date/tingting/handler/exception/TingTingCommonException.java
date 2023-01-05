package com.date.tingting.handler.exception;

public class TingTingCommonException extends CommonException{

    public TingTingCommonException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
