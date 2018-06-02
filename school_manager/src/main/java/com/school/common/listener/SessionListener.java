package com.school.common.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.school.constant.Constants;
import com.school.domain.entity.user.AdminUser;
import com.school.util.SessionHandler;
@Component
@Slf4j
public class SessionListener implements HttpSessionListener {
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		log.info("会话["+event.getSession().getId()+"]生效...");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		AdminUser user = (AdminUser) session.getAttribute(Constants.ATTRIBUTE_ADMIN_USER);
		if(null!=user){
			SessionHandler.removeUserFromSessionMap(user.getLoginName());
		}
		log.info("会话["+event.getSession().getId()+"]失效...");
	}
}
