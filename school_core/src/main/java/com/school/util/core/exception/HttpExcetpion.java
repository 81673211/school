package com.school.util.core.exception;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HttpExcetpion extends Exception {

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

	public HttpExcetpion(Integer code) {
		init();
		this.code = String.valueOf(code);
		String errMsg = props.getProperty(this.code);
		this.msg = errMsg;
	}

	public HttpExcetpion(String code, String errMsg) {
		this.code = code;
		this.msg = errMsg;
	}

	/**
	 * 获取字符串对应值
	 * 
	 * @throws IOException
	 */
	public static HttpExcetpion createErr(String code) {
		// 取出相关值
		try {
			init();
			String errMsg = props.getProperty(code);
			//errMsg = new String(errMsg.getBytes(ISO_8859_1), UTF_8);
			//errMsg = new String(errMsg.getBytes(ISO_8859_1));
			return new HttpExcetpion(code, errMsg);
		} catch (Exception e) {
			log.error("读取配置文件属性异常", e);
			return new HttpExcetpion("5000", "未知异常");
		}
	}

	@Override
	public String toString() {
		return "[code=" + code + ", msg=" + msg + "]";
	}
}