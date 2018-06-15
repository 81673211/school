package com.school.service.wechat;

import com.school.util.wechat.OAuthToken;
import com.school.util.wechat.UserWechat;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 12/06/2018 21:02
 */
public interface OauthService {

    boolean isOAuthed(String openId);

    String getOAuthUrl(String redirectUrl);

    UserWechat getDetail(String openId);

    OAuthToken getOAuthToken(String code);
}
