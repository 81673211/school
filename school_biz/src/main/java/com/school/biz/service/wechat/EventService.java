package com.school.biz.service.wechat;

import java.util.Map;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 13/06/2018 16:28
 */
public interface EventService {

    String process(Map<String, String> paramMap);
}
