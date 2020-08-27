package com.wuli.commnity.wulicommunity.exception;

public enum CustomizeErrorCode implements InterfaceCustomizeErrorCode{
    NO_LOG_IN("未登录，请先登录",2000),
    POST_NOT_FOUND("你要找的内容不存在或者已经被删除",2001),
    TARGET_NOT_FOUND("没有选中要评论的目标或该目标已被删除",2002),
    SYS_ERROR("服务器撑不住了>_<!!!",2003),
    COMMENT_TYPE_WRONG("评论类型不正确或不存在",2004),
    COMMENT_NOT_FOUND("该评论已被删除或不存在",2005),
    CONTENT_IS_EMPTY("评论内容为空",2006 ),
    CESHI("测试返回Json",2007);
    private Integer code;
    private String message;
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(String message,Integer code)
    {
        this.message=message;this.code=code;
    }
}
