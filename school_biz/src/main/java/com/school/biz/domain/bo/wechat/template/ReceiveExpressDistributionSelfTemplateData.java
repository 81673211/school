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
public class ReceiveExpressDistributionSelfTemplateData extends TemplateData {

    private TemplateDataItem keyword1;
    private TemplateDataItem keyword2;
    private TemplateDataItem keyword3;
    private TemplateDataItem keyword4;
    private TemplateDataItem keyword5;

    ReceiveExpressDistributionSelfTemplateData(TemplateDataItem keyword1,
                                               TemplateDataItem keyword2,
                                               TemplateDataItem keyword3,
                                               TemplateDataItem keyword4,
                                               TemplateDataItem keyword5,
                                               TemplateDataItem first,
                                               TemplateDataItem remark) {
        super(first, remark);
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
        this.keyword3 = keyword3;
        this.keyword4 = keyword4;
        this.keyword5 = keyword5;
    }

    public static class Builder {
        private TemplateDataItem keyword1;
        private TemplateDataItem keyword2;
        private TemplateDataItem keyword3 = new TemplateDataItem("无");
        private TemplateDataItem keyword4 = new TemplateDataItem("铜锣湾");
        private TemplateDataItem keyword5 = new TemplateDataItem("每日9:00 - 19:00");
        private TemplateDataItem first = new TemplateDataItem("亲，这是您的快递自提信息");
        private TemplateDataItem remark = new TemplateDataItem("请尽快在规定时间内到上述地址取件");

        public Builder buildKeyword1(String keyword1) {
            this.keyword1 = new TemplateDataItem(keyword1);
            return this;
        }

        public Builder buildKeyword2(String keyword2) {
            this.keyword2 = new TemplateDataItem(keyword2);
            return this;
        }

        public ReceiveExpressDistributionSelfTemplateData build() {
            return new ReceiveExpressDistributionSelfTemplateData(keyword1, keyword2, keyword3, keyword4,
                                                                  keyword5, first, remark);
        }
    }
}
