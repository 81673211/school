package com.school.service.wechat;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 13/06/2018 16:28
 */
public interface WechatEventService {

    String process(HttpServletRequest request) throws Exception;
}
