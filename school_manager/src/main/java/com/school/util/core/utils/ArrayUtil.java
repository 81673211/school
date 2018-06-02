package com.school.util.core.utils;

import java.util.List;


public class ArrayUtil {



	/**
	 * 判断所给数组是否为空。
	 * 
	 * @return 如果为<code>null</code>或长度为0，返回<code>true</code>；否则返回
	 *         <code>false</code>.
	 */
	public static boolean isEmpty(double[] array) {
		return (array == null || array.length == 0);
	}

	/**
	 * 将指定的list以token为连接符进行字符串拼接并返回
	 * 
	 * @param list
	 * @param token
	 * @return
	 */
	public static String toString(List<String> list, String token) {
		if (list == null || list.size() == 0) {
			return "";
		}
		if (null == token) {
			token = "";
		}
		StringBuffer returnStr = new StringBuffer();
		boolean isBegin = true;
		for (String str : list) {
			if (StringHelper.isEmpty(str)) {
				continue;
			}
			if (isBegin) {
				returnStr.append(str);
				isBegin = false;
				continue;
			}
			returnStr = returnStr.append(token).append(str);
		}
		return returnStr.toString();
	}

}
