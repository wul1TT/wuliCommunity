package com.wuli.commnity.wulicommunity.exception;

public enum CustomizeErrorCode implements InterfaceCustomizeErrorCode{
    POST_NOT_FOUND("你要找的页面不存在");
    private String message;
    @Override
    public String getMessage() {
        return message;
    }
    CustomizeErrorCode(String message)
    {
        this.message=message;
    }
}
