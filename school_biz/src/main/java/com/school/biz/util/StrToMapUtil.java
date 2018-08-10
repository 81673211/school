package com.school.biz.util;

import java.util.HashMap;
import java.util.Map;

public class StrToMapUtil {
	/** 
	 * 方法名称:transStringToMap 
	 * 传入参数:mapString
	 * 返回值:Map 
	 */  
	public static Map<String,String> transStringToMap(String str){  
		str = str.substring(1, str.length()-1);
		Map<String, String> map = new HashMap<String, String>();
		String[] data = str.split(",");
		for (int i = 0; i < data.length; i++) {
			if(data[i].split("=").length>=2){
				map.put(data[i].split("=")[0].trim(),data[i].split("=")[1].trim());
			}
		}
		return map;
	} 
}
