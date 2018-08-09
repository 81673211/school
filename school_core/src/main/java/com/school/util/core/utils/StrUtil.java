package com.school.util.core.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 字符串工具类 
 */
public class StrUtil {

	public static final String DOT = ".";

	public static final String EMPTY = "";

	/**
	 * 字符串是否为空白 空白的定义如下： <br>
	 * 1、为null <br>
	 * 2、为不可见字符（如空格）<br>
	 * 3、""<br>
	 * 
	 * @param str 被检测的字符串
	 * @return 是否为空
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}
	
	
	public static boolean isBlank(Number number) {
		return (number == null || number.intValue() == 0);
	}
	
	/**
	 * 字符串是否为非空白 空白的定义如下： <br>
	 * 1、不为null <br>
	 * 2、不为不可见字符（如空格）<br>
	 * 3、不为""<br>
	 * 
	 * @param str 被检测的字符串
	 * @return 是否为非空
	 */
	public static boolean isNotBlank(String str) {
		return false == isBlank(str);
	}

	/**
	 * 字符串是否为空，空的定义如下
	 * 1、为null <br>
	 * 2、为""<br>
	 * @param str 被检测的字符串
	 * @return 是否为空
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 去除字符串两边的空格符，如果为null返回null
	 * @param str 字符串
	 * @return 处理后的字符串
	 */
	public static String trim(String str) {
		return (null == str) ? null : str.trim();
	}

	/**
	 * 去掉首部指定长度的字符串并将剩余字符串首字母小写<br/>
	 * 例如：str=setName, preLength=3 -> return name
	 * @param str 被处理的字符串
	 * @param preLength 去掉的长度
	 * @return 处理后的字符串，不符合规范返回null
	 */
	public static String cutPreAndLowerFirst(String str, int preLength) {
		if(str == null) {
			return null;
		}
		if(str.length() > preLength) {
			char first = Character.toLowerCase(str.charAt(preLength));
			if(str.length() > preLength +1) {
				return first + str.substring(preLength +1);
			}
			return String.valueOf(first);
		}
		return null;
	}
	
	/**
	 * 原字符串首字母大写并在其首部添加指定字符串
	 * 例如：str=name, preString=get -> return getName
	 * @param str 被处理的字符串
	 * @param preString 添加的首部
	 * @return 处理后的字符串
	 */
	public static String upperFirstAndAddPre(String str, String preString) {
		if(str == null || preString == null) {
			return null;
		}
		return preString + upperFirst(str);
	}
	
	/**
	 * 大写首字母<br>
	 * 例如：str = name, return Name
	 * @param str 字符串
	 * @return 字符串
	 */
	public static String upperFirst(String str) {
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}
	

	/**
	 * 切分字符串<br/>
	 * a#b#c -> [a,b,c]
	 * a##b#c -> [a,"",b,c]
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @return 切分后的集合
	 */
	public static List<String> split(String str, char separator) {
		return split(str, separator, 0);
	}
	
	/**
	 * 切分字符串
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @param limit 限制分片数
	 * @return 切分后的集合
	 */
	public static List<String> split(String str, char separator, int limit){
		if(str == null) {
			return null;
		}
		List<String> list = new ArrayList<String>(limit == 0 ? 16 : limit);
		if(limit == 1) {
			list.add(str);
			return list;
		}
		
		boolean isNotEnd = true;	//未结束切分的标志
		int strLen = str.length();
		StringBuilder sb = new StringBuilder(strLen);
		for(int i=0; i < strLen; i++) {
			char c = str.charAt(i);
			if(isNotEnd && c == separator) {
				list.add(sb.toString());
				//清空StringBuilder
				sb.delete(0, sb.length());
				
				//当达到切分上限-1的量时，将所剩字符全部作为最后一个串
				if(limit !=0 && list.size() == limit-1) {
					isNotEnd = false;
				}
			}else {
				sb.append(c);
			}
		}
		list.add(sb.toString());
		return list;
	}
	
	/**
	 * 切分字符串<br>
	 * from jodd
	 * @param str 被切分的字符串
	 * @param delimiter 分隔符
	 * @return 字符串
	 */
	public static String[] split(String str, String delimiter) {
		if(str == null) {
			return null;
		}
		if(str.trim().length() == 0) {
			return new String[]{str};
		}
		
		int dellen = delimiter.length();	//del length
		int maxparts = (str.length() / dellen) + 2;		// one more for the last
		int[] positions = new int[maxparts];

		int i, j = 0;
		int count = 0;
		positions[0] = - dellen;
		while ((i = str.indexOf(delimiter, j)) != -1) {
			count++;
			positions[count] = i;
			j = i + dellen;
		}
		count++;
		positions[count] = str.length();

		String[] result = new String[count];

		for (i = 0; i < count; i++) {
			result[i] = str.substring(positions[i] + dellen, positions[i + 1]);
		}
		return result;
	}
	
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
	 * 切割后部分
	 * @param string 字符串
	 * @param fromIndex 切割开始的位置（包括）
	 * @return 切割后的字符串
	 */
	public static String subSuf(String string, int fromIndex) {
		if(isEmpty(string)) {
			return null;
		}
		return sub(string, fromIndex, string.length());
	}
	
	/**
	 * 重复某个字符
	 * @param c 被重复的字符
	 * @param count 重复的数目
	 * @return 重复字符字符串
	 */
	public static String repeat(char c, int count) {
		char[] result = new char[count];
		for (int i = 0; i < count; i++) {
			result[i] = c;
		}
		return new String(result);
	}
	
	/**
	 * 重复某个字符串
	 * @param str 被重复的字符
	 * @param count 重复的数目
	 * @return 重复字符字符串
	 */
	public static String repeat(String str, int count) {
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * 格式化文本
	 * @param template 文本模板，被替换的部分用 {} 表示
	 * @param values 参数值
	 * @return 格式化后的文本
	 */
	public static String format(String template, Object... values) {
		if(ArrayUtils.isEmpty(values) || isBlank(template)) {
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
	 * 编码字符串
	 * @param str 字符串
	 * @param charset 字符集
	 * @return 编码后的字节码
	 */
	public static byte[] encode(String str, String charset) {
		if(str == null) {
			return null;
		}
		
		try {
			return str.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(format("Charset [{}] unsupported!", charset));
		}
	}
	
	/**
	 * 解码字节码
	 * @param data 字符串
	 * @param charset 字符集
	 * @return 解码后的字符串
	 */
	public static String decode(byte[] data, String charset) {
		if(data == null) {
			return null;
		}
		
		try {
			return new String(data, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(format("Charset [{}] unsupported!", charset));
		}
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
