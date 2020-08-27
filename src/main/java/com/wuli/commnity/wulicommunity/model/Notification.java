package com.wuli.commnity.wulicommunity.model;

import lombok.Data;

@Data
public class Notification {
    private Integer id;
    private Integer notifier;
    private Integer receiver;
    private Integer outerId;
    private Integer type;
    private long gmt_create;
    private Integer status;
    private String outerTitle;
}
