package com.wuli.commnity.wulicommunity.model;

public class User {
    private Integer id;
    private String account_id;
    private String name;
    private String token;
    private String avatarUri;
    private long time_create;
    private long time_update;
    public String getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }

    public void setTime_create(long time_create) {
        this.time_create = time_create;
    }

    public void setTime_update(long time_update) {
        this.time_update = time_update;
    }

    public long getTime_create() {
        return time_create;
    }

    public long getTime_update() {
        return time_update;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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
