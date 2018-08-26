package com.school.biz.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jame
 */
public enum SendExpressStatusEnum {

    CREATE(0, "已发起寄件"),
    WAIT_SMQJ(1, "等待上门取件"),
    PROXY_RECIEVED(2, "代理点已签收"),
    SUPPLEMENT(3, "补单待支付"),
    WAIT_SEND(4, "准备寄出"),
    BE_SEND(5, "已寄出");


    private int flag;
    private String message;

    SendExpressStatusEnum(int flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public static SendExpressStatusEnum parseObj(Integer code) {
        for (SendExpressStatusEnum o : SendExpressStatusEnum.values()) {
            if (o.flag == code) {
                return o;
            }
        }
        return null;
    }

    public static Map<Object, String> getAllStatusEnum() {
        Map<Object, String> resultMap = new HashMap<Object, String>();
        SendExpressStatusEnum[] sendExpressStatusEnums = SendExpressStatusEnum.values();
        for (SendExpressStatusEnum sendExpressStatusEnum : sendExpressStatusEnums) {
            resultMap.put(sendExpressStatusEnum.flag, sendExpressStatusEnum.message);
        }
        return resultMap;
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
