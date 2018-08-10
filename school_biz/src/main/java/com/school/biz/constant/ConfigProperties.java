package com.school.biz.constant;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;

import jodd.props.Props;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 09/08/2018 10:51
 */
public final class ConfigProperties {

    private static Props p = new Props();

    static {
        loadConfigProp();
    }

    private ConfigProperties() {
    }

    public static void loadConfigProp() {
        try {
            p.load(new ClassPathResource("config/config.properties").getInputStream());
            p.load(new ClassPathResource("config/wechat.properties").getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 微信支付证书保存路径
     */
    public static final String WXPAY_CERT_PATH = p.getValue("wxpay_cert_path");
    /**
     * 微信支付appid
     */
    public static final String WXPAY_APP_ID = p.getValue("wxpay_app_id");
    /**
     * 微信支付商户id
     */
    public static final String WXPAY_MCH_ID = p.getValue("wxpay_mch_id");
    /**
     * 微信支付密钥
     */
    public static final String WXPAY_KEY = p.getValue("wxpay_key");

    public static final String ID_WORKER_WORK_ID = p.getValue("idworker_work_id");

    public static final String ID_WORKER_DATA_ID = p.getValue("idworker_data_id");

    /**
     * 与接口配置信息中的Token要一致
     */
    public static final String TOKEN = p.getValue("token");

    /**
     * 第三方用户唯一凭证
     */
    public static final String APPID = p.getValue("appId");

    /**
     * 第三方用户唯一凭证密钥
     */
    public static final String APPSECRET = p.getValue("appSecret");

    public static final boolean REFRESH_MENU = Boolean.parseBoolean(p.getValue("refresh_menu"));

    /**
     * notifyUrl
     */
    public static final String WXPAY_NOTIFY_URL = p.getValue("wxpay_notify_url");
}
