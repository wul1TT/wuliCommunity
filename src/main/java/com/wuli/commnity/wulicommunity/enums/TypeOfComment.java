package com.wuli.commnity.wulicommunity.enums;

public enum TypeOfComment {
    POST(1),
    COMMENT(2);
    private Integer type;
     TypeOfComment(Integer type)
    {
        this.type=type;
    }

    public static boolean isExist(Integer type) {
        for (TypeOfComment value : TypeOfComment.values()) {
            if(type==value.getType())
                return true;
        }
        return false;
    }

    public Integer getType() {
        return type;
    }
}
