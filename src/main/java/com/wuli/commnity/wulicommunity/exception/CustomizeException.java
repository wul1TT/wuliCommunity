package com.wuli.commnity.wulicommunity.exception;

public class CustomizeException extends RuntimeException {
    private String message;
    public CustomizeException(CustomizeErrorCode code)
    {
        this.message=code.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
