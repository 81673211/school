package com.school.biz.constant;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 07/08/2018 21:17
 */
public final class Constants {

    private Constants() {
    }

    public static final String CHARSET_UTF8 = "UTF-8";

    /**
     * 订单号类型：平台订单号
     */
    public static final String ORDER_NO_TYPE_ORDER = "10";

    /**
     * 订单号类型：支付订单号
     */
    public static final String ORDER_NO_TYPE_PAY_ORDER = "11";

    /**
     * 订单号类型：提现订单
     */
    public static final String ORDER_NO_TYPE_SETT_WITHDRAW = "12";

    /**
     * 订单号类型：委托代付申请
     */
    public static final String ORDER_NO_TYPE_SETT_APPLY = "13";

    /**
     * 订单号类型：直连委托
     */
    public static final String ORDER_NO_TYPE_SETT_DIRECT = "14";

    /**
     * 订单号类型：手工上账订单号
     */
    public static final String ORDER_NO_TYPE_MANUAL_IN = "15";


    public static final String SCOPE_SNSAPI_USERINFO = "snsapi_userinfo";


    /** 用户左侧菜单 */
    public final static String USER_SESSION_MENUS = "myLeftMenus";
    /** 用户拥有资源 */
    public final static String USER_SESSION_RESOURCES = "myResources";
    /** 用户session过期时间配置,单位：分钟 */
    public final static int USER_SESSION_TIMEOUT = Integer.parseInt("30");

    /** 登录会话相关 */
    public static final String ATTRIBUTE_ADMIN_USER = "adminUser";

    public static final String ATTRIBUTE_ADMIN_USER_MENU = "userMenu";

    public static final String ATTRIBUTE_ADMIN_BTN_MENU = "btnMenu";

    public static final String ATTRIBUTE_ADMIN_ALL_MENU = "allMenu";

    public static final String ADMIN_LOGIN_NAME = "admin";

    /**
     * 员工状态：有效
     */
    public static final int EMPLOYEE_ON_JOB = 0;
    /**
     * 员工状态：无效
     */
    public static final int EMPLOYEE_OUT_JOB = 1;

    public static final String EASY_PAGE = "page";

}
