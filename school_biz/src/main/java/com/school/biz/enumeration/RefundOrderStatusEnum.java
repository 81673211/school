package com.school.biz.enumeration;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 07/06/2018 13:01
 */
@AllArgsConstructor
@Getter
public enum RefundOrderStatusEnum {

    REFUNDING(0,"退款处理中"),
    SUCCESS(1,"退款成功"),
    FAILED(2,"退款失败");

    private Integer code;

    private String message;
    
    public static RefundOrderStatusEnum parseObj(Integer code){
    	for (RefundOrderStatusEnum o : RefundOrderStatusEnum.values()) {
    		if(o.code.equals(code)){
    			return o;
    		}
		}
    	return null;
    }
    
    public static Map<Object, String> getAllStatusEnum(){
    	Map<Object, String> resultMap = new HashMap<Object,String>();
    	RefundOrderStatusEnum[] orderStatusEnums = RefundOrderStatusEnum.values();
    	for (RefundOrderStatusEnum orderStatusEnum : orderStatusEnums) {
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
