package com.school.service.order.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.school.exception.OrderException;
import com.school.service.base.impl.BaseServiceImpl;
import com.school.service.order.OrderInfoService;
import com.school.util.core.Log;
import com.school.util.core.utils.RandomUtil;
import com.school.vo.request.OrderCreateVo;

@Service
@Transactional(rollbackFor = OrderException.class)
public class OrderInfoServiceImpl extends BaseServiceImpl<OrderInfo, OrderInfoMapper> implements OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private ExpressSendMapper expressSendMapper;
    @Autowired
    private ExpressReceiveMapper expressReceiveMapper;

    @Override
    public OrderInfo findByOrderNo(String orderNo) {
        return orderInfoMapper.findByOrderNo(orderNo);
    }


    @Override
    public void createSendOrder(OrderCreateVo vo) throws OrderException {
        try {
            ExpressSend expressSend = expressSendMapper.selectByPrimaryKey(vo.getExpressId());
            if (!(orderInfoMapper.insertSelective(initOrderInfo(expressSend)) > 0)) {
                String message = "create send order error,when insert table 'order_info' the number of affected rows is 0";
                Log.error.error(message);
                throw new OrderException(message);
            }
        } catch (Exception e) {
            String message = "throw exception when create send order";
            Log.error.error(message, e);
            throw new OrderException(message, e);
        }
    }

    @Override
    public void createReceiveOrder(OrderCreateVo vo) throws OrderException {
        try {
            ExpressReceive expressReceive = expressReceiveMapper.selectByPrimaryKey(vo.getExpressId());
            if (!(orderInfoMapper.insertSelective(initOrderInfo(expressReceive)) > 0)) {
                String message = "create receive order error,when insert table 'order_info' the number of affected rows is 0";
                Log.error.error(message);
                throw new OrderException(message);
            }
        } catch (Exception e) {
            String message = "throw exception when create receive order";
            Log.error.error(message, e);
            throw new OrderException(message, e);
        }
    }

    /**
     * 初始化订单对象
     *
     * @param express
     * @return
     */
    private OrderInfo initOrderInfo(Express express) throws OrderException {
        OrderInfo orderInfo = new OrderInfo();
        if (express instanceof ExpressSend) {
            ExpressSend expressSend = (ExpressSend) express;
            orderInfo.setExpressType(ExpressTypeEnum.SEND.getFlag());
            orderInfo.setExpressId(expressSend.getId());
            orderInfo.setExpressType(ExpressTypeEnum.SEND.getFlag());
        } else if (express instanceof ExpressReceive) {
            ExpressReceive expressReceive = (ExpressReceive) express;
            orderInfo.setExpressType(ExpressTypeEnum.RECEIVE.getFlag());
            orderInfo.setExpressId(expressReceive.getId());
            orderInfo.setExpressType(ExpressTypeEnum.RECEIVE.getFlag());
        } else {
            String errorMsg = "error express type.";
            Log.error.error(errorMsg);
            throw new OrderException(errorMsg);
        }
        orderInfo.setCustomerId(express.getCustomerId());
        orderInfo.setStatus(0);
        orderInfo.setAmount(calcSendExpressAmount());
        orderInfo.setOrderNo(RandomUtil.GenerateOrderNo(Constants.idWorker,ConstantMap.ORDER_NO_TYPE_ORDER));
        orderInfo.setNotifyUrl(Constants.WXPAY_NOTIFY_URL);
        return orderInfo;
    }
    
    /**
     * todo 寄件金额计算
     *
     * @return
     */
    private BigDecimal calcSendExpressAmount() {
        return BigDecimal.ZERO;
    }

}
