package com.school.manager.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.school.biz.constant.ConfigProperties;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.domain.vo.PushMessageVo;
import com.school.biz.extension.wxpay.sdk.WXPay;
import com.school.biz.extension.wxpay.sdk.WXPayConfigImpl;
import com.school.biz.extension.wxpay.sdk.WXPayConstants.SignType;
import com.school.biz.extension.wxpay.sdk.WXPayUtil;
import com.school.biz.service.express.ExpressService;
import com.school.biz.service.order.OrderInfoService;
import com.school.biz.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 未支付快递消息推送：快递创建后，10分钟内还未支付成功则推送消息给用户
 */
@Slf4j
@Service(value = "NotExpressQuartz")
public class NotExpressQuartz {

	@Autowired
	private ExpressService expressService;

    public void execute() throws Exception {
        log.info("==========NotExpressQuartz：未支付快递消息推送==========");
        
        List<PushMessageVo> pushMessageVos = expressService.findPushMessageData();
    }

}
