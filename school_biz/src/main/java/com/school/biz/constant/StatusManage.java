package com.school.biz.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 *        
 * 类名称：StatusManage    
 * 类描述：状态常量类
 * 创建人：xiexu    
 * 创建时间：2016年7月28日 下午4:40:00    
 * 最后修改人：xiexu    
 * 最后修改时间：2016年7月28日 下午4:40:00    
 * 修改备注：    
 * @version 1.0    
 *
 */
public class StatusManage {
	

	
	/**
	 * 用户状态：0-有效
	 */
	public static final Integer ADMIN_USER_STATUS_ON = 0;
	
	/**
	 * 用户状态：1-失效
	 */
	public static final Integer ADMIN_USER_STATUS_OFF = 1;
	
	public static final Map<Integer, String> adminUserStatusMap = new HashMap<Integer, String>();
	

	
	static {
		adminUserStatusMap.put(ADMIN_USER_STATUS_ON, "有效");
		adminUserStatusMap.put(ADMIN_USER_STATUS_OFF, "失效");
	}
	
}
