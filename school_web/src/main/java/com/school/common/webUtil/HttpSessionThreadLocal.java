package com.school.common.webUtil;

import javax.servlet.http.HttpSession;

public class HttpSessionThreadLocal extends ThreadLocal<HttpSession> {
    private static HttpSessionThreadLocal hstl;
    
    // private constructor to enforce singleton.
    private HttpSessionThreadLocal() {
    }

    public static HttpSessionThreadLocal getInstance() {
        if (hstl == null) {
            synchronized (HttpSessionThreadLocal.class) {
                if (hstl == null) {
                    hstl = new HttpSessionThreadLocal();
                }
            }
        }
        return hstl;
    }
}
