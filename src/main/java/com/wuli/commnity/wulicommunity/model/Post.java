package com.wuli.commnity.wulicommunity.model;

import lombok.Data;

import java.math.BigInteger;
@Data
public class Post {
    private Integer id;
    private String title;
    private String description;
    private long gmt_create;
    private long gmt_modified;
    private Integer creator;
    private Integer comment_count;
    private Integer view_count;
    private Integer like_count;
    private Integer tag;
}
