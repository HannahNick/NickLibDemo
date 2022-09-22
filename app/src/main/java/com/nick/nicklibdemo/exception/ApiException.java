package com.nick.nicklibdemo.exception;

/**
 * Created by Nick on 2017/10/7.
 */

public class ApiException extends RuntimeException {
    private String errorCode;

    public ApiException(String code, String msg) {
        super(msg);
        this.errorCode = code;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
