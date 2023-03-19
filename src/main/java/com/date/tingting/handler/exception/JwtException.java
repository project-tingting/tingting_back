package com.date.tingting.handler.exception;

public class JwtException extends CommonException{

    public JwtException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
