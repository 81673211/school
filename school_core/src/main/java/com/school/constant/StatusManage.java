package com.school.constant;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 *        
 * 类名称：StatusManage    
 * 类描述：状态常量类
 * 创建人：xiexu    
 * 创建时间：2016年7月28日 下午4:40:00    
 * 最后修改人：xiexu    
 * 最后修改时间：2016年7月28日 下午4:40:00    
 * 修改备注：    
 * @version 1.0    
 *
 */
public class StatusManage {
	
	/**
	 * 商品类型状态：0-不启用
	 */
	public static final Integer GOOD_TYPE_STATUS_OFF = 0;
	
	/**
	 * 商品类型状态：1-启用
	 */
	public static final Integer GOOD_TYPE_STATUS_ON = 1;
	
	public static final Map<Integer, String> goodTypeStatusMap = new HashMap<Integer, String>();
	
	/**
	 * 是否邮寄：0-否
	 */
	public static final Integer IS_POST_NO = 0; 
	
	/**
	 * 是否邮寄：1-是
	 */
	public static final Integer IS_POST_YES = 1;
	
	public static final Map<Integer, String> isPostMap = new HashMap<Integer,String>();
	
	/**
	 * 是否店铺商品：0-直销商品
	 */
	public static final Integer IS_SHOP_GOOD_DIRECT = 0; 
	
	/**
	 * 是否店铺商品：1-店铺商品
	 */
	public static final Integer IS_SHOP_GOOD_SHOP = 1;
	
	public static final Map<Integer, String> isShopGoodMap = new HashMap<Integer,String>();
	
	/**
	 * 商品是否推荐至首页：0-否
	 */
	public static final Integer IS_RECOMMEND_NO = 0; 
	
	/**
	 * 商品是否推荐至首页：1-是
	 */
	public static final Integer IS_RECOMMEND_YES = 1;
	
	public static final Map<Integer, String> isRecommendMap = new HashMap<Integer,String>();
	
	/**
	 * 商品状态：1-上架
	 */
	public static final Integer GOOD_INFO_STATUS_UP = 1;
	
	/**
	 * 商品状态：2-下架
	 */
	public static final Integer GOOD_INFO_STATUS_DOWN = 2;
	
	/**
	 * 商品审核状态：3-待审核
	 */
	public static final Integer GOOD_INFO_STATUS_AUTH_WAIT = 3; 
	
	/**
	 * 商品审核状态：4-审核通过
	 */
	public static final Integer GOOD_INFO_STATUS_AUTH_SUCCESS = 4;
	/**
	 * 商品审核状态：5-审核失败
	 */
	public static final Integer GOOD_INFO_STATUS_AUTH_FAILED = 5;
	
	public static final Map<Integer, String> goodInfoAuthStatusMap = new HashMap<Integer,String>();
	
	public static final Map<Integer, String> goodInfoStatusMap = new HashMap<Integer,String>();
	
	/**
	 * 用户状态：0-有效
	 */
	public static final Integer ADMIN_USER_STATUS_ON = 0;
	
	/**
	 * 用户状态：1-失效
	 */
	public static final Integer ADMIN_USER_STATUS_OFF = 1;
	
	public static final Map<Integer, String> adminUserStatusMap = new HashMap<Integer, String>();
	
	/**
	 * 充值渠道：1-微信
	 */
	public static final Integer RECHARGE_CHANNEL_WECHAT = 1;
	
	/**
	 * 充值渠道：2-支付宝
	 */
	public static final Integer RECHARGE_CHANNEL_ALIPAY = 2;
	
	public static final Map<Integer, String> rechargeChannelMap = new HashMap<Integer, String>();
	
	/**
	 * 充值状态:0-未支付
	 */
	public static final Integer RECHARGE_STATUS_UNPAYED = 0;
	
	/**
	 * 充值状态:1-已支付
	 */
	public static final Integer RECHARGE_STATUS_SUCCESS = 1;
	
	/**
	 * 充值状态：2-支付失败
	 */
	public static final Integer RECHARGE_STATUS_FAILED = 2;
	
	public static final Map<Integer, String> rechargeStatusMap = new HashMap<Integer, String>();
	
	/**
	 * 短信状态：0-待发送
	 */
	public static final Integer SHORT_MESSAGE_STATUS_WAIT = 0;
	
	/**
	 * 短信状态：1-已发送
	 */
	public static final Integer SHORT_MESSAGE_STATUS_SUCCESS = 1;
	
	/**
	 * 短信状态：2-发送失败
	 */
	public static final Integer SHORT_MESSAGE_STATUS_FAILED = 2;
	
	public static final Map<Integer, String> shortMessageStatusMap = new HashMap<Integer, String>();
	
	/**
	 * 是否为批量上架：1-是
	 */
	public static final Integer IS_UP_ALL_YES = 1;
	
	/**
	 * 是否为批量上架：0-否
	 */
	public static final Integer IS_UP_ALL_NO = 0;
	
	/**
	 * 常用的商品标签
	 */
	public static final Map<String, Object> goodLabels = new HashMap<String, Object>();
	
