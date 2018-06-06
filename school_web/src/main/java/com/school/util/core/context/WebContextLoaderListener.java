package com.school.util.core.context;

import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/** 
 * Description : WebContextLoaderListener 监听器
 * @author fujie                       
*/ 
public class WebContextLoaderListener implements ServletContextListener{
 	 
    public void contextInitialized(ServletContextEvent sce) {
    	
    	System.out.println("Initialize spring context "+System.currentTimeMillis());    	
    	SpringContextFactory.setSpringWebContext(WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()));
    }

    public void contextDestroyed(ServletContextEvent sce){
    	System.out.println("contextDestroyed spring context "+System.currentTimeMillis()); 
    }
}