package com.wuli.commnity.wulicommunity.model;

import lombok.Data;
import lombok.Setter;

@Data
public class User {
    private Integer id;
    private String account_id;
    private String name;
    private String token;
    private String avatarUri;
    private long time_create;
    private long time_update;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account_id='" + account_id + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", time_create=" + time_create +
                ", time_update=" + time_update +
                '}';
    }
}
