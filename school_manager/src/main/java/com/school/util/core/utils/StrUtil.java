package com.school.util.core.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类 
 */
public class StrUtil {

	public static final String EMPTY = "";

	
	/**
	 * 改进JDK subString<br>
	 * index从0开始计算，最后一个字符为-1<br>
	 * 如果from和to位置一样，返回 ""
	 * example:
	 * 		abcdefgh 2 3 -> c
	 * 		abcdefgh 2 -3 -> cde
	 * @param string String
	 * @param fromIndex 开始的index（包括）
	 * @param toIndex 结束的index（不包括）
	 * @return 字串
	 */
	public static String sub(String string, int fromIndex, int toIndex) {
		int len = string.length();

		if (fromIndex < 0) {
			fromIndex = len + fromIndex;

			if (toIndex == 0) {
				toIndex = len;
			}
		}

		if (toIndex < 0) {
			toIndex = len + toIndex;
		}
		
		if(toIndex < fromIndex) {
			int tmp = fromIndex;
			fromIndex = toIndex;
			toIndex = tmp;
		}
		
		if(fromIndex == toIndex) {
			return EMPTY;
		}

		char[] strArray = string.toCharArray();
		char[] newStrArray = Arrays.copyOfRange(strArray, fromIndex, toIndex);
		return new String(newStrArray);
	}

	/**
	 * 格式化文本
	 * @param template 文本模板，被替换的部分用 {} 表示
	 * @param values 参数值
	 * @return 格式化后的文本
	 */
	public static String format(String template, Object... values) {
		if(ArrayUtils.isEmpty(values) || StringUtils.isBlank(template)) {
			return template;
		}
		
		final StringBuilder sb = new StringBuilder();
		final int length = template.length();
		
		int valueIndex = 0;
		char currentChar;
		for(int i = 0; i < length; i++) {
			if(valueIndex >= values.length) {
				sb.append(sub(template, i, length));
				break;
			}
			
			currentChar = template.charAt(i);
			if(currentChar == '{') {
				final char nextChar = template.charAt(++i);
				if(nextChar == '}') {
					sb.append(values[valueIndex ++]);
				}else {
					sb.append('{').append(nextChar);
				}
			}else {
				sb.append(currentChar);
			}
			
		}
		
		return sb.toString();
	}
	
	/**
	 * 格式化文本
	 * @param template 文本模板，被替换的部分用 {key} 表示
	 * @param map 参数值对
	 * @return 格式化后的文本
	 */
	public static String format(String template, Map<?, ?> map) {
		if(null == map || map.isEmpty()) {
			return template;
		}
		
		for (Entry<?, ?> entry : map.entrySet()) {
			template = template.replace("{" + entry.getKey() + "}", entry.getValue().toString());
		}
		return template;
	}
	
	/**
	 * 将多个对象字符化<br>
	 * 每个对象字符化后直接拼接，无分隔符
	 * @param objs 对象数组
	 * @return 字符串
	 */
	public static String str(Object... objs) {
		StringBuilder sb = new StringBuilder();
		for (Object obj : objs) {
			sb.append(obj);
		}
		return sb.toString();
	}
}
