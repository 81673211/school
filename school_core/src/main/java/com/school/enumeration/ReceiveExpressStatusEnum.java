package com.school.enumeration;

/**
 * @author jame
 */
public enum ReceiveExpressStatusEnum {

    PROXY_RECIEVED(0, "代理点已签收"),
    WAIT_SELF(1, "等待自提"),
    WAIT_INTO_BOX(2, "等待入柜"),
    ALREADY_INTO_BOX(3, "快件已入柜"),
    TIMEOUT_INTO_BOX(4, "快件入柜超时"),
    FINISHED(5, "已完成");
    private int flag;
    private String message;

    ReceiveExpressStatusEnum(int flag, String message) {
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
