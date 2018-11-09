package com.school.biz.service.wechat.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.school.biz.constant.ConfigProperties;
import com.school.biz.domain.bo.wechat.template.ReceiveExpressArrivalAlertTemplateData;
import com.school.biz.domain.bo.wechat.template.ReceiveExpressArrivalTemplateData;
import com.school.biz.domain.bo.wechat.template.ReceiveExpressDistributionAlertTemplateData;
import com.school.biz.domain.bo.wechat.template.ReceiveExpressDistributionSelfTemplateData;
import com.school.biz.domain.bo.wechat.template.ReceiveExpressFinishTemplateData;
import com.school.biz.domain.bo.wechat.template.SendExpressArrivalAlertTemplateData;
import com.school.biz.domain.bo.wechat.template.Template;
import com.school.biz.domain.bo.wechat.template.TemplateData;
import com.school.biz.domain.bo.wechat.template.UnpayAlertTemplateData;
import com.school.biz.domain.entity.express.Express;
import com.school.biz.domain.entity.express.ExpressReceive;
import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.domain.entity.region.Region;
import com.school.biz.enumeration.PushMessageEnum;
import com.school.biz.enumeration.SendExpressStatusEnum;
import com.school.biz.enumeration.WechatTemplateEnum;
import com.school.biz.service.region.RegionService;
import com.school.biz.service.wechat.AccessTokenService;
import com.school.biz.service.wechat.TemplateService;
import com.school.biz.util.HttpUtil;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 07/08/2018 10:25
 */
@Service
@Transactional
@Slf4j
public class TemplateServiceImpl implements TemplateService {

    private static final String TEMPLATE_SEND_URL =
            "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=${ACCESS_TOKEN}";

    @Autowired
    private AccessTokenService accessTokenService;
    @Autowired
    private RegionService regionService;

