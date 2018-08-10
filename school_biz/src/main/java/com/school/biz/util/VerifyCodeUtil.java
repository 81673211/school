package com.school.biz.util;

import java.util.Random;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 26/07/2018 15:11
 */
public final class VerifyCodeUtil {

    private static final String COLL = "0123456789";

    private VerifyCodeUtil() {
    }

    public static String obtain() {
        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            char ch = COLL.charAt(new Random().nextInt(COLL.length()));
            sb.append(ch);
        }
        return sb.toString();
    }
}
