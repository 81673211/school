/**
 * Title: MOBAO  <BR>
 * Description: todo 
 * Copyright: Copyright (c) 2014-2015<BR>
 * Company: 成都摩宝网络科技有限公司(www.mobo360.com/)<BR>
 * 
 * @author linqingsong
 * @version 1.0
 */
package com.school.common.interceptor;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: InterfaceInterceptor
 * @Description:  接口拦截器 
 * @author linqingsong
 * @date Jan 18, 2016 9:57:39 AM
 */
public class InterfaceInterceptor implements HandlerInterceptor{

	private final Log log = LogFactory.getLog(getClass());
	private static String[] ips;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	/**
	 * 控制接口访问ip
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {/*
		//1 获取请求接口的IP
		String requestIp=InterfaceInterceptor.getIpAddr(request);
		log.info("---------请求接口ip:"+requestIp);
		//2 判断当前IP是否允许访问
		for(int i=0;i<ips.length;i++){
			String ip=ips[i];
			if(requestIp.equals(ip)){
				return true;
			}
		}
		return false;
	*/
		return true;
	}

	/**
	   * 获取访问者IP
	   * 
	   * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
	   * 
	   * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
	   * 如果还不存在则调用Request .getRemoteAddr()。
	   * 
	   * @param request
	  * @return
	  */
	 public static String getIpAddr(HttpServletRequest request) {
	     String ip = request.getHeader("X-Real-IP");
	     if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
	         return ip;
	     }
	     ip = request.getHeader("X-Forwarded-For");
	     if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
	         // 多次反向代理后会有多个IP值，第一个为真实IP。
	         int index = ip.indexOf(',');
	         if (index != -1) {
	             return ip.substring(0, index);
	         } else {
	             return ip;
	         }
	     } else {
	         return request.getRemoteAddr();
	     }
	 }
}
