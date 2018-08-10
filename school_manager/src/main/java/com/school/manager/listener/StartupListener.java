package com.school.manager.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupListener implements ServletContextListener{

	  //private UserService userservice;
	
	  private final Log log = LogFactory.getLog(getClass());
	  

	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {

		// 加载用户缓存
		try {
//			log.info("begin to initial user cache.");
//			userservice = SpringContextFactory.getBean("userService");
//			userservice.initialUserMenuMap();
//			log.info("cache size:" + UserServiceImpl.UserMenuMap.size());
			
			
		} catch (Exception e) {
			log.error("initial user cache error.", e);
		}

	}

}