    @Override
    public void send(Template template) {
        String accessToken = accessTokenService.get();
        String templateSendUrl = TEMPLATE_SEND_URL.replace("${ACCESS_TOKEN}", accessToken);
        try {
            HttpUtil.post(templateSendUrl, JSON.toJSONString(template), "UTF-8", false);
            log.info(JSON.toJSONString(template));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(PushMessageEnum pushMessageEnum, String createTime, String openId) {
        String templateId = "zTYIZ1C21Li3htdTgYjGR31wP5scX5O6MTPC0YrU8qE";
        TemplateData templateData = new UnpayAlertTemplateData.Builder().buildKeyword1(createTime).build();
        Template template = new Template.Builder()
                .buildId(templateId)
                .buildToUser(openId)
                .buildTemplateData(templateData)
                .build();
        send(template);
    }

    @Override
    public void send(String templateType, String openId, Express express, int expressType) {
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                String templateId = ConfigProperties.p.getValue(templateType);
                log.info("templateId:{}", templateId);
                TemplateData templateData;
                Template template;
                if (WechatTemplateEnum.RECEIVE_EXPRESS_ARRIVAL.getType().equals(templateType)) {
                    templateData = new ReceiveExpressArrivalTemplateData.Builder()
                            .buildKeyword2(express.getCompanyName())
                            .buildRemark("快递单号：${code} \r请到待收快件页面选择取件方式"
                                                 .replace("${code}", express.getCode())).build();
                    template = new Template.Builder()
                            .buildId(templateId)
                            .buildToUser(openId)
                            .buildTemplateData(templateData).build();
                    send(template);
                } else if (WechatTemplateEnum.RECEIVE_EXPRESS_DISTRIBUTION_SELF.getType().equals(templateType)) {
                    templateData = new ReceiveExpressDistributionSelfTemplateData.Builder()
                            .buildKeyword1(express.getCode())
                            .buildKeyword2(express.getCompanyName()).build();
                    template = new Template.Builder()
                            .buildId(templateId)
                            .buildToUser(openId)
                            .buildTemplateData(templateData).build();
                    send(template);
                } else if (WechatTemplateEnum.RECEIVE_EXPRESS_FINISH.getType().equals(templateType)) {
                    templateData = new ReceiveExpressFinishTemplateData.Builder()
                            .buildKeyword1("四川大学锦江学院学生公寓14栋背面，学生公寓15栋东侧")
                            .buildKeyword2(express.getCode())
                            .build();
                    template = new Template.Builder()
                            .buildId(templateId)
                            .buildToUser(openId)
                            .buildTemplateData(templateData).build();
                    send(template);
                } else if (WechatTemplateEnum.SEND_EXPRESS_ARRIVAL_ALERT.getType().equals(templateType)) {
                    ExpressSend expressSend = (ExpressSend) express;
                    Integer expressStatus = expressSend.getExpressStatus();
                    String status;
                    if (expressStatus == SendExpressStatusEnum.CREATE.getFlag()) {
                        status = "用户会送到集散中心";
                    } else {
                        status = "等待上门取件";
                    }
                    String info = "快递单号：" + expressSend.getCode() + ", " +
                                  "收件人姓名：" + expressSend.getReceiverName() + " " +
                                  "收件人电话：" + expressSend.getReceiverPhone() + " " +
                                  "寄件人姓名：" + expressSend.getSenderName() + " " +
                                  "寄件人电话：" + expressSend.getSenderPhone() + " " +
                                  "寄件人地址：" + expressSend.getSenderAddr() + " " +
                                  "状态：" + status;
                    Integer sendExpressType = expressSend.getExpressType();
                    String sendExpressTypeName;
                    switch (sendExpressType) {
                        case 1:sendExpressTypeName = "文件";break;
                        case 2:sendExpressTypeName = "数码产品";break;
                        case 3:sendExpressTypeName = "日用品";break;
                        case 4:sendExpressTypeName = "服饰";break;
                        case 5:sendExpressTypeName = "食品";break;
                        case 6:sendExpressTypeName = "医药类产品";break;
                        default:sendExpressTypeName = "其它";
                    }

                    Region province = regionService.get(expressSend.getReceiverProvinceId());
                    Region city = regionService.get(expressSend.getReceiverCityId());
                    Region district = regionService.get((expressSend.getReceiverDistrictId()));
                    String receiverAddr = (province == null ? "" : province.getAreaName()) + " " +
                                          (city == null ? "" : city.getAreaName()) + " " +
                                          (district == null ? "" : district.getAreaName()) + " " +
                                          expressSend.getReceiverAddr();
                    templateData = new SendExpressArrivalAlertTemplateData.Builder()
                            .buildKeyword1(expressSend.getSenderName())
                            .buildKeyword2(info)
                            .buildKeyword3(receiverAddr)
                            .buildKeyword4(expressSend.getCompanyName())
                            .buildKeyword5(sendExpressTypeName)
                            .buildRemark("请关注")
                            .build();
                    batchSend(templateId, templateData);
                } else if (WechatTemplateEnum.RECEIVE_EXPRESS_ARRIVAL_ALERT.getType().equals(templateType)) {
                    ExpressReceive expressReceive = (ExpressReceive) express;
                    String helpReceiveCode = expressReceive.getHelpReceiveCode();
                    String realCode = helpReceiveCode == null ? "无" : helpReceiveCode;
                    String remark = "快递单号：" + expressReceive.getCode() + ", " +
                                    "配送方式：" + getDistributionType(expressReceive.getHelpDistributionType()) + ", " +
                                    "收件人地址：" + expressReceive.getReceiverAddr() + ", " +
                                    "收件人姓名：" + expressReceive.getReceiverName() + " " +
                                    "收件人电话：" + expressReceive.getReceiverPhone() + " " +
                                    "快递公司：" + expressReceive.getCompanyName() + " " +
                                    "取件码：" + realCode + " " +
                                    "取件地址：" + expressReceive.getHelpReceiveAddr();
                    templateData = new ReceiveExpressArrivalAlertTemplateData.Builder()
                            .buildKeyword1(expressReceive.getReceiverName())
                            .buildRemark(remark)
                            .build();

                    batchSend(templateId, templateData);
                } else if (WechatTemplateEnum.RECEIVE_EXPRESS_DISTRIBUTION_ALERT.getType().equals(templateType)) {
                    ExpressReceive expressReceive = (ExpressReceive) express;
                    Integer expressWay = expressReceive.getExpressWay();
                    String remarkDistribution;
                    switch (expressWay) {
                        case 0: remarkDistribution = "自提"; break;
                        case 1: remarkDistribution = "配送入柜"; break;
                        case 2: remarkDistribution = "送货上门"; break;
                        default: remarkDistribution = "自提"; break;
                    }
                    String remark = "快递单号：" + expressReceive.getCode() + ", " +
                                    "收件人姓名：" + expressReceive.getReceiverName() + " " +
                                    "收件人电话：" + expressReceive.getReceiverPhone() + " " +
                                    "收件人地址：" + expressReceive.getReceiverAddr() + " " +
                                    "配送方式：" + remarkDistribution + " " +
                                    "快递公司：" + expressReceive.getCompanyName();
                    templateData = new ReceiveExpressDistributionAlertTemplateData.Builder()
                            .buildKeyword3(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expressReceive.getCreatedTime()))
                            .buildRemark(remark)
                            .build();

                    batchSend(templateId, templateData);
                }
            }
        });

    }

    private String getDistributionType(String orig) {
        if (StringUtils.isBlank(orig)) {
            return "";
        } else if ("door".equals(orig)) {
            return "送货上门";
        } else {
            return "入柜";
        }
    }

    private void batchSend(String templateId, TemplateData templateData) {
        List<String> openIds = Arrays.asList("oSAxK1BqVfUy1gFW_1HtISgQ4VhY", //王玲
                                             "oSAxK1EzD7Qk6txf9nHKYOzHD8Vk", //别踩井盖
                                             "oSAxK1ED-3bFrTDVJdy-U1JZi-Ws"); //李姝锦
        Template template;
        for (String id : openIds) {
            template = new Template.Builder()
                    .buildId(templateId)
                    .buildToUser(id)
                    .buildTemplateData(templateData).build();
            send(template);
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
    }
}
