package com.school.service.wechat;

import com.school.util.wechat.UserWechat;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 12/06/2018 21:02
 */
public interface OauthService {

    String getOAuthUrl();

    UserWechat getDetail(String openId);


}
