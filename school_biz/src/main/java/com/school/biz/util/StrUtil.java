package com.school.biz.util;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类 
 */
public class StrUtil {

	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6',
										   '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };


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
			return StringUtils.EMPTY;
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
	 * 将给定的字节数组用十六进制字符串表示.
	 */
	public static String toString(byte[] data) {
		if (data == null) {
			return "null!";
		}
		int l = data.length;

		char[] out = new char[l << 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}

		return new String(out);
	}

}
