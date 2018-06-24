package com.school.enumeration;

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
public enum OrderStatusEnum {

    UNPAY(0, "未支付"),
    SUCCESS(1, "支付成功"),
    FAILED(2, "支付失败"),
    PAYING(3, "支付处理中"),
    EXPIRED(4, "已过期");

    private Integer code;

    private String message;
    
    public static OrderStatusEnum parseObj(String code){
    	for (OrderStatusEnum o : OrderStatusEnum.values()) {
    		if(o.code.equals(code)){
    			return o;
    		}
		}
    	return null;
    }
    
    public static Map<Object, String> getAllStatusEnum(){
    	Map<Object, String> resultMap = new HashMap<Object,String>();
    	OrderStatusEnum[] orderStatusEnums = OrderStatusEnum.values();
    	for (OrderStatusEnum orderStatusEnum : orderStatusEnums) {
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
