package com.school.util.core.utils;

import javax.servlet.http.HttpServletRequest;


public class GetIpAddress {

    /**
     * <p>
     * Title: getIp
     * </p>
     * <p>
     * Description: 获取ip
     * </p>
     * 
     * @param request
     *            HttpServletRequest
     * @return ip地址
     */
    public static String getIp(HttpServletRequest request) {
        
        String unknow="unknown";
        
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || unknow.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknow.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknow.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null) {
            return ip.split(",")[0];
        } else {
            return ip;
        }

    }
}
