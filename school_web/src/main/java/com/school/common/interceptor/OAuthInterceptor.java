package com.school.common.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.school.domain.entity.customer.Customer;
import com.school.service.customer.CustomerService;
import com.school.service.wechat.OauthService;
import com.school.util.wechat.OAuthToken;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: OAuthInterceptor
 * @Description:  接口拦截器 
 * @author linqingsong
 * @date Jan 18, 2016 9:57:39 AM
 */
@Slf4j
public class OAuthInterceptor implements HandlerInterceptor{

	@Autowired
	private OauthService oauthService;
	@Autowired
	private CustomerService customerService;

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3) {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws IOException {

		String requestURI = request.getRequestURI();
		log.info("requestUri:{}", requestURI);
		if (requestURI.startsWith("/wx/proxy")) {
			return true;
		}
		String openId = request.getParameter("openId");
		if (StringUtils.isBlank(openId)) {
		    log.info("openId not found");
		    return false;
        }
		oauthService.check(openId);
		Customer customer = customerService.getByOpenId(openId);
		log.info("customer:{}, openId:{}", customer, openId);
		if (customer == null || !customer.isSubscribed()) {
			response.sendRedirect("/index.html");
		} else if (StringUtils.isBlank(customer.getPhone())) {
			response.sendRedirect("/customer/profile");
		} else {
			return true;
		}
		return false;
	}

}
