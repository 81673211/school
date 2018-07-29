package com.school.util.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.relops.snowflake.Snowflake;

/**
 * 随机工具类
 */
public class RandomUtil {
	private static Random random = new Random();
	
	/** 用于随机选的数字 */
	private static final String BASE_NUMBER = "0123456789";
	/** 用于随机选的字符 */
	private static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz";
	/** 用于随机选的字符和数字 */
	private static final String BASE_CHAR_NUMBER = BASE_CHAR + BASE_NUMBER;
	
	/**
	 * 获得指定范围内的随机数
	 * @param min 最小数
	 * @param max 最大数
	 * @return 随机数
	 */
	public static int randomInt(int min, int max) {
		return random.nextInt(max - min) + min;
	}
	
	/**
	 * 获得随机数
	 * @return 随机数
	 */
	public static int randomInt() {
		return random.nextInt();
	}
	
	/**
	 * 获得指定范围内的随机数 [0,limit)
	 * @param limit 限制随机数的范围，不包括这个数
	 * @return 随机数
	 */
	public static int randomInt(int limit) {
		return random.nextInt(limit);
	}
	
	/**
	 * 随机获得列表中的元素
	 * @param <T> 元素类型
	 * @param list 列表
	 * @return 随机元素
	 */
	public static <T> T randomEle(List<T> list) {
		return randomEle(list, list.size());
	}
	
	/**
	 * 随机获得列表中的元素
	 * @param <T> 元素类型
	 * @param list 列表
	 * @param limit 限制列表的前N项
	 * @return  随机元素
	 */
	public static <T> T randomEle(List<T> list, int limit) {
		return list.get(randomInt(limit));
	}
	
	/**
	 * 随机获得列表中的一定量元素
	 * @param <T> 元素类型
	 * @param list 列表
	 * @param count 随机取出的个数
	 * @return 随机元素
	 */
	public static <T> List<T> randomEles(List<T> list, int count) {
		final List<T> result = new ArrayList<T>(count);
		int limit = list.size();
		while(--count > 0) {
			result.add(randomEle(list, limit));
		}
		
		return result;
	}
	
	/**
	 * 获得一个随机的字符串（只包含数字和字符）
	 * 
	 * @param length 字符串的长度
	 * @return 随机字符串
	 */
	public static String randomString(int length) {
		return randomString(BASE_CHAR_NUMBER, length);
	}
	
	/**
	 * 获得一个只包含数字的字符串
	 * 
	 * @param length 字符串的长度
	 * @return 随机字符串
	 */
	public static String randomNumbers(int length) {
		return randomString(BASE_NUMBER, length);
	}
	
	/**
	 * 获得一个随机的字符串
	 * 
	 * @param baseString 随机字符选取的样本
	 * @param length 字符串的长度
	 * @return 随机字符串
	 */
	public static String randomString(String baseString, int length) {
		StringBuffer sb = new StringBuffer();
		
		if (length < 1) {
			length = 1;
		}
		int baseLength = baseString.length();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(baseLength);
			sb.append(baseString.charAt(number));
		}
		return sb.toString();
	}
	
	/**
	 * @return 随机UUID
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * @Description: 生成订单号,采用twitter的Snowflake方法避免id重复
	 * @param
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getOrderNoSnow() {
		int node = 1;
		Snowflake s = new Snowflake(node);
		long id = s.next();
		return String.valueOf(id);
	}
	
	/**
	 * 生成訂單號
	 * @param orderType
	 * @return
	 */
	   private static IdWorker w = new IdWorker(1, 1);
	   
	   
	   public static String GenerateOrderNo(String orderType) {
//	       final IdWorker w = new IdWorker(1, 2);
	       String orderNo = String.valueOf(w.nextId());
	       orderNo = orderType + orderNo;
	       return orderNo;
	    }
	   
	   /**
	    * 
	    *           
	    * 方法描述：创建订单号，支持多节点并发创建不重复的订单号 
	    * 创建人：zhuqing    
	    * 创建时间：2017年8月24日 下午5:04:36    
	    * 最后修改人：zhuqing    
	    * 最后修改时间：2017年8月24日 下午5:04:36    
	    * 修改备注：    
	    * @version 1.0    
	    *
	    */
	   public static String GenerateOrderNo(IdWorker idWorker, String orderType) {
	       String orderNo = String.valueOf(idWorker.nextId());
	       orderNo = orderType + orderNo;
	       return orderNo;
	   }
	   
	   public static String getOrderNo() {
	    long ct = System.currentTimeMillis();
	    String t = String.valueOf(ct);
	    int random = (int) (Math.random() * 1000);
	    String payOrderNo = t + random;
	    if (payOrderNo.length() < 16) {
	        int num = 16 - payOrderNo.length();
	        for (int i = 0; i < num; i++) {
	            payOrderNo += "0";
	        }
	    }
	    
	    return payOrderNo;
	}
	
	/**
	 * 生成16位商户号
	 * @param agentId 商户所属机构ID
	 * @return
	 */
	public static String creatMerchNo(int agentId) {
	    long ct = System.currentTimeMillis();
	    String agentIdPrefix = String.valueOf(agentId);
	    String t = "2";
	    String merchNo = t + agentIdPrefix +String.valueOf(ct);
	    if (merchNo.length() < 16) {
	        int num = 16 - merchNo.length();
	        for (int i = 0; i < num; i++) {
	            merchNo += "0";
	        }
	    }
	    if(merchNo.length() >=16){
	        merchNo = merchNo.substring(0, 16);
	    }
	    
	    return merchNo;
	}
	/**
	 * 生成16位机构号
	 * @return
	 */
	public static String createAgentNo() {
		long ct = System.currentTimeMillis();
		String t = "10001";
		String merchNo = t +String.valueOf(ct);
		if (merchNo.length() < 16) {
			int num = 16 - merchNo.length();
			for (int i = 0; i < num; i++) {
				merchNo += "0";
			}
		}
		if(merchNo.length() >=16){
			merchNo = merchNo.substring(0, 16);
		}
		
		return merchNo;
	}
	
	
	
	/**
	 * 创建指定数量的随机字符串
	 * @param numberFlag 是否是数字
	 * @param length
	 * @return
	 */
	public static String createRandom(boolean numberFlag, int length) {
		
		String retStr = "";
		String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);

		return retStr;
	}
}
