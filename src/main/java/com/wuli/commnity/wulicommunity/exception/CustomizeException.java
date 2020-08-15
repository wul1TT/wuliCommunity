package com.wuli.commnity.wulicommunity.exception;

public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;
    public CustomizeException(CustomizeErrorCode code)
    {
        this.message=code.getMessage();
        this.code=code.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }
    public Integer getCode() { return code; }
}
