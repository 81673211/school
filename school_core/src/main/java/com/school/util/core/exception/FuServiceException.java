package com.school.util.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Properties;

/**
 * @Description: Service 层统一异常 
 * 
 * 异常处理由Spring-mvc.mxl统一配置处理
 * 
 * @author fujie
 */

@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
public class FuServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String msg;
	
	public FuServiceException(String code) {
		this.code = code;
	}

	public FuServiceException(String code, String errMsg) {
		this.code = code;
		this.msg = errMsg;
	}

	/** 属性集 */
	private static Properties props = null;

	/** 构造私有 "err.properties" */
	private static void init() {
		
		if (props == null) {
			
			try {
				props = new Properties();
				InputStream in = FuServiceException.class.getClassLoader()
						.getResourceAsStream("config/err.properties");
				props.load(in);
				
			} catch (Exception e) {
				
				log.error("系统级别错误:读取错误信息描述配置文件属性异常!", e);
			}
		}
	}

	/**
	 * 获取字符串对应值
	 * 
	 */
	public static FuServiceException createErr(int code) {
		
		// 取出相关值
		try {
			
			init();
			String strErrCode = String.valueOf(code);
			
			return new FuServiceException(strErrCode, props.getProperty(strErrCode));
			
		} catch (Exception e) {
			
			log.error("读取错误信息描述配置文件属性异常!", e);
			return new FuServiceException("5000", props.getProperty("5000"));
		}
	}

}
