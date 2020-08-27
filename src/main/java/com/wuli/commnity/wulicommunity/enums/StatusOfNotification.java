package com.wuli.commnity.wulicommunity.enums;

public enum  StatusOfNotification {
    UNREAD(0),
    READ(1)
    ;
    private Integer status;

    StatusOfNotification(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
