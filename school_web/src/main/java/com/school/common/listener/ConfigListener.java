package com.school.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.school.constant.Constants.SYSTEM_CONFIG;


public class ConfigListener implements ServletContextListener {
	private final static Logger LOG = LoggerFactory.getLogger(ConfigListener.class);
	
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	public void contextInitialized(ServletContextEvent event) {
		
		ServletContext servletContext = event.getServletContext();
		
		String profile = servletContext.getInitParameter("spring.profiles.active");
		
		LOG.info("#################@@@@@@@@@@"+profile+"@@@@@@@@@###################################");
		
		this.workByEntry(SYSTEM_CONFIG);
	}
	
	
	 private void workByEntry(Map<String, String> map) {
        Set<Map.Entry<String, String>> set = map.entrySet();
        for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            LOG.info(entry.getKey() + "--->" + entry.getValue());
        }
    }
}
