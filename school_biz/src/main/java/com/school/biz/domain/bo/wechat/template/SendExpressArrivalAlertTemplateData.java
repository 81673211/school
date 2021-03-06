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
public class SendExpressArrivalAlertTemplateData extends TemplateData {

    private TemplateDataItem keyword1;
    private TemplateDataItem keyword2;
    private TemplateDataItem keyword3;
    private TemplateDataItem keyword4;
    private TemplateDataItem keyword5;

    SendExpressArrivalAlertTemplateData(TemplateDataItem keyword1,
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
        private TemplateDataItem keyword3;
        private TemplateDataItem keyword4;
        private TemplateDataItem keyword5;
        private TemplateDataItem first = new TemplateDataItem("有新的寄件订单，请速处理");
        private TemplateDataItem remark;

        public Builder buildKeyword1(String keyword1) {
            this.keyword1 = new TemplateDataItem(keyword1);
            return this;
        }

        public Builder buildKeyword2(String keyword2) {
            this.keyword2 = new TemplateDataItem(keyword2);
            return this;
        }

        public Builder buildKeyword3(String keyword3) {
            this.keyword3 = new TemplateDataItem(keyword3);
            return this;
        }

        public Builder buildKeyword4(String keyword4) {
            this.keyword4 = new TemplateDataItem(keyword4);
            return this;
        }

        public Builder buildKeyword5(String keyword5) {
            this.keyword5 = new TemplateDataItem(keyword5);
            return this;
        }

        public Builder buildRemark(String remark) {
            this.remark = new TemplateDataItem(remark);
            return this;
        }

        public SendExpressArrivalAlertTemplateData build() {
            return new SendExpressArrivalAlertTemplateData(keyword1, keyword2, keyword3, keyword4, keyword5, first, remark);
        }
    }
}
