package com.school.biz.constant;

/**
 * 页面Url常量类
 */
public class ConstantUrl {
    // 公用常量
    public static final String DETAIL_URL = "detailUrl";
    public static final String EDIT_URL = "editUrl";
    public static final String DEL_URL = "delUrl";
    public static final String RE_ORDER_URL = "reOrderUrl";
    public static final String REFUND_URL = "refundUrl";

    /** 订单管理 */
    /**
     * 支付订单列表
     */
    // 订单详情
    public static final String ORDER_DETAIL_URL = "/order/detail.do";
    /**
     * 退款订单列表
     */
    // 退款订单详情
    public static final String REFUND_ORDER_DETAIL_URL = "/refundOrder/detail.do";

    /** 快递管理 */
    /**
     * 收件
     */
    // 收件详情
    public static final String EXPRESS_RECEIVE_DETAIL_URL = "/express/expressReceive/detail.do";
    // 收件编辑
    public static final String EXPRESS_RECEIVE_EDIT_URL = "/express/expressReceive/edit.do";
    // 收件删除
    public static final String EXPRESS_RECEIVE_DEL_URL = "/express/expressReceive/del.do";
    //收件补单
    public static final String EXPRESS_RECEIVE_REORDER_URL = "/express/expressReceive/reOrder.do";
    // 退款
    public static final String EXPRESS_RECEIVE_REFUND_URL = "/express/expressReceive/refund.do";

    /**
     * 寄件
     */
    // 寄件详情
    public static final String EXPRESS_SEND_DETAIL_URL = "/express/expressSend/detail.do";
    // 寄件编辑
    public static final String EXPRESS_SEND_EDIT_URL = "/express/expressSend/edit.do";
    // 寄件删除
    public static final String EXPRESS_SEND_DEL_URL = "/express/expressSend/del.do";
    // 补单
    public static final String EXPRESS_SEND_REORDER_URL = "/express/expressSend/reOrder.do";
    // 退款
    public static final String EXPRESS_SEND_REFUND_URL = "/express/expressSend/refund.do";

    /**
     * 柜子取件
     */
    // 柜子取件详情
    public static final String EXPRESS_BOX_INFO_DETAIL_URL = "/expressBoxInfo/detail.do";
    // 柜子取件编辑
    public static final String EXPRESS_BOX_INFO_EDIT_URL = "/expressBoxInfo/edit.do";
    // 柜子取件删除
    public static final String EXPRESS_BOX_INFO_DEL_URL = "/expressBoxInfo/del.do";

    /** 快递公司管理 */
    /**
     * 快递公司
     */
    // 快递公司详情
    public static final String EXPRESS_COMPANY_DETAIL_URL = "/expressCompany/detail.do";
    // 快递公司编辑
    public static final String EXPRESS_COMPANY_EDIT_URL = "/expressCompany/edit.do";
    // 快递公司删除
    public static final String EXPRESS_COMPANY_DEL_URL = "/expressCompany/del.do";

    /** 客户管理 */
    /**
     * 客户
     */
    // 客户详情
    public static final String CUSTOMER_DETAIL_URL = "/customer/detail.do";
    // 客户编辑
    public static final String CUSTOMER_EDIT_URL = "/customer/edit.do";
    // 客户删除
    public static final String CUSTOMER_DEL_URL = "/customer/del.do";

    /** 权限管理 */
    /**
     * 用户管理
     */
    // 用户详情
    public static final String ADMIN_USER_DETAIL_URL = "/permission/adminUser/detail.do";
    // 用户编辑
    public static final String ADMIN_USER_EDIT_URL = "/permission/adminUser/edit.do";
    // 用户删除
    public static final String ADMIN_USER_DEL_URL = "/permission/adminUser/del.do";

    /**
     * 角色管理
     */
    // 角色详情
    public static final String ROLE_DETAIL_URL = "/permission/role/detail.do";
    // 角色编辑
    public static final String ROLE_EDIT_URL = "/permission/role/edit.do";
    // 角色删除
    public static final String ROLE_DEL_URL = "/permission/role/del.do";

    /**
     * 资源管理
     */
    // 资源详情
    public static final String RESOURCE_INFO_DETAIL_URL = "/permission/resourceInfo/detail.do";
    // 资源编辑
    public static final String RESOURCE_INFO_EDIT_URL = "/permission/resourceInfo/edit.do";
    // 资源删除
    public static final String RESOURCE_INFO_DEL_URL = "/permission/resourceInfo/del.do";

    /** 系统管理 */
    /**
     * 日志列表
     */
    // 日志详情
    public static final String EXPRESS_LOG_DETAIL_URL = "/expressLog/detail.do";
    /**
     * 意见列表
     */
    // 意见详情
    public static final String SUGGESTION_DETAIL_URL = "/suggestion/detail.do";
}
