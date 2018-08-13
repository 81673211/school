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
public class ReceiveExpressAtProxyTemplateData extends TemplateData {

    private TemplateDataItem keyword1;
    private TemplateDataItem keyword2;
    private TemplateDataItem keyword3;
    private TemplateDataItem keyword4;
    private TemplateDataItem keyword5;

    public ReceiveExpressAtProxyTemplateData(TemplateDataItem keyword1,
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
        private TemplateDataItem keyword3 = new TemplateDataItem(new SimpleDateFormat("yyyy-MM-dd HH:MM").format(new Date()));
        private TemplateDataItem keyword4 = new TemplateDataItem("铜锣湾");
        private TemplateDataItem keyword5 = new TemplateDataItem(ReceiveExpressStatusEnum.PROXY_RECIEVED.getMessage());
        private TemplateDataItem first = new TemplateDataItem("您有一个待收快递已到代收点");
        private TemplateDataItem remark = new TemplateDataItem("请到待收快件页面选择自提或配送，如有疑问请致电：66776677");

        public Builder buildKeyword1(String keyword1) {
            this.keyword1 = new TemplateDataItem(keyword1);
            return this;
        }

        public Builder buildKeyword2(String keyword2) {
            this.keyword2 = new TemplateDataItem(keyword2);
            return this;
        }

        public ReceiveExpressAtProxyTemplateData build() {
            return new ReceiveExpressAtProxyTemplateData(keyword1, keyword2, keyword3, keyword4, keyword5,
                                                         first, remark);
        }
    }
}
