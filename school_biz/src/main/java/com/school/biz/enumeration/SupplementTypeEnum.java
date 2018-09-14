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
public enum SupplementTypeEnum {

    EXPRESS_AMT(1, "补快递运费"),
    SERVICE_AMT(2, "补配送服务费");

    private Integer code;

    private String message;

    public static SupplementTypeEnum parseObj(Integer code) {
        for (SupplementTypeEnum o : SupplementTypeEnum.values()) {
            if (o.code.equals(code)) {
                return o;
            }
        }
        return null;
    }

    public static Map<Object, String> getAllStatusEnum() {
        Map<Object, String> resultMap = new HashMap<Object, String>();
        SupplementTypeEnum[] orderStatusEnums = SupplementTypeEnum.values();
        for (SupplementTypeEnum orderStatusEnum : orderStatusEnums) {
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
