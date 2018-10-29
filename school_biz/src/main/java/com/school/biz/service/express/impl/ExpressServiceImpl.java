package com.school.biz.service.express.impl;

import com.alibaba.fastjson.JSON;
import com.school.biz.constant.RedisKeyNS;
import com.school.biz.domain.entity.customer.Customer;
import com.school.biz.domain.entity.express.ExpressReceive;
import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.domain.entity.order.RefundOrderInfo;
import com.school.biz.domain.entity.supplement.SupplementInfo;
import com.school.biz.domain.vo.PushMessageVo;
import com.school.biz.enumeration.*;
import com.school.biz.service.customer.CustomerService;
import com.school.biz.service.express.ExpressReceiveService;
import com.school.biz.service.express.ExpressSendService;
import com.school.biz.service.express.ExpressService;
import com.school.biz.service.order.OrderInfoService;
import com.school.biz.service.supplement.SupplementService;
import com.school.biz.service.wechat.TemplateService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    @Autowired
    private SupplementService supplementService;

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
                if (sendExpress.getExpressStatus().equals(SendExpressStatusEnum.INEFFECTIVE.getFlag()) && sendExpress.getExpressWay().equals(
                        SendExpressCollectTypeEnum.DOOR.getFlag())) {
                    //改为等待上门取件
                    expressSendService.updateSendExpressStatus(expressId, SendExpressStatusEnum.WAIT_SMQJ.getFlag());
                    alertAdmin(sendExpress);
                }
                //当前寄件状态为 已发起寄件 && 寄件方式为 自发
                else if (sendExpress.getExpressStatus().equals(SendExpressStatusEnum.INEFFECTIVE.getFlag()) && sendExpress.getExpressWay().equals(
                        SendExpressCollectTypeEnum.SELF.getFlag())) {
                    //改为已发起寄件
                    expressSendService.updateSendExpressStatus(expressId, SendExpressStatusEnum.CREATE.getFlag());
                }
                //当前寄件状态为 补单待支付
                else if (sendExpress.getExpressStatus().equals(SendExpressStatusEnum.SUPPLEMENT.getFlag())) {
                    AmountVo amountVo = getSupplementAmount(orderInfo);
                    if (amountVo.getServiceAmt().compareTo(BigDecimal.ZERO) > 0) {
                        //修改服务费
                        expressSendService.updateServiceAmt(amountVo.getServiceAmt(), expressId);
                    }
                    if (amountVo.getExpressAmt().compareTo(BigDecimal.ZERO) > 0) {
                        //修改运费
                        expressSendService.updateReOrderAmt(amountVo.getExpressAmt(), expressId);
                    }
                    //判断该快件是否所有补单的都支付完毕
                    if (supplementService.checkIsPayOff(expressId)) {
                        //改为已经补单
                        expressSendService.updateSendExpressStatus(expressId, SendExpressStatusEnum.HAS_SUPPLEMENT.getFlag());
                    }
                } else {
                    log.error("updateExpressByPay error,no know expressStatus:" + sendExpress.getExpressStatus());
                }
            } else if (expressType == ExpressTypeEnum.RECEIVE.getFlag()) {
                ExpressReceive expressReceive = expressReceiveService.getReceiveExpress(expressId);
                //当前收件状态为 补单待支付
                if (expressReceive.getExpressStatus().equals(ReceiveExpressStatusEnum.SUPPLEMENT.getFlag())) {
                    AmountVo amountVo = getSupplementAmount(orderInfo);
                    if (amountVo.getServiceAmt().compareTo(BigDecimal.ZERO) > 0) {
                        //修改服务费
                        expressReceiveService.updateServiceAmt(amountVo.getServiceAmt(), expressId);
                    }
                    //判断该快件是否所有补单的都支付完毕
                    if (supplementService.checkIsPayOff(expressId)) {
                        //改为已经补单
                        expressReceiveService.updateReceiveExpressStatus(expressId, ReceiveExpressStatusEnum.HAS_SUPPLEMENT.getFlag());
                    }
                } else {
                    //收件非补单支付
                    expressReceiveService.updateReceiveExpress(expressId);
                }
            } else {
                log.error("not support express type:" + expressId);
            }
        } catch (Exception e) {
            log.error("updateExpressByPay error", e);
            throw new RuntimeException(e);
        }
    }

    private AmountVo getSupplementAmount(OrderInfo orderInfo) {
        //计算费用
        AmountVo amountVo = new AmountVo();
        List ids = orderInfoService.selectSupplementIdsById(orderInfo.getId());
        List<SupplementInfo> supplementInfos = supplementService.selectByIds(ids);
        BigDecimal serviceAmt = BigDecimal.ZERO;
        BigDecimal expressAmt = BigDecimal.ZERO;
        for (SupplementInfo supplementInfo : supplementInfos) {
            supplementService.updateIsPay(supplementInfo.getId());
            if (supplementInfo.getType().equals(SupplementTypeEnum.SERVICE_AMT.getCode())) {
                serviceAmt = serviceAmt.add(supplementInfo.getAmount());
            } else {
                expressAmt = expressAmt.add(supplementInfo.getAmount());
            }
        }
        amountVo.setServiceAmt(serviceAmt);
        amountVo.setExpressAmt(expressAmt);
        return amountVo;
    }

    @Data
    public class AmountVo {
        private BigDecimal serviceAmt = BigDecimal.ZERO;
        private BigDecimal expressAmt = BigDecimal.ZERO;
    }

    private void alertAdmin(ExpressSend sendExpress) {
        Customer customer = customerService.get(sendExpress.getCustomerId());
        if (customer != null) {
            templateService.send(WechatTemplateEnum.SEND_EXPRESS_ARRIVAL_ALERT.getType(),
                    customer.getOpenId(), sendExpress, ExpressTypeEnum.SEND.getFlag());
        }
    }

    @Override
    public void updateExpressByRefund(RefundOrderInfo refundOrderInfo) throws RuntimeException {
        Integer expressType = refundOrderInfo.getExpressType();
        if (ExpressTypeEnum.RECEIVE.getFlag() == expressType) {
            ExpressReceive receiveExpress = expressReceiveService.getReceiveExpress(
                    refundOrderInfo.getExpressId());
            if (receiveExpress == null) {
                log.error("not find receive express record,expressId:" + refundOrderInfo.getExpressId());
                return;
            }
            //是否全额退款
            boolean flag = orderInfoService.isRefundAll(receiveExpress);
            log.info("是否退全款：" + flag);
            if (flag) {
                expressReceiveService.updateReceiveExpressStatus(receiveExpress.getId(),
                                                                 SendExpressStatusEnum.CANCEL.getFlag());
            }
        } else {
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
            redisTemplate.delete(redisTemplate.keys("redis:push_message:*"));
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
