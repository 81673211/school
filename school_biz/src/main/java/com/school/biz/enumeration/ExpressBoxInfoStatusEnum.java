package com.school.biz.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * 柜子取件状态
 */
public enum ExpressBoxInfoStatusEnum {

    WAIT(0, "未取件"),
    DONE(1, "已取走");

    private int flag;
    private String message;

    ExpressBoxInfoStatusEnum() {
    }

    ExpressBoxInfoStatusEnum(int flag, String message) {
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
    
    public static Map<Object, String> getAllStatusEnum(){
    	Map<Object, String> resultMap = new HashMap<Object,String>();
    	ExpressBoxInfoStatusEnum[] expressBoxInfoStatusEnums = ExpressBoxInfoStatusEnum.values();
    	for (ExpressBoxInfoStatusEnum expressBoxInfoStatusEnum : expressBoxInfoStatusEnums) {
			resultMap.put(expressBoxInfoStatusEnum.flag, expressBoxInfoStatusEnum.message);
		}
    	return resultMap;
    }
}
