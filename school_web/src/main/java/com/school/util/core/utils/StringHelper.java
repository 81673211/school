package com.school.util.core.utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class StringHelper{
	
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0);
	}

	/**
	 * 为空或null时返回缺省值
	 * 
	 * @see #isEmpty(String)
	 */
	public static String avoidEmpty(String str, String defaultStr) {
		return isEmpty(str) ? defaultStr : str;
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
	
	  /**
     * 校验银行卡卡号
     * @param cardId
     * @return
     */
    public static boolean isBankCard(String cardId) {
    	
    	try {
    		char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
   		 if(bit == 'N'){
   			 return false;
   		 }
   		 return cardId.charAt(cardId.length() - 1) == bit;
		} catch (Exception e) {
			return false;
		}
    		 
    }
   
    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeCardId
     * @return
     */
    private static char getBankCardCheckCode(String nonCheckCodeCardId){
        if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
        	//如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;           
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }
    final static Map<Integer, String> zoneNum = new HashMap<Integer, String>();
    static {
        zoneNum.put(11, "北京");
        zoneNum.put(12, "天津");
        zoneNum.put(13, "河北");
        zoneNum.put(14, "山西");
        zoneNum.put(15, "内蒙古");
        zoneNum.put(21, "辽宁");
        zoneNum.put(22, "吉林");
        zoneNum.put(23, "黑龙江");
        zoneNum.put(31, "上海");
        zoneNum.put(32, "江苏");
        zoneNum.put(33, "浙江");
        zoneNum.put(34, "安徽");
        zoneNum.put(35, "福建");
        zoneNum.put(36, "江西");
        zoneNum.put(37, "山东");
        zoneNum.put(41, "河南");
        zoneNum.put(42, "湖北");
        zoneNum.put(43, "湖南");
        zoneNum.put(44, "广东");
        zoneNum.put(45, "广西");
        zoneNum.put(46, "海南");
        zoneNum.put(50, "重庆");
        zoneNum.put(51, "四川");
        zoneNum.put(52, "贵州");
        zoneNum.put(53, "云南");
        zoneNum.put(54, "西藏");
        zoneNum.put(61, "陕西");
        zoneNum.put(62, "甘肃");
        zoneNum.put(63, "青海");
        zoneNum.put(64, "宁夏");
        zoneNum.put(65, "新疆");
        zoneNum.put(71, "台湾");
        zoneNum.put(81, "香港");
        zoneNum.put(82, "澳门");
        zoneNum.put(91, "国外");
    }

    final static int[] PARITYBIT = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
    final static int[] POWER_LIST = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,
            5, 8, 4, 2 };

    /**
     * 身份证号是否基本有效
     * 
     * @param s
     *            号码内容
     * @return 是否有效，null和""都是false
     */
    public static boolean isIdcard(String s) {
        if (s == null || (s.length() != 15 && s.length() != 18))
            return false;
        final char[] cs = s.toUpperCase().toCharArray();
        // （1）校验位数
        int power = 0;
        for (int i = 0; i < cs.length; i++) {// 循环比正则表达式更快
            if (i == cs.length - 1 && cs[i] == 'X')
                break;// 最后一位可以是X或者x
            if (cs[i] < '0' || cs[i] > '9')
                return false;
            if (i < cs.length - 1)
                power += (cs[i] - '0') * POWER_LIST[i];
        }
        // （2）校验区位码
        if (!zoneNum.containsKey(Integer.valueOf(s.substring(0, 2)))) {
            return false;
        }
        // （3）校验年份
        String year = s.length() == 15 ? "19" + s.substring(6, 8) : s
                .substring(6, 10);
        final int iyear = Integer.parseInt(year);
        if (iyear < 1900 || iyear > Calendar.getInstance().get(Calendar.YEAR)) {
            return false;// 1900年的PASS，超过今年的PASS
        }
        // （4）校验月份
        String month = s.length() == 15 ? s.substring(8, 10) : s.substring(10,
                12);
        final int imonth = Integer.parseInt(month);
        if (imonth < 1 || imonth > 12)
            return false;
        // （5）校验天数
        String day = s.length() == 15 ? s.substring(10, 12) : s.substring(12,
                14);
        final int iday = Integer.parseInt(day);
        if (iday < 1 || iday > 31)
            return false;
        // （6）校验一个合法的年月日
        if (!validate(iyear, imonth, iday))
            return false;
        // （7）校验“校验码”
        if (s.length() == 15)
            return true;
        return cs[cs.length - 1] == PARITYBIT[power % 11];
    }

    static boolean validate(int year, int month, int day) {
        //比如考虑闰月，大小月等
        return true;
    }
	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6',
		'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

}
