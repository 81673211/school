package com.school.biz.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>Description:快递类型枚举.</b><br>
 *
 * @author <b>sil.zhou</b>
 *         <br><b>ClassName:ExpressTypeEnum</b>
 *         <br><b>Date:</b> 07/06/2018 14:55
 */
public enum ReceiveExpressTypeEnum {

    PROXY_RECEIVE(0, "快递点收件"),
    HELP_RECEIVE(1, "帮我收件");

    private int flag;
    private String message;

    ReceiveExpressTypeEnum() {
    }

    ReceiveExpressTypeEnum(int flag, String message) {
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

    public static Map<Object, String> getAllTypeEnum() {
        Map<Object, String> resultMap = new HashMap<Object, String>();
        ReceiveExpressTypeEnum[] expressTypeEnums = ReceiveExpressTypeEnum.values();
        for (ReceiveExpressTypeEnum expressTypeEnum : expressTypeEnums) {
            resultMap.put(expressTypeEnum.flag, expressTypeEnum.message);
        }
        return resultMap;
    }
}
