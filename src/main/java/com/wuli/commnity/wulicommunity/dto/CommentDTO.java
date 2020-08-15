package com.wuli.commnity.wulicommunity.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Integer post_id;
    private String content;
    private Integer type;
}
