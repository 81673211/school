package com.school.util.core.utils;

import javax.servlet.http.HttpServletRequest;


public class RequestUtil {

	/**
	 * 获取指定request的指定参数的整数值.
	 * 
	 * @param request
	 *            指定request
	 * @param param
	 *            指定参数
	 * @return 参数值的整数形式. 如果该参数不存在或者解析整数时发生了异常, 则返回0.
	 */
	public static int getParameterAsInt(HttpServletRequest request, String param) {
	    return getParameterAsInt(request, param, 0);
	}
	
	/**
	 * 获取指定request的指定参数的整数值. 如果该参数不存在或者解析整数时发生了异常, 则返回给定的默认值.
	 * 
	 * @param request
	 *            指定request
	 * @param param
	 *            指定参数
	 * @param defaultValue
	 *            给定的默认值.
	 * @return 参数值的整数形式. 如果该参数不存在或者解析整数时发生了异常, 则返回给定的默认值.
	 */
	public static int getParameterAsInt(HttpServletRequest request,
	        String param, int defValue) {
		String result = request.getParameter(param);
		if (result == null) {
			return defValue;
		}
		result = result.trim();
		if (result.length() == 0) {
			return defValue;
		}
		try {
			return Integer.parseInt(result);
		} catch (Exception e) {
			return defValue;
		}
	}
	
	/**
	 * 返回给定的Request对象中的指定参数的取值(String值), <b>不做任何编码转换</b>. 如果Request对象中找不到该指定参数, 则返回给定的默认值.
	 * 
	 * @param request
	 *            给定的Request对象
	 * @param param
	 *            指定参数
	 * @param defaultValue
	 *            默认取值
	 * @return 给定的Request对象中的指定参数的值
	 */
	public static String getParameter(HttpServletRequest request, String param, String defaultValue) {
		return getParameterAndTrim(request, param, defaultValue, -1);
	}
	
	/**
	 * 获取指定request的指定参数的值并作trim处理. 如果不存在该参数, 返回"".
	 * 
	 * @return 指定参数的值并作trim处理
	 */
	public static String getParameterAndTrim(HttpServletRequest req,
			String param, String defaultValue, int maxLength) {
		String result = req.getParameter(param);
		result = (result == null) ? defaultValue : result.trim();
		if (maxLength != -1 && result.length() >= maxLength) {
			throw new IllegalArgumentException("Param[" + param
					+ "]'s value greater than maxLength(" + maxLength + ")");
		}
		return result;
	}
	
	/**
	 * 获取给定参数名的取值，以int数组返回.
	 * 
	 * @return int数组; 如该参数不存在或不是int序列，则返回<code>int[0]</code>.
	 */
	public static int[] getParameterAsIntArray(HttpServletRequest request,
			String param) {
		String[] valuesStr = request.getParameterValues(param);
		if (valuesStr == null) {
			return new int[0];
		}
	
		int[] valuesInt = new int[valuesStr.length];
		for (int i = 0; i < valuesStr.length; i++) {
			valuesInt[i] = Integer.parseInt(valuesStr[i]);
		}
		return valuesInt;
	}
	
}
