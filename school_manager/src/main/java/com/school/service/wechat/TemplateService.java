package com.school.service.wechat;

import com.school.common.model.wechat.Template;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 07/08/2018 10:16
 */
public interface TemplateService {

    /**
     * <b>Description:消息模板，注意keyword的顺序</b><br>
     * <b>Author:fred</b>
     * <br><b>Date:07/08/2018 14:29.</b>
     * <br><b>BackLog:</b>
     */
    void send(Template template);
}
