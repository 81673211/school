package com.school.biz.service.express;

import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.domain.entity.order.RefundOrderInfo;
import com.school.biz.domain.vo.PushMessageVo;

import java.util.List;

/**
 * @author jame
 * @date 2018/8/26
 * @desc description
 */
public interface ExpressService {

    /**
     * 支付之后的快件状态以及相关流程运转
     *
     * @param orderInfo
     */
    void updateExpressByPay(OrderInfo orderInfo) throws RuntimeException;


    /**
     * 退款之后的快件状态以及相关流程运转
     *
     * @param refundOrderInfo
     */
    void updateExpressByRefund(RefundOrderInfo refundOrderInfo) throws RuntimeException;

    /**
     * 获取需要推送消息的数据
     *
     * @return
     */
    List<PushMessageVo> findPushMessageData();
}
