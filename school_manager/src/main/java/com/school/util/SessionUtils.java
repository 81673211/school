package com.school.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.school.constant.Constants;
import com.school.domain.entity.user.AdminUser;


public class SessionUtils {
	
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
