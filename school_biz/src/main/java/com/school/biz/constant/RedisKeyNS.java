package com.school.biz.constant;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 26/07/2018 15:05
 */
public final class RedisKeyNS {

    private RedisKeyNS() {}

    public static final String CACHE_CUSTOMER_PROFILE_VERIFY_CODE = "redis:customer:profile:verify:code:";

    public static final String CACHE_CUSTOMER_PROFILE_VERIFY_CODE_REPEAT = "redis:customer:profile:verify:code:repeat:";

    public static final String CACHE_BASE_ACCESS_TOKEN = "redis:base:access_token";

    public static final String CACHE_NAMESPACE_AUTH_TOKEN = "redis:auth:access_token:";

    public static final String CACHE_NAMESPACE_SUGGESTION = "redis:suggestion:";

    public static final String CACHE_SEND_EXPRESS_FEE = "redis:send_express_fee";

    public static final String CACHE_REGION_CHILDREN = "redis:region:children:";
}
