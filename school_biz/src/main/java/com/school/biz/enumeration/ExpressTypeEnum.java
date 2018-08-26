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
public enum ExpressTypeEnum {

    SEND(0, "send", "寄件"),
    RECEIVE(1, "receive", "收件");

    private int flag;
    private String code;
    private String message;

    ExpressTypeEnum() {
    }

    ExpressTypeEnum(int flag, String code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public static Map<Object, String> getAllTypeEnum(){
    	Map<Object, String> resultMap = new HashMap<Object,String>();
    	ExpressTypeEnum[] expressTypeEnums = ExpressTypeEnum.values();
    	for (ExpressTypeEnum expressTypeEnum : expressTypeEnums) {
			resultMap.put(expressTypeEnum.flag, expressTypeEnum.message);
		}
    	return resultMap;
    }
}
