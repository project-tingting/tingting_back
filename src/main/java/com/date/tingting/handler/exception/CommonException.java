package com.date.tingting.handler.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class CommonException extends RuntimeException{
    public final Map<String, String> validation = new HashMap<>();

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String filedName, String message) {
        validation.put(filedName, message);
    }
}
