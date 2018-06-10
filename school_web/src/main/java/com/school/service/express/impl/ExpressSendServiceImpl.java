package com.school.service.express.impl;

import com.school.common.ExpressTypeEnum;
import com.school.dao.express.ExpressSendMapper;
import com.school.dao.order.OrderInfoMapper;
import com.school.domain.entity.express.ExpressSend;
import com.school.domain.entity.order.OrderInfo;
import com.school.enumeration.OrderStatusEnum;
import com.school.exception.ExpressException;
import com.school.service.base.impl.BaseServiceImpl;
import com.school.service.express.ExpressSendService;
import com.school.util.core.Log;
import com.school.vo.request.CreateSendExpressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author jame
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpressSendServiceImpl extends BaseServiceImpl<ExpressSend, ExpressSendMapper> implements ExpressSendService {

    @Autowired
    private ExpressSendMapper expressSendMapper;
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public void createSendExpress(CreateSendExpressVo expressVo) throws ExpressException {
        try {
            Long id = expressSendMapper.insertSelective(converterVo2Po(expressVo, ExpressSend.class));
            if (!(id > 0L)) {
                Log.error.error("create send express error,when insert table 'express_send' the number of affected rows is 0");
                throw new ExpressException("create send express error,when insert table 'express_send' the number of affected rows is 0");
            }
            if (!(orderInfoMapper.insertSelective(initOrderInfo(expressVo, id)) > 0)) {
                Log.error.error("create send express error,when insert table 'order_info' the number of affected rows is 0");
                throw new ExpressException("create send express error,when insert table 'order_info' the number of affected rows is 0");
            }
        } catch (Exception e) {
            Log.error.error("throw exception when create send express", e);
            throw new ExpressException("throw exception when create send express", e);
        }
    }

    private OrderInfo initOrderInfo(CreateSendExpressVo expressVo, Long id) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setExpressId(id);
        orderInfo.setExpressType(ExpressTypeEnum.SEND.getFlag());
        orderInfo.setStatus(OrderStatusEnum.UNPAID.getCode());
        orderInfo.setAmount(BigDecimal.valueOf(expressVo.getAmount()));
        orderInfo.setOrderNo(UUID.randomUUID().toString());
        return orderInfo;
    }

}
