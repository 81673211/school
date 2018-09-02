package com.school.biz.domain.bo.wechat.template;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

/**
 *
 * <b>Description:代收点收到收件快递时发送给用户的模板消息.</b><br>
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 13/08/2018 14:13
 */
@Data
public class ReceiveExpressArrivalAlertTemplateData extends TemplateData {

    private TemplateDataItem keyword1;
    private TemplateDataItem keyword2;

    ReceiveExpressArrivalAlertTemplateData(TemplateDataItem keyword1,
                                           TemplateDataItem keyword2,
                                           TemplateDataItem first,
                                           TemplateDataItem remark) {
        super(first, remark);
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
    }

    public static class Builder {
        private TemplateDataItem keyword1;
        private TemplateDataItem keyword2 = new TemplateDataItem(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        private TemplateDataItem first = new TemplateDataItem("有新的帮我取件订单，请速处理");
        private TemplateDataItem remark;

        public Builder buildKeyword1(String keyword1) {
            this.keyword1 = new TemplateDataItem(keyword1);
            return this;
        }

        public Builder buildRemark(String remark) {
            this.remark = new TemplateDataItem(remark);
            return this;
        }

        public ReceiveExpressArrivalAlertTemplateData build() {
            return new ReceiveExpressArrivalAlertTemplateData(keyword1, keyword2, first, remark);
        }
    }
}
