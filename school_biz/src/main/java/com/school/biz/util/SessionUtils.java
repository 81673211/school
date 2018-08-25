package com.school.biz.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.school.biz.constant.Constants;
import com.school.biz.domain.entity.user.AdminUser;


public class SessionUtils {

	private SessionUtils() {
	}

	/**
	 * @throws Exception
	 * 获取session中的用户信息
	 * @return PlatformUser
	 * @throws  
	 */
	 public static AdminUser getSessionUser(HttpServletRequest request){
		 HttpSession session = request.getSession();
		 return (AdminUser) session.getAttribute(Constants.ATTRIBUTE_ADMIN_USER);
	 } 
}
