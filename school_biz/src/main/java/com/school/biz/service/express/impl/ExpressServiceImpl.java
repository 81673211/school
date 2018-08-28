package com.school.biz.service.express.impl;

import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.domain.entity.order.RefundOrderInfo;
import com.school.biz.enumeration.*;
import com.school.biz.service.express.ExpressReceiveService;
import com.school.biz.service.express.ExpressSendService;
import com.school.biz.service.express.ExpressService;
import com.school.biz.service.order.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author jame
 * @date 2018/8/26
 * @desc description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpressServiceImpl implements ExpressService {

    @Autowired
    private ExpressSendService expressSendService;
    @Autowired
    private ExpressReceiveService expressReceiveService;
    @Autowired
    private OrderInfoService orderInfoService;

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
                if (sendExpress == null) {
                    log.error("not find send express record,expressId:" + expressId);
                    return;
                }
                //当前寄件状态为 已发起寄件 && 寄件方式为 配送
                if (sendExpress.getExpressStatus().equals(SendExpressStatusEnum.CREATE.getFlag()) && sendExpress.getExpressWay().equals(DistributionTypeEnum.DISTRIBUTION.getFlag())) {
                    //改为等待上门取件 && 寄件方式为 自发
                    expressSendService.updateSendExpressStatus(expressId, SendExpressStatusEnum.WAIT_SMQJ.getFlag());
                } else if (sendExpress.getExpressStatus().equals(SendExpressStatusEnum.CREATE.getFlag()) && sendExpress.getExpressWay().equals(DistributionTypeEnum.SELF.getFlag())) {
//                    expressSendService.updateSendExpressStatus(expressId, SendExpressStatusEnum.PROXY_RECIEVED.getFlag());
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
    public void updateExpressByRefund(RefundOrderInfo refundOrderInfo) throws RuntimeException {
        ExpressSend sendExpress = expressSendService.getSendExpress(refundOrderInfo.getExpressId());
        if (sendExpress == null) {
            log.error("not find send express record,expressId:" + refundOrderInfo.getExpressId());
            return;
        }
        //是否全额退款
        boolean flag = orderInfoService.isRefundAll(sendExpress);
        log.info("是否退全款：" + flag);
        if (flag) {
            expressSendService.updateSendExpressStatus(sendExpress.getId(), SendExpressStatusEnum.CANCEL.getFlag());
        }
    }
}
