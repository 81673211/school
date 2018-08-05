package com.school.service.order.impl;

import com.school.constant.ConstantMap;
import com.school.constant.Constants;
import com.school.dao.express.ExpressReceiveMapper;
import com.school.dao.express.ExpressSendMapper;
import com.school.dao.order.OrderInfoMapper;
import com.school.domain.entity.express.Express;
import com.school.domain.entity.express.ExpressReceive;
import com.school.domain.entity.express.ExpressSend;
import com.school.domain.entity.order.OrderInfo;
import com.school.enumeration.ExpressTypeEnum;
import com.school.enumeration.OrderStatusEnum;
import com.school.exception.OrderException;
import com.school.service.base.impl.BaseServiceImpl;
import com.school.service.calc.CalcCostService;
import com.school.service.order.OrderInfoService;
import com.school.util.core.utils.DateUtil;
import com.school.util.core.utils.RandomUtil;
import com.school.vo.request.OrderCreateVo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = OrderException.class)
@Slf4j
public class OrderInfoServiceImpl extends BaseServiceImpl<OrderInfo, OrderInfoMapper>
        implements OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private ExpressSendMapper expressSendMapper;
    @Autowired
    private ExpressReceiveMapper expressReceiveMapper;
    @Autowired
    private CalcCostService calcCostService;

    @Override
    public OrderInfo findByOrderNo(String orderNo) {
        return orderInfoMapper.findByOrderNo(orderNo);
    }

    @Override
    public String createSendOrder(OrderCreateVo vo) throws OrderException {
        try {
            ExpressSend expressSend = expressSendMapper.selectByPrimaryKey(vo.getExpressId());
            OrderInfo orderInfo = initOrderInfo(expressSend);
            if (!(orderInfoMapper.insertSelective(orderInfo) > 0)) {
                String message =
                        "create send order error,when insert table 'order_info' the number of affected rows is 0";
                log.error(message);
                throw new OrderException(message);
            } else {
                return orderInfo.getOrderNo();
            }
        } catch (Exception e) {
            String message = "throw exception when create send order";
            log.error(message, e);
            throw new OrderException(message, e);
        }
    }

    @Override
    public String createReceiveOrder(OrderCreateVo vo) throws OrderException {
        try {
            Long expressId = vo.getExpressId();
            OrderInfo orderInfo = findByExpressReceiveId(expressId);
            if (orderInfo == null || canRecreate(orderInfo)) {
                ExpressReceive expressReceive = expressReceiveMapper.selectByPrimaryKey(expressId);
                orderInfo = initOrderInfo(expressReceive);
                int result = orderInfoMapper.insertSelective(orderInfo);
                if (result <= 0) {
                    String message =
                            "create receive order error,when insert table 'order_info' the number of affected rows is 0";
                    log.error(message);
                    throw new OrderException(message);
                }
            }
            return orderInfo.getOrderNo();
        } catch (Exception e) {
            String message = "throw exception when create receive order";
            log.error(message, e);
            throw new OrderException(message, e);
        }
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

    private OrderInfo findByExpressIdAndType(Long expressId, int expressType) {
        return orderInfoMapper.findByExpressIdAndType(expressId, expressType);
    }

    /**
     * 初始化订单对象
     *
     * @param express
     * @return
     */
    private OrderInfo initOrderInfo(Express express) throws Exception {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setTradeSummary("express");
        if (express instanceof ExpressSend) {
            ExpressSend expressSend = (ExpressSend) express;
            orderInfo.setExpressType(ExpressTypeEnum.SEND.getFlag());
            orderInfo.setAmount(calcCostService.calcSendDistributionCost(expressSend));
        } else if (express instanceof ExpressReceive) {
            ExpressReceive expressReceive = (ExpressReceive) express;
            orderInfo.setExpressType(ExpressTypeEnum.RECEIVE.getFlag());
            orderInfo.setAmount(calcCostService.calcReceiveDistributionCost(expressReceive));
        } else {
            String errorMsg = "error express type.";
            log.error(errorMsg);
            throw new OrderException(errorMsg);
        }
        orderInfo.setExpressId(express.getId());
        orderInfo.setExpressCode(express.getCode());
        orderInfo.setCustomerId(express.getCustomerId());
        orderInfo.setStatus(OrderStatusEnum.UNPAY.getCode());
        orderInfo.setOrderNo(RandomUtil.GenerateOrderNo(Constants.idWorker, ConstantMap.ORDER_NO_TYPE_ORDER));
        orderInfo.setNotifyUrl(Constants.WXPAY_NOTIFY_URL);
        return orderInfo;
    }

	@Override
	public List<OrderInfo> getNotPayOrder() {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("tenMinutesAgo", DateUtil.offsiteMinute(new Date(), -10));
		return orderInfoMapper.getNotPayOrder(map);
	}
	
	/**
     * 将订单更新为成功
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

}
