package com.school.biz.service.express.impl;

import com.alibaba.fastjson.JSON;
import com.school.biz.constant.RedisKeyNS;
import com.school.biz.domain.entity.customer.Customer;
import com.school.biz.domain.entity.express.ExpressReceive;
import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.domain.entity.order.RefundOrderInfo;
import com.school.biz.domain.vo.PushMessageVo;
import com.school.biz.enumeration.*;
import com.school.biz.service.customer.CustomerService;
import com.school.biz.service.express.ExpressReceiveService;
import com.school.biz.service.express.ExpressSendService;
import com.school.biz.service.express.ExpressService;
import com.school.biz.service.order.OrderInfoService;
import com.school.biz.service.wechat.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private TemplateService templateService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

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
                if (sendExpress.getExpressStatus().equals(SendExpressStatusEnum.INEFFECTIVE.getFlag()) && sendExpress.getExpressWay().equals(DistributionTypeEnum.DISTRIBUTION.getFlag())) {
                    //改为等待上门取件
                    expressSendService.updateSendExpressStatus(expressId, SendExpressStatusEnum.WAIT_SMQJ.getFlag());
                    alertAdmin(sendExpress);
                }
                //当前寄件状态为 已发起寄件 && 寄件方式为 自发
                else if (sendExpress.getExpressStatus().equals(SendExpressStatusEnum.INEFFECTIVE.getFlag()) && sendExpress.getExpressWay().equals(DistributionTypeEnum.SELF.getFlag())) {
                    //改为已发起寄件
                    expressSendService.updateSendExpressStatus(expressId, SendExpressStatusEnum.CREATE.getFlag());
                }
                //当前寄件状态为 补单待支付
                else if (sendExpress.getExpressStatus().equals(SendExpressStatusEnum.SUPPLEMENT.getFlag())) {
                    //改为准备寄出
                    expressSendService.updateSendExpressStatus(expressId, SendExpressStatusEnum.WAIT_SEND.getFlag());
                } else {
                    log.error("updateExpressByPay error,no know expressStatus:" + sendExpress.getExpressStatus());
                }
            } else if (expressType == ExpressTypeEnum.RECEIVE.getFlag()) {
                //收件只有选择配送的时候才有支付
                expressReceiveService.updateReceiveExpress(orderInfo.getExpressId());
                alertAdmin(orderInfo);
            } else {
                log.error("not support express type:" + expressId);
            }
        } catch (Exception e) {
            log.error("updateExpressByPay error", e);
            throw new RuntimeException(e);
        }
    }

    private void alertAdmin(ExpressSend sendExpress) {
        Customer customer = customerService.get(sendExpress.getCustomerId());
        if (customer != null) {
            templateService.send(WechatTemplateEnum.SEND_EXPRESS_ARRIVAL_ALERT.getType(),
                    customer.getOpenId(), sendExpress, ExpressTypeEnum.SEND.getFlag());
        }
    }

    private void alertAdmin(OrderInfo orderInfo) {
        ExpressReceive receiveExpress = expressReceiveService.getReceiveExpress(orderInfo.getExpressId());
        if (receiveExpress != null && ReceiveExpressTypeEnum.HELP_RECEIVE.getFlag() == receiveExpress.getExpressType()) {
            Customer customer = customerService.get(receiveExpress.getCustomerId());
            if (customer != null) {
                templateService.send(WechatTemplateEnum.RECEIVE_EXPRESS_ARRIVAL_ALERT.getType(),
                        customer.getOpenId(), receiveExpress, ExpressTypeEnum.RECEIVE.getFlag());
            }
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

    @Override
    public List<PushMessageVo> findPushMessageData() {
        List<PushMessageVo> list = new ArrayList<>();
        List<PushMessageVo> list1 = expressSendService.findPushMessageByExpressStatus(SendExpressStatusEnum.INEFFECTIVE);
        List<PushMessageVo> list2 = expressSendService.findPushMessageByExpressStatus(SendExpressStatusEnum.SUPPLEMENT);
        List<PushMessageVo> list3 = expressReceiveService.findPushOpenMessageByExpressStatus(ReceiveExpressStatusEnum.INEFFECTIVE);
        list.addAll(list1);
        list.addAll(list2);
        list.addAll(list3);
        //检查该消息是否推送过
        List<PushMessageVo> removeList = new ArrayList<>();
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        getRemoveList(list1, removeList, opsForValue, RedisKeyNS.PUSH_MESSAGE_SEND_INEFFECTIVE);
        getRemoveList(list2, removeList, opsForValue, RedisKeyNS.PUSH_MESSAGE_SEND_SUPPLEMENT);
        getRemoveList(list3, removeList, opsForValue, RedisKeyNS.PUSH_MESSAGE_RECEIVE_INEFFECTIVE);
        list.removeAll(removeList);
        return list;
    }

    @Override
    public Integer cleanPushMessageAndCancelExpress() {
        Integer count = 0;
        try {
            redisTemplate.delete("redis:push_message:*");
            count += expressSendService.updateIneffectiveToCancel();
            count += expressReceiveService.updateIneffectiveToCancel();
        } catch (Exception e) {
            log.error("cleanPushMessageAndCancelExpress error", e);
        }
        return count;
    }

    private void getRemoveList(List<PushMessageVo> list, List<PushMessageVo> removeList, ValueOperations<String, String> opsForValue, String keyPrefix) {
        for (PushMessageVo vo : list) {
            if (!opsForValue.setIfAbsent(keyPrefix + vo.getOpenId(), JSON.toJSONString(vo))) {
                removeList.add(vo);
            }
        }
    }
}
