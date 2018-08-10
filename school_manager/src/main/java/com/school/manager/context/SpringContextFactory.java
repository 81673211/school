package com.school.manager.context;

import org.springframework.web.context.WebApplicationContext;

/** 
 * Description : Spring上下文工厂
 * @author fujie                       
*/
public class SpringContextFactory {
	
	private static WebApplicationContext springWebContext;

 
	public static void setSpringWebContext(WebApplicationContext springWebContext) {
		
		System.out.println("setSpringWebContext "+System.currentTimeMillis());
		SpringContextFactory.springWebContext = springWebContext;		
	}
	
	public static WebApplicationContext getSpringWebContext(){
		return springWebContext;
	}
	
	public static <T> T getBean(String name)
	{
		if(springWebContext == null){
			throw new RuntimeException("springWebContext is null!!!!!");
		}
		return (T)springWebContext.getBean(name);
	}

}
