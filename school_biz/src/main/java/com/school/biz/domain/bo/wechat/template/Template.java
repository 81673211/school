package com.school.biz.domain.bo.wechat.template;

import lombok.Data;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 07/08/2018 10:21
 */
@Data
public class Template {

    private String template_id;
    private String touser;
    private String url;
    private TemplateData data;

    public Template(String id, String toUser, String url, TemplateData data) {
        this.template_id = id;
        this.touser = toUser;
        this.url = url;
        this.data = data;
    }

    public static class Builder {
        private String id;
        private String toUser;
        private String url;
        private TemplateData templateData;

        public Builder buildId(String id) {
            this.id = id;
            return this;
        }

        public Builder buildToUser(String toUser) {
            this.toUser = toUser;
            return this;
        }

        public Builder buildUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder buildTemplateData(TemplateData templateData) {
            this.templateData = templateData;
            return this;
        }

        public Template build() {
            return new Template(id, toUser, url, templateData);
        }
    }
}
