package com.school.manager.util;

import javax.servlet.http.HttpSession;

public class HttpSessionThreadLocal extends ThreadLocal<HttpSession> {
    private static HttpSessionThreadLocal hstl;

    // private constructor to enforce singleton.
    private HttpSessionThreadLocal() {
    }

    public static HttpSessionThreadLocal getInstance() {
        if (hstl == null) {
            hstl = new HttpSessionThreadLocal();
        }
        return hstl;
    }
}
