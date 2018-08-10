package com.school.biz.service.wechat;

import com.school.biz.domain.bo.wechat.OAuthToken;
import com.school.biz.domain.bo.wechat.UserWechat;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 12/06/2018 21:02
 */
public interface OauthService {

    String getOAuthUrl(String state);

    UserWechat getDetail(String openId, String accessToken);

    OAuthToken getOAuthToken(String code);

    int check(String openId);
}
