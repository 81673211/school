package com.school.biz.domain.bo.wechat.template;

import lombok.Data;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 13/08/2018 14:18
 */
@Data
public class TemplateDataItem {

    private String value;

    private String color;

    public TemplateDataItem(String value) {
        this.value = value;
    }

}
