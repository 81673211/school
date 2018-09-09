package com.school.biz.domain.bo.wechat.template;

import lombok.Data;

/**
 *
 * <b>Description:代收点收到收件快递时发送给用户的模板消息.</b><br>
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 13/08/2018 14:13
 */
@Data
public class UnpayAlertTemplateData extends TemplateData {

    private TemplateDataItem keyword1;
    private TemplateDataItem keyword2;
    private TemplateDataItem keyword3;

    UnpayAlertTemplateData(TemplateDataItem keyword1,
                           TemplateDataItem keyword2,
                           TemplateDataItem keyword3,
                           TemplateDataItem first,
                           TemplateDataItem remark) {
        super(first, remark);
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
        this.keyword3 = keyword3;
    }

    public static class Builder {
        private TemplateDataItem keyword1;
        private TemplateDataItem keyword2 = new TemplateDataItem("");
        private TemplateDataItem keyword3 = new TemplateDataItem("");
        private TemplateDataItem first = new TemplateDataItem("您好！您有一笔快递订单尚未支付");
        private TemplateDataItem remark = new TemplateDataItem("为了保障您的快递能及时寄出或及时收到，请尽快支付，如已支付过请忽略该消息");

        public Builder buildKeyword1(String keyword1) {
            this.keyword1 = new TemplateDataItem(keyword1);
            return this;
        }

        public UnpayAlertTemplateData build() {
            return new UnpayAlertTemplateData(keyword1, keyword2, keyword3, first, remark);
        }
    }
}
