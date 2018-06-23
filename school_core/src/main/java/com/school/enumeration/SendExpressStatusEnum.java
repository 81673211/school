package com.school.enumeration;

/**
 * @author jame
 */
public enum SendExpressStatusEnum {

    CREATE(0, "已发起寄件"),
    PROXY_RECIEVED(1, "代理点已签收"),
    BE_SEND(2, "已寄出");


    private int flag;
    private String message;

    SendExpressStatusEnum(int flag, String message) {
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
