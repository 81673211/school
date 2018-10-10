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
public class ReceiveExpressDistributionAlertTemplateData extends TemplateData {

    private TemplateDataItem keyword1;
    private TemplateDataItem keyword2;
    private TemplateDataItem keyword3;

    ReceiveExpressDistributionAlertTemplateData(TemplateDataItem keyword1,
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
        private TemplateDataItem keyword1 = new TemplateDataItem("收件");
        private TemplateDataItem keyword2 = new TemplateDataItem("无");
        private TemplateDataItem keyword3;
        private TemplateDataItem first = new TemplateDataItem("收件配送方式通知，请速处理");
        private TemplateDataItem remark;

        public Builder buildKeyword3(String keyword3) {
            this.keyword3 = new TemplateDataItem(keyword3);
            return this;
        }

        public Builder buildRemark(String remark) {
            this.remark = new TemplateDataItem(remark);
            return this;
        }

        public ReceiveExpressDistributionAlertTemplateData build() {
            return new ReceiveExpressDistributionAlertTemplateData(keyword1, keyword2, keyword3, first, remark);
        }
    }
}
