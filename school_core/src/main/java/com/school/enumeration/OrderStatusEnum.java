package com.school.enumeration;

import java.util.HashMap;
import java.util.List;
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

    PROXY_RECIEVED("proxy_received", "代理处已签收"),
    WAITING_PROXY_OBTAIN("waiting_proxy_obtain", "待从代理处取件"),
    PROXY_OBTAINED("geted", "已从代理处取件"),
    UNPAID("unpaid", "待支付，中间状态，用户不可见"),
    PAID("paid", "已支付"),
    CABINET_RECIEVED("cabinet_received", "已放入柜子"),
    CABINET_OBTAINED("cabinet_obtained", "已从柜子取件"),
    RETRIEVED("retrieved", "已回收");

    private String code;

    private String message;
    
    public static OrderStatusEnum parseObj(String code){
    	for (OrderStatusEnum o : OrderStatusEnum.values()) {
    		if(o.code.equals(code)){
    			return o;
    		}
		}
    	return null;
    }
    
    public static Map<String, String> getAllStatusEnum(){
    	Map<String, String> resultMap = new HashMap<String,String>();
    	OrderStatusEnum[] orderStatusEnums = OrderStatusEnum.values();
    	for (OrderStatusEnum orderStatusEnum : orderStatusEnums) {
			resultMap.put(orderStatusEnum.code, orderStatusEnum.message);
		}
    	return resultMap;
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
}
