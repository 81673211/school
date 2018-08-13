package com.school.biz.domain.bo.wechat.template;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.school.biz.enumeration.ReceiveExpressStatusEnum;

import lombok.Data;

/**
 *
 * <b>Description:代收点收到收件快递时发送给用户的模板消息.</b><br>
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 13/08/2018 14:13
 */
@Data
public class ReceiveExpressArrivalTemplateData extends TemplateData {

    private TemplateDataItem keyword1;
    private TemplateDataItem keyword2;

    ReceiveExpressArrivalTemplateData(TemplateDataItem keyword1,
                                      TemplateDataItem keyword2,
                                      TemplateDataItem first,
                                      TemplateDataItem remark) {
        super(first, remark);
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
    }

    public static class Builder {
        private TemplateDataItem keyword1 = new TemplateDataItem(
                new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        private TemplateDataItem keyword2;
        private TemplateDataItem first = new TemplateDataItem("您有一个待收快递已到代收点");
        private TemplateDataItem remark;

        public Builder buildKeyword2(String keyword2) {
            this.keyword2 = new TemplateDataItem(keyword2);
            return this;
        }

        public Builder buildRemark(String remark) {
            this.remark = new TemplateDataItem(remark);
            return this;
        }

        public ReceiveExpressArrivalTemplateData build() {
            return new ReceiveExpressArrivalTemplateData(keyword1, keyword2, first, remark);
        }
    }
}
