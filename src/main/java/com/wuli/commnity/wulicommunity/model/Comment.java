package com.wuli.commnity.wulicommunity.model;

import lombok.Data;

@Data
public class Comment {
    private Integer id;
    private Integer post_id;
    private Integer type;
    private Integer commentator;
    private long gmt_create;
    private Integer like_count;
    private String content;
    private Integer comment_count;
}
