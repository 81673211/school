package com.school.common.interceptor;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.school.service.wechat.OauthService;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: OAuthInterceptor
 * @Description: 接口拦截器
 * @author linqingsong
 * @date Jan 18, 2016 9:57:39 AM
 */
@Slf4j
public class OAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private OauthService oauthService;

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
                             Object arg2) {
        String openId = request.getParameter("openId");
        String requestURI = request.getRequestURI();
        log.info("requestURI:{}", requestURI);
        if (StringUtils.isBlank(openId)) {
            log.info("openId not found");
            return false;
        }
        handleAuthResult(oauthService.check(openId), request, response);
        return true;
    }

    private void handleAuthResult(int authResult, HttpServletRequest request, HttpServletResponse response) {
        if (authResult == 1) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            String requestURI = request.getRequestURI();
            StringBuilder state = new StringBuilder();
            state.append(requestURI);
            if (!CollectionUtils.isEmpty(parameterMap)) {
                state.append("?");
                parameterMap.forEach((key, value) -> {
                    if (!"openId".equals(key)) {
                        state.append(key).append("=").append(value[0]);
                    }
                });
            }
            log.info("re-auth state:{}", state);
            try {
                response.sendRedirect(oauthService.getOAuthUrl(state.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (authResult == 2) {
            throw new RuntimeException("认证失败");
        }
    }
}
