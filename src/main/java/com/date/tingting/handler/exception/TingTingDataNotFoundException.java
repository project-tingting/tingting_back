package com.date.tingting.handler.exception;

public class TingTingDataNotFoundException extends CommonException{
    private static final String MESSAGE = "데이터가 존재하지 않습니다.";
    public String filedName;
    public String message;

    public TingTingDataNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 204;
    }
}
