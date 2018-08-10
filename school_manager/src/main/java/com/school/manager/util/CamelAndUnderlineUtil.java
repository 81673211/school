package com.school.manager.util;

//驼峰结构和数据库下划线相互转换
public class CamelAndUnderlineUtil {

	private static final char UNDERLINE = '_';

	private CamelAndUnderlineUtil() {
	}

	public static String camelToUnderline(String param) {
		
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

}
