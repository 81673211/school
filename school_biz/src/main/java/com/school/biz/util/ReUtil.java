package com.school.biz.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则相关工具类
 * 
 */
public class ReUtil {
	
	/** 正则表达式匹配中文 */

	private ReUtil() {
		//阻止实例化
	}

	/**
	 * 获得匹配的字符串
	 * 
	 * @param regex 匹配的正则
	 * @param content 被匹配的内容
	 * @param groupIndex 匹配正则的分组序号
	 * @return 匹配后得到的字符串，未匹配返回null
	 */
	public static String get(String regex, String content, int groupIndex) {
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		return get(pattern, content, groupIndex);
	}
	
	/**
	 * 获得匹配的字符串
	 * 
	 * @param pattern 编译后的正则模式
	 * @param content 被匹配的内容
	 * @param groupIndex 匹配正则的分组序号
	 * @return 匹配后得到的字符串，未匹配返回null
	 */
	public static String get(Pattern pattern, String content, int groupIndex) {
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			return matcher.group(groupIndex);
		}
		return null;
	}

}