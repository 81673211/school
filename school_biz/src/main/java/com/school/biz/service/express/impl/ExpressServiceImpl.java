package com.school.biz.service.express.impl;

import com.school.biz.domain.entity.express.ExpressReceive;
import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.enumeration.*;
import com.school.biz.service.express.ExpressReceiveService;
import com.school.biz.service.express.ExpressSendService;
import com.school.biz.service.express.ExpressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author jame
 * @date 2018/8/26
 * @desc description
 */
@Slf4j
@Service
public class ExpressServiceImpl implements ExpressService {

    @Autowired
    private ExpressSendService expressSendService;
    @Autowired
    private ExpressReceiveService expressReceiveService;

    @Override
    public void updateExpressByPay(OrderInfo orderInfo) throws RuntimeException {
        try {
            Long expressId = orderInfo.getExpressId();
            //0,寄件，1，收件
            Integer expressType = orderInfo.getExpressType();
            //0-未支付；1-支付成功；2-支付失败；3-支付处理中；4-过期
            Integer status = orderInfo.getStatus();
            if (!Objects.equals(status, OrderStatusEnum.SUCCESS.getCode())) {
                log.error("updateExpressByPay error,because order status not success");
                return;
            }
            if (expressType == ExpressTypeEnum.SEND.getFlag()) {
                ExpressSend sendExpress = expressSendService.getSendExpress(expressId);
                //当前寄件状态为 已发起寄件
                if (sendExpress.getExpressStatus().equals(SendExpressStatusEnum.CREATE.getFlag())) {
                    //改为等待上门取件
                    expressSendService.updateSendExpressStatus(expressId, SendExpressStatusEnum.WAIT_SMQJ.getFlag());
                }
                //当前寄件状态为 补单待支付
                else if (sendExpress.getExpressStatus().equals(SendExpressStatusEnum.SUPPLEMENT.getFlag())) {
                    //改为准备寄出
                    expressSendService.updateSendExpressStatus(expressId, SendExpressStatusEnum.WAIT_SEND.getFlag());
                } else {
                    log.error("updateExpressByPay error,no know expressStatus:" + sendExpress.getExpressStatus());
                }
            } else if (expressType == ExpressTypeEnum.RECEIVE.getFlag()) {
                //收件只有选择配送的时候才有支付，所以直接修改为等待入柜
                expressReceiveService.updateReceiveExpress(orderInfo.getExpressId(),
                        ReceiveExpressStatusEnum.WAIT_INTO_BOX
                                .getFlag(),
                        DistributionTypeEnum.DISTRIBUTION
                                .getFlag());
            } else {
                log.error("not support express type:" + expressId);
            }
        } catch (Exception e) {
            log.error("updateExpressByPay error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateExpressByRefund(OrderInfo orderInfo) throws RuntimeException {

    }
}
