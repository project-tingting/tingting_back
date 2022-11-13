package com.date.tingting.handler.exception;

public class InValidRequest extends CommonException{
    private static final String MESSAGE = "잘못된 요청입니다.";
    public String filedName;
    public String message;

    public InValidRequest() {
        super(MESSAGE);
    }

    public InValidRequest(String filedName, String message) {
        super(MESSAGE);
        addValidation(filedName, message);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
