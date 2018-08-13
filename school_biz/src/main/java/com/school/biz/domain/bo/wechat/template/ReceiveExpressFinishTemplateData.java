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
public class ReceiveExpressFinishTemplateData extends TemplateData {

    private TemplateDataItem keyword1;
    private TemplateDataItem keyword2;
    private TemplateDataItem keyword3;

    ReceiveExpressFinishTemplateData(TemplateDataItem keyword1,
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
        private TemplateDataItem keyword2;
        private TemplateDataItem keyword3 = new TemplateDataItem(
                new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        private TemplateDataItem first = new TemplateDataItem("您已成功取件，感谢您对我们的支持^_^");
        private TemplateDataItem remark = new TemplateDataItem("感谢您的使用。");

        public Builder buildKeyword1(String keyword1) {
            this.keyword1 = new TemplateDataItem(keyword1);
            return this;
        }

        public Builder buildKeyword2(String keyword2) {
            this.keyword2 = new TemplateDataItem(keyword2);
            return this;
        }

        public ReceiveExpressFinishTemplateData build() {
            return new ReceiveExpressFinishTemplateData(keyword1, keyword2, keyword3, first, remark);
        }
    }
}
