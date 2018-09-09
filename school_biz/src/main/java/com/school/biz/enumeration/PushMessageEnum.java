package com.school.biz.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jame
 * @date 2018/9/9
 * @desc description
 */
@AllArgsConstructor
@Getter
public enum PushMessageEnum {

    SEND_INEFFECTIVE(1, "发起寄件未支付"),
    SEND_SUPPLEMENT(2, "寄件补单未支付"),
    RECEIVE_INEFFECTIVE(3, "收件未支付"),
    RECEIVE_SUPPLEMENT(4, "收件补单未支付"),
    ;
    private Integer code;

    private String message;

    public static PushMessageEnum parseObj(Integer code) {
        for (PushMessageEnum o : PushMessageEnum.values()) {
            if (o.code.equals(code)) {
                return o;
            }
        }
        return null;
    }

    public static Map<Object, String> getAllStatusEnum() {
        Map<Object, String> resultMap = new HashMap<Object, String>();
        PushMessageEnum[] orderStatusEnums = PushMessageEnum.values();
        for (PushMessageEnum orderStatusEnum : orderStatusEnums) {
            resultMap.put(orderStatusEnum.code, orderStatusEnum.message);
        }
        return resultMap;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
