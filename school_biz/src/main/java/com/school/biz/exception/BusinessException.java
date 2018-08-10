/**
 * Title: MOBAO <BR>
 * Description: Copyright: Copyright (c) 2014-2015<BR>
 * Company: 成都摩宝网络科技有限公司(www.mobo360.com/)<BR>
 * 
 * @author linqingsong
 * @version 1.0
 */
package com.school.biz.exception;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * ClassName: BusinessException 
 * @Description: 自定义异常
 * @author linqingsong
 * @date Feb 12, 2015
 */
public class BusinessException extends Exception {
		private static Logger log = Logger.getLogger(BusinessException.class);
		private static final String ISO_8859_1 = "ISO-8859-1";
		private static final String UTF_8 = "UTF-8";
		private static final long serialVersionUID = 8149279196909104645L;
		private String code;
		private String msg;

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		/** 属性集 */
		private static Properties props = null;

		/** 构造私有 "property.properties" */
		private static void init() {
			if (props == null) {
				try {
					props = new Properties();
					InputStream in = BusinessException.class.getClassLoader()
							.getResourceAsStream("config/err.properties");
					props.load(in);
				} catch (Exception e) {
					log.error("读取配置文件异常", e);
				}
			}
		}
		
		public BusinessException(String code) {
			this.code = code;
		}

		public BusinessException(String code, String errMsg) {
			this.code = code;
			this.msg = errMsg;
		}

		/**
		 * 获取字符串对应值
		 * 
		 * @throws IOException
		 */
		public static BusinessException createErr(String code) {
			// 取出相关值
			try {
				init();
				String errMsg = props.getProperty(code);
//				errMsg = new String(errMsg.getBytes(ISO_8859_1), UTF_8);
				return new BusinessException(code, errMsg);
			} catch (Exception e) {
				log.error("读取配置文件属性异常", e);
				return new BusinessException("5000", "未知异常");
			}
		}

		@Override
		public String toString() {
			return "[code=" + code + ", msg=" + msg + "]";
		}
}
 