package com.school.manager.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.school.biz.constant.Constants;
import com.school.biz.domain.entity.user.AdminUser;


public class UserSessionManager {

    /** 日志操作对象 */
    protected static Log logger = LogFactory.getLog(UserSessionManager.class);

    /**
     * 存放Session
     */
    private static final Map<String, AdminUser> SESSION_MAP = new HashMap<String, AdminUser>();

    /**
     * 添加Session
     */
    public static void addSession(String sessionId, AdminUser adminUser) {
        SESSION_MAP.put(sessionId, adminUser);
    }

    /**
     * 更新Session
     */
    public static void updateSession(String sessionId, AdminUser adminUser) {
        SESSION_MAP.put(sessionId, adminUser);
    }

    /**
     * 删除
     * 
     * @param sessionId
     */
    public static void removeSession(String sessionId) {
        SESSION_MAP.remove(sessionId);
    }

    /**
     * 获取
     * 
     * @param sessionId
     * @return
     */
    public static AdminUser getSession(String sessionId) {
        return SESSION_MAP.get(sessionId);
    }

    /**
     * 清除已过期的session
     */
    public static void clearTimeoutSession() {
    	AdminUser adminUser = null;
        List<String> keyArray = new ArrayList<String>();
        for (String key : SESSION_MAP.keySet()) {
            adminUser = SESSION_MAP.get(key);
            if (isInvalid(adminUser)) {
                keyArray.add(key);
            }
        }
        for (int i = 0; i < keyArray.size(); i++) {
            removeSession(keyArray.get(i));
        }
    }

    /**
     * 判断登录用户session是否过期 返回值：true 已过期 false ：没有过期
     * 
     * @param adminUser
     */
    public static boolean isInvalid(AdminUser adminUser) {
        long systime = System.currentTimeMillis();
        if ((systime - adminUser.getLastRequestTime()) > Constants.USER_SESSION_TIMEOUT * 60 * 1000) {
            return true;
        } else {
            return false;
        }
    }
}