	public static final Map<Integer, String> blackStatusMap = new HashMap<Integer, String>();
	public static final Map<Integer, String> userInfoTypeMap = new HashMap<Integer, String>();
	public static final Map<Integer, String> userInfoStatusMap = new HashMap<Integer, String>();
	public static final Map<Integer, String> sexMap = new HashMap<Integer, String>();
	public static final Map<Integer, String> bannerTypeMap = new HashMap<Integer, String>();
	public static final Map<Integer, String> bannerShowMap = new HashMap<Integer, String>();
	public static final Map<Integer, String> bannerPositionMap = new HashMap<Integer, String>();

	public static final Map<Integer, String> appTypeMap = new HashMap<Integer, String>();
	public static final Map<Integer, String> appForeMap = new HashMap<Integer, String>();
	public static final Map<Integer, String> questionTypeMap = new HashMap<Integer, String>();
	public static final Map<Integer, String> questionhotFlagMap = new HashMap<Integer, String>();

	
	public static final DecimalFormat MONEY_FORMAT = new DecimalFormat("##,##0.00");
	
	static {
		goodTypeStatusMap.put(GOOD_TYPE_STATUS_ON, "启用");
		goodTypeStatusMap.put(GOOD_TYPE_STATUS_OFF, "不启用");
		
		isPostMap.put(IS_POST_YES, "是");
		isPostMap.put(IS_POST_NO, "否");
		
		isShopGoodMap.put(IS_SHOP_GOOD_DIRECT, "直销商品");
		isShopGoodMap.put(IS_SHOP_GOOD_SHOP, "店铺商品");
		
		isRecommendMap.put(IS_RECOMMEND_YES, "是");
		isRecommendMap.put(IS_RECOMMEND_NO, "否");
		
		goodInfoStatusMap.put(GOOD_INFO_STATUS_UP, "上架");
		goodInfoStatusMap.put(GOOD_INFO_STATUS_DOWN, "下架");
		goodInfoStatusMap.put(GOOD_INFO_STATUS_AUTH_WAIT, "待审核");
		goodInfoStatusMap.put(GOOD_INFO_STATUS_AUTH_SUCCESS, "审核通过");
		goodInfoStatusMap.put(GOOD_INFO_STATUS_AUTH_FAILED, "审核不通过");
		
		goodInfoAuthStatusMap.put(GOOD_INFO_STATUS_AUTH_WAIT, "待审核");
		goodInfoAuthStatusMap.put(GOOD_INFO_STATUS_AUTH_SUCCESS, "审核通过");
		goodInfoAuthStatusMap.put(GOOD_INFO_STATUS_AUTH_FAILED, "审核不通过");
		
		adminUserStatusMap.put(ADMIN_USER_STATUS_ON, "有效");
		adminUserStatusMap.put(ADMIN_USER_STATUS_OFF, "失效");
		
		rechargeChannelMap.put(RECHARGE_CHANNEL_WECHAT, "微信");
		rechargeChannelMap.put(RECHARGE_CHANNEL_ALIPAY, "支付宝");
		
		rechargeStatusMap.put(RECHARGE_STATUS_UNPAYED, "未支付");
		rechargeStatusMap.put(RECHARGE_STATUS_SUCCESS, "已支付");
		rechargeStatusMap.put(RECHARGE_STATUS_FAILED, "支付失败");
		
		shortMessageStatusMap.put(SHORT_MESSAGE_STATUS_WAIT, "待发送");
		shortMessageStatusMap.put(SHORT_MESSAGE_STATUS_SUCCESS, "已发送");
		shortMessageStatusMap.put(SHORT_MESSAGE_STATUS_FAILED, "发送失败");

		blackStatusMap.put(1, "启用");
		blackStatusMap.put(0, "不启用");

		userInfoStatusMap.put(1, "禁用");
		userInfoStatusMap.put(0, "正常");

		sexMap.put(1, "女");
		sexMap.put(0, "男");

		appForeMap.put(1, "强制");
		appForeMap.put(0, "非强制");

		appTypeMap.put(1, "IOS");
		appTypeMap.put(0, "安卓");

		bannerTypeMap.put(0, "不连接");
		bannerTypeMap.put(1, "绝对路径");
		bannerTypeMap.put(2, "相对路径");
		bannerTypeMap.put(3, "页面内容");

		bannerShowMap.put(1, "显示");
		bannerShowMap.put(0, "不显示");

		bannerPositionMap.put(1, "顶部");
		bannerPositionMap.put(0, "底部");


		userInfoTypeMap.put(0, "普通用户");
		userInfoTypeMap.put(1, "经销商");
		userInfoTypeMap.put(2, "代理商");

		questionTypeMap.put(1, "注册");
		questionTypeMap.put(2, "充值");
		questionTypeMap.put(3, "订单");
		questionTypeMap.put(4, "账户安全");


		questionhotFlagMap.put(0, "否");
		questionhotFlagMap.put(1, "是");

	}
	
}
