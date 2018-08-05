package com.school.common.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.constructs.web.filter.Filter;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.school.constant.Constants;
import com.school.domain.entity.user.AdminUser;
import com.school.util.SessionUtils;

@Slf4j
public class LoginUserFilter extends Filter {

	/**
	 * 公共资源，免鉴权
	 */
	private static String commonResourceFile = "/config/commonreource.properties";

	private static Properties commonResourceProperties = null;

	private static Set<Object> commonResourceSet = null;

	private static String login_view = "/managers/user/toLogin";
	
	public enum LoginError {
		TIMEOUT , REPEAT , DISABLE ;
	}
	
	/** 类加载的时候初始化不要权限验证的请求 */
	static {
		Resource resource = new ClassPathResource(commonResourceFile);
		commonResourceProperties = new Properties();
		InputStream in;
		try {
			in = resource.getInputStream();
			if (null == in) {
				log.debug("获取通用资源文件[" + commonResourceFile + "]出错未找到指定文件..");
			} else {
				try {
					commonResourceProperties.load(in);
					commonResourceSet = commonResourceProperties.keySet();
					if (null != commonResourceSet) {
						for (Object key : commonResourceSet) {
							log.debug(commonResourceProperties.get(key) + "");
						}
					}
				} catch (IOException e) {
					log.debug("获取通用资源文件[" + commonResourceFile + "]出错 " + e.getMessage());
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	protected void doDestroy() {
	}

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws Throwable {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpSession session = request.getSession(true);
		log.debug("请求地址：" + request.getRemoteHost() + ",请求目标：" + request.getRequestURL().toString() + ",sessionId:"
				+ request.getRequestedSessionId());
		request.getSession().removeAttribute("message");
		AdminUser adminUser = SessionUtils.getSessionUser(httpRequest);
		String target = request.getRequestURI();
		try {
			// 访问resources资源文件，免鉴权
			if (target.startsWith(request.getContextPath() + "/static")) {
				chain.doFilter(request, response);
				return;
			} else if (target.startsWith(request.getContextPath() + "/upload")) {
				chain.doFilter(request, response);
				return;
			} else {
				// 公共免鉴权资源
				target = target.substring(request.getContextPath().length());
				boolean needPermisionCheck = true;
				if (null != commonResourceSet) {
					for (Object key : commonResourceSet) {
						if (target.contains(commonResourceProperties.get(key) + "")) {
							needPermisionCheck = false;
							break;
						}
					}
				}
				if (!needPermisionCheck) {
					log.debug("检测到通用资源-->" + target + " ,不需要权限验证直接跳转");
					chain.doFilter(request, response);
					return;
				}
			}
			if (adminUser != null) {
				if (adminUser.getStatus()!=Constants.EMPLOYEE_ON_JOB) {
					log.info( "您的账号已被设置为无效或者注销！");
					session.invalidate();
					UserSessionManager.removeSession(adminUser.getId().toString());
					doRedirect(request,response,LoginError.DISABLE);
				} else if (UserSessionManager.getSession(adminUser.getId().toString()).getSessionId()
						.equals(session.getId())) {
					adminUser.setLastRequestTime(System.currentTimeMillis());
					chain.doFilter(request, response);
					return;
				} else { // 用户重复登录
					log.info( "您的账户已在别处登录！");
					session.removeAttribute(Constants.ATTRIBUTE_ADMIN_USER);
					session.setAttribute("message", "您的账户已在别处登录！");
					doRedirect(request,response,LoginError.REPEAT);
				}
			} else {
				log.info( "未登陆或者系统session超时！");
				doRedirect(request,response,LoginError.TIMEOUT);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

    /**
     * @Title: doRedirect   
     * @Description: 执行重定向操作
     * @param: request
     * @param: response
     * @param: isRepeatLogin
     * @return: void      
     * @throws
     */
    private void doRedirect(HttpServletRequest request,HttpServletResponse response,LoginError loginErr) {
    	if(request.getHeader("x-requested-with")!=null
    			&&request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){ //区别ajax请求
    		switch (loginErr) { 
	   		 	case TIMEOUT: 
	   		 		response.setHeader("timeout", "timeout"); 
	                break; 
	            case REPEAT: 
	            	response.setHeader("timeout", "repeat");
	                break; 
	            case DISABLE: 
	            	response.setHeader("timeout", "disable");
	                break;
	   		}
    	}else{
    		try {
				response.sendRedirect(request.getContextPath() + login_view);
			} catch (IOException e) {
				 log.error(e.getMessage());
			}
    	}
	}
	
	@Override
	protected void doInit() throws Exception {
		
	}
}
