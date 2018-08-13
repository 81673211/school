package com.school.biz.domain.bo.wechat.template;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 13/08/2018 14:15
 */
@Data
@AllArgsConstructor
public abstract class TemplateData {

    private TemplateDataItem first;
    private TemplateDataItem remark;
}
