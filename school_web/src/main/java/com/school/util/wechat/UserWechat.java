package com.school.util.wechat;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 微信关注者用户信息
 * @author caspar.chen
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class UserWechat {
	
	/**
	 * 用户的标识，对当前公众号唯一
	 */
	private String openId;
	
	/**
	 * 用户的昵称
	 */
	private String nickname;

	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
	private Integer sex;

	/**
	 * 用户头像，最后一个数值代表正方形头像大小
	 * （有0、46、64、96、132数值可选，0代表640*640正方形头像），
	 * 用户没有头像时该项为空
	 */
	private String avatar;
}
