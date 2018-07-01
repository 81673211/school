package com.school.constant;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.props.Props;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 类名称：Constants    
 * 类描述：配置参数常量类
 * 创建人：xiexu    
 * 创建时间：2016年7月28日 下午4:40:25    
 * 最后修改人：xiexu    
 * 最后修改时间：2016年7月28日 下午4:40:25    
 * 修改备注：    
 * @version 1.0    
 *
 */
public class Constants {
	/**
	 * 系统配置
	 * 
	 * @author yao
	 * @since 2016-01-13 11:11
	 */
	public static final Map<String, String> SYSTEM_CONFIG = new HashMap<String, String>();
	/**
	 * 执行环境
	 */
	public static String env = "";
	
	public static Props p = new Props();
	
	static {
		
		getEnv();
		
		loadConfigProp();
		
		
	}

	@SuppressWarnings("unchecked")
	public static void getEnv() {
		
		SAXReader saxReader = new SAXReader();

        Document document = null;
        
		try {
			Resource resource = new ClassPathResource("../web.xml");
		
			document = saxReader.read(resource.getInputStream());
		 
			Element root = document.getRootElement();
			 	
		    List<Element> contextParamList = root.elements("context-param");
		    for(Element element : contextParamList){
		        		
	        	if(element.element("param-name").getText().equals("spring.profiles.active")){
	        		env = element.element("param-value").getText();
	        	}
		     }
	    
		}catch (Exception e) {
			
//			e.printStackTrace();
		}
	    
	}
	

	/**
	 * 支持上传文件类型
	 */
	public static final Map<String, String> UPLOAD_FILE_TYPE = new HashMap<String, String>();

	public static void loadConfigProp(){
		
		 Resource resource = new ClassPathResource("config/config.all.properties");
		
		 try {
			p.load(resource.getInputStream());
		 }catch (IOException e) {
			e.printStackTrace();
		 }
	}
	/** 用户密码加密盐值 */
	public static final String PASSWD_SALT = p.getValue("passwd_salt");
	/** 登陆系统url */
	public static final String LOGIN_SYS_URL = p.getValue("login_sys_url");
	/** 文件保存路径(外部路径) */
	public static final String UPLOAD_FILE_PATH = p.getValue("upload_file_path");
	/** 文件保存路径(项目内部路径) */
	public static final String UPLOAD_FILE_PROJECT_PATH = "/upload/cm";
	/** 模板保存路径 */
	public static final String TEMPLATE_LETTER_PATH = p.getValue("template_letter_path");
	/** 临时文件路径 */
	public static final String TEMP_PATH = p.getValue("temp_path");
	/**
	 * 微信支付证书保存路径
	 */
	public static final String WXPAY_CERT_PATH = p.getValue("wxpay_cert_path");
	/**
	 * 微信支付appid
	 */
	public static final String WXPAY_APP_ID = p.getValue("wxpay_app_id");
	/**
	 * 微信支付商户id
	 */
	public static final String WXPAY_MCH_ID = p.getValue("wxpay_mch_id");
	/**
	 * 微信支付密钥
	 */
	public static final String WXPAY_KEY = p.getValue("wxpay_key");
	
	/**
	 * 默认分页显示数
	 */
	public static final int DEFAULT_PAGESIZE = 10;

	public static final String USER_TOKEN_PRIVATEKEY = "";
	/** 用户session名称 */
    public final static String USER_SESSION_NAME = "case_loginUser";
    /** 用户左侧菜单 */
    public final static String USER_SESSION_MENUS = "myLeftMenus";
    /** 用户拥有资源 */
    public final static String USER_SESSION_RESOURCES = "myResources";
    /** 用户所属角色 */
    public final static String USER_SESSION_ROLES = "myRoles";
    
    /** 登录会话相关 */
	public static final String ATTRIBUTE_ADMIN_USER = "adminUser";
	
//	public static final String ATTRIBUTE_ADMIN_MERCHANT = "adminMerchant";

	public static final String ATTRIBUTE_ADMIN_USER_MENU = "userMenu";
	
	public static final String ATTRIBUTE_ADMIN_BTN_MENU = "btnMenu";
	
	public static final String ATTRIBUTE_ADMIN_ALL_MENU = "allMenu";
	
	public static final String ADMIN_LOGIN_NAME = "admin";
	
	public static final String ADMIN_USER_DEFAULT_PASSWORD = "123456";
	
	/** 用户session过期时间配置,单位：分钟 */
	public final static int USER_SESSION_TIMEOUT = Integer.parseInt("30");
	
	/**
	 * 员工状态：有效
	 */
	public static final Integer EMPLOYEE_ON_JOB = 0;
	/**
	 * 员工状态：无效
	 */
	public static final Integer EMPLOYEE_OUT_JOB = 1;
    
	/**
	 * 文件服务器访问地址
	 */
	public static final String HostUrl = p.getValue("host_url");

	/**
	 * @Title: suspendThreadSleep @Description: 暂停访问 @param @return void @throws
	 */
	public static void suspendThreadSleep() {
		String suspend = SYSTEM_CONFIG.get("suspend");
		// int i=0;
		if (suspend.equals("true")) {
			while (true) {
				try {
					// 线程休眠1秒
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String sd = SYSTEM_CONFIG.get("suspend");

				if (sd.equals("false")) {
					break;
				}
			}
		}
	}
	
	/**
	 * 
	 * 方法描述：获取当前年月日作为文件存储路径
	 * 创建人：方梁
	 * 创建时间：2016年9月26日 下午3:27:49
	 * 最后修改人：方梁
	 * 最后修改时间：2016年9月26日 下午3:27:49
	 * 修改备注：
	 * @version 1.0
	 */
	public static String datePath(){
		String path = "";
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = (now.get(Calendar.MONTH) + 1);
		int day = now.get(Calendar.DAY_OF_MONTH);
		String m = month + "", d = day + "";
		if(month < 10)
			m = "0" + m;
		if(day < 10)
			d = "0" + d;
		path = "/" + year + "-" + m + "-" + d;
		return path;
	}
}
