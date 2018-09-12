package com.school.biz.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jame
 */
public enum ReceiveExpressStatusEnum {

    PROXY_RECIEVED(0, "代理点已签收"),
    WAIT_SELF(1, "等待自提"),
    WAIT_INTO_BOX(2, "等待入柜"),
    ALREADY_INTO_BOX(3, "快件已入柜"),
    TIMEOUT_INTO_BOX(4, "快件入柜超时"),
    FINISHED(5, "已完成"),
    WAIT_PICKUP(6, "等待取件"),
    PICKUP_ING(7, "正在取件"),
    INEFFECTIVE(8, "未生效"),
    CANCEL(9, "已取消"),
    SUPPLEMENT(10, "补单待支付"),
    HAS_SUPPLEMENT(11, "补单已支付");


    private int flag;
    private String message;

    ReceiveExpressStatusEnum(int flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public static ReceiveExpressStatusEnum parseObj(Integer code) {
        for (ReceiveExpressStatusEnum o : ReceiveExpressStatusEnum.values()) {
            if (o.flag == code) {
                return o;
            }
        }
        return null;
    }

    public static Map<Object, String> getAllStatusEnum() {
        Map<Object, String> resultMap = new HashMap<Object, String>();
        ReceiveExpressStatusEnum[] receiveExpressStatusEnums = ReceiveExpressStatusEnum.values();
        for (ReceiveExpressStatusEnum receiveExpressStatusEnum : receiveExpressStatusEnums) {
            resultMap.put(receiveExpressStatusEnum.flag, receiveExpressStatusEnum.message);
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
