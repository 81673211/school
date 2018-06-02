package com.school.common.webUtil;

import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
/**
* <p>Title: ResourcePathExposer</p>
* <p>Description:静态资源访问 </p>
* @author liliang
*/
public class ResourcePathExposer implements ServletContextAware {
	private ServletContext servletContext;
	private String resourceRoot;
	
//	@Autowired
   // private ConfigService configService; 
	/**
	 * 初始化方法
	 */
	public void init() {
		//Config config=configService.findByWebusKey("static_version");
		String version ="1";
		resourceRoot = "/rs_" + version;
		getServletContext().setAttribute("resourceRoot",resourceRoot);
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public String getResourceRoot() {
		return resourceRoot;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}
}
