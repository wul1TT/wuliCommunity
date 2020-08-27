package com.wuli.commnity.wulicommunity.enums;

public enum TypeOfNotification {
    REPLY_POST(1,"回复了文章"),
    REPLY_COMMENT(2,"回复了评论"),
    ;
    private int type;
    private String name;

    TypeOfNotification(int status, String name) {
        this.type = status;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
