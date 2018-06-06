package com.school.util.core.utils.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
	/**
	 * 移动电话
	 * @param mobiles 号码
	 * @return
	 */
	public static boolean isMobileNumber(String mobiles) {
		Pattern p = Pattern.compile("^(1)\\d{10}$");

		Matcher m = p.matcher(mobiles);

		return m.matches();
	}
	/**
	 * 身份证号
	 * @param str 身份证号
	 * @return
	 */
	public static boolean isIdenNum(String str) {
		Pattern pattern = Pattern.compile("([0-9]{17}([0-9]|[X|x]))|([0-9]{15})");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}

		return true;
	}
}
