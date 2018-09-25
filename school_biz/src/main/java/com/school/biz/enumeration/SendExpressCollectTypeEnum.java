package com.school.biz.enumeration;

/**
 * @author jame
 * @date 2018/7/31
 * @desc 寄件时揽件方式枚举
 */
public enum SendExpressCollectTypeEnum {

    SELF(0, "自送到点"),
    DOOR(1, "上门揽件");
    private int flag;
    private String message;

    SendExpressCollectTypeEnum(int flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
