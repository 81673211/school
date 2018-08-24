package com.school.biz.service.order.impl;

import com.school.biz.constant.ConfigProperties;
import com.school.biz.constant.Constants;
import com.school.biz.dao.express.ExpressReceiveMapper;
import com.school.biz.dao.express.ExpressSendMapper;
import com.school.biz.dao.order.OrderInfoMapper;
import com.school.biz.domain.entity.express.Express;
import com.school.biz.domain.entity.express.ExpressReceive;
import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.enumeration.ExpressTypeEnum;
import com.school.biz.enumeration.OrderStatusEnum;
import com.school.biz.service.base.impl.BaseServiceImpl;
import com.school.biz.service.calc.CalcCostService;
import com.school.biz.service.order.OrderInfoService;
import com.school.biz.util.DateUtil;
import com.school.biz.util.IdWorkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class OrderInfoServiceImpl extends BaseServiceImpl<OrderInfo, OrderInfoMapper> implements OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private ExpressReceiveMapper expressReceiveMapper;
    @Autowired
    private CalcCostService calcCostService;

    @Override
    public OrderInfo findByOrderNo(String orderNo) {
        return orderInfoMapper.findByOrderNo(orderNo);
    }

    @Override
    public String createSendOrder(ExpressSend expressSend) {
        OrderInfo orderInfo = initOrderInfo(expressSend);
        if (!(orderInfoMapper.insertSelective(orderInfo) > 0)) {
            String message =
                    "create send order error,when insert table 'order_info' the number of affected rows is 0";
            log.error(message);
            throw new RuntimeException(message);
        } else {
            return orderInfo.getOrderNo();
        }
    }

    @Override
    public String createReceiveOrder(Long expressId) {
        OrderInfo orderInfo = findByExpressReceiveId(expressId);
        if (orderInfo == null || canRecreate(orderInfo)) {
            ExpressReceive expressReceive = expressReceiveMapper.selectByPrimaryKey(expressId);
            orderInfo = initOrderInfo(expressReceive);
            int result = orderInfoMapper.insertSelective(orderInfo);
            if (result <= 0) {
                String message =
                        "create receive order error,when insert table 'order_info' the number of affected rows is 0";
                log.error(message);
                throw new RuntimeException(message);
            }
        }
        return orderInfo.getOrderNo();
    }

    private boolean canRecreate(OrderInfo orderInfo) {
        int status = orderInfo.getStatus();
        if (OrderStatusEnum.SUCCESS.getCode().equals(status)) {
            log.error("重复支付，orderNo:{}", orderInfo.getOrderNo());
            throw new RuntimeException("快递已成功支付过，请勿重复支付.");
        }
        return OrderStatusEnum.FAILED.getCode() == status ||
                OrderStatusEnum.PAYING.getCode() == status ||
                OrderStatusEnum.EXPIRED.getCode() == status;
    }

    @Override
    public OrderInfo findByExpressReceiveId(Long expressId) {
        return findByExpressIdAndType(expressId, ExpressTypeEnum.RECEIVE.getFlag());
    }

    @Override
    public OrderInfo findByExpressSendId(Long expressId) {
        return findByExpressIdAndType(expressId, ExpressTypeEnum.SEND.getFlag());
    }

    private OrderInfo findByExpressIdAndType(Long expressId, int expressType) {
        return orderInfoMapper.findByExpressIdAndType(expressId, expressType);
    }

    /**
     * 初始化订单对象
     *
     * @param express
     * @return
     */
    private OrderInfo initOrderInfo(Express express) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setTradeSummary("express");
        if (express instanceof ExpressSend) {
            ExpressSend expressSend = (ExpressSend) express;
            orderInfo.setExpressType(ExpressTypeEnum.SEND.getFlag());
            orderInfo.setAmount(calcCostService.calcSendTransportCost(expressSend).add(
                    calcCostService.calcSendDistributionCost(expressSend.getExpressWay())));
        } else if (express instanceof ExpressReceive) {
            ExpressReceive expressReceive = (ExpressReceive) express;
            orderInfo.setExpressType(ExpressTypeEnum.RECEIVE.getFlag());
            orderInfo.setAmount(calcCostService.calcReceiveDistributionCost(expressReceive.getExpressWay()));
        } else {
            String errorMsg = "error express type.";
            log.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        orderInfo.setExpressId(express.getId());
        orderInfo.setExpressCode(express.getCode());
        orderInfo.setCustomerId(express.getCustomerId());
        orderInfo.setStatus(OrderStatusEnum.UNPAY.getCode());
        orderInfo.setOrderNo(IdWorkerUtil.generateOrderNo(Constants.ORDER_NO_TYPE_ORDER));
        orderInfo.setNotifyUrl(ConfigProperties.WXPAY_NOTIFY_URL);
        return orderInfo;
    }

    @Override
    public List<OrderInfo> getNotPayOrder() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tenMinutesAgo", DateUtil.offsiteMinute(new Date(), -10));
        return orderInfoMapper.getNotPayOrder(map);
    }

    /**
     * 将订单更新为成功
     *
     * @param orderInfo
     */
    @Override
    public void orderSuccess(OrderInfo orderInfo) {
        if (orderInfo == null) {
            return;
        }
        orderInfo.setStatus(OrderStatusEnum.SUCCESS.getCode());
        orderInfo.setSucTime(new Date());
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
    }

    /**
     * 将订单更新为支付处理中
     *
     * @param orderInfo
     */
    @Override
    public void orderPaying(OrderInfo orderInfo) {
        if (orderInfo == null) {
            return;
        }
        orderInfo.setStatus(OrderStatusEnum.PAYING.getCode());
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
    }

    /**
     * 将订单更新为失败
     *
     * @param orderInfo
     */
    @Override
    public void orderFailed(OrderInfo orderInfo) {
        if (orderInfo == null) {
            return;
        }
        orderInfo.setStatus(OrderStatusEnum.FAILED.getCode());
        orderInfo.setSucTime(new Date());
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
    }

    @Override
    public List<OrderInfo> queryPage(Map<String, Object> paramMap) {
        return orderInfoMapper.queryPage(paramMap);
    }

    @Override
    public void saveOrUpdate(OrderInfo orderInfo) {
        if (orderInfo.getId() == null) {
            this.save(orderInfo);
        } else {
            this.update(orderInfo);
        }
    }
}
