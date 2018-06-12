package com.school.service.express.impl;

import com.school.common.ExpressTypeEnum;
import com.school.dao.express.ExpressCompanyMapper;
import com.school.dao.express.ExpressSendMapper;
import com.school.dao.order.OrderInfoMapper;
import com.school.domain.entity.express.Express;
import com.school.domain.entity.express.ExpressCompany;
import com.school.domain.entity.express.ExpressSend;
import com.school.domain.entity.order.OrderInfo;
import com.school.enumeration.OrderStatusEnum;
import com.school.exception.ExpressException;
import com.school.service.base.impl.BaseServiceImpl;
import com.school.service.express.ExpressSendService;
import com.school.util.core.Log;
import com.school.vo.BaseVo;
import com.school.vo.request.SendExpressVo;
import com.school.vo.response.SendExpressResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author jame
 */
@Service
@Transactional(rollbackFor = ExpressException.class)
public class ExpressSendServiceImpl extends BaseServiceImpl<ExpressSend, ExpressSendMapper> implements ExpressSendService {

    @Autowired
    private ExpressSendMapper expressSendMapper;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private ExpressCompanyMapper expressCompanyMapper;

    @Override
    public void createSendExpress(SendExpressVo expressVo) throws ExpressException {
        try {
            ExpressSend expressSend = converterVo2Po(expressVo, ExpressSend.class);
            boxExpressCompany(expressSend);
            Long expressId = expressSendMapper.insertSelective(expressSend);
            if (!(expressId > 0L)) {
                String message = "create send express error,when insert table 'express_send' the number of affected rows is 0";
                Log.error.error(message);
                throw new ExpressException(message);
            }
            if (!(orderInfoMapper.insertSelective(initOrderInfo(expressId)) > 0)) {
                String message = "create send express error,when insert table 'order_info' the number of affected rows is 0";
                Log.error.error(message);
                throw new ExpressException(message);
            }
        } catch (Exception e) {
            String message = "throw exception when create send express";
            Log.error.error(message, e);
            throw new ExpressException(message, e);
        }
    }

    @Override
    public void modifySendExpress(SendExpressVo expressVo) throws ExpressException {
        try {
            ExpressSend expressSend = converterVo2Po(expressVo, ExpressSend.class);
            if (!(expressSendMapper.updateByPrimaryKeySelective(expressSend) > 0)) {
                String message = "modify send express error,when update table 'express_send' the number of affected rows is 0";
                Log.error.error(message);
                throw new ExpressException(message);
            }
        } catch (Exception e) {
            String message = "throw exception when modify send express";
            Log.error.error(message, e);
            throw new ExpressException(message, e);
        }
    }

    @Override
    public BaseVo getSendExpress(Long id) throws ExpressException {
        try {
            ExpressSend expressSend = expressSendMapper.selectByPrimaryKey(id);
            if (expressSend == null) {
                String message = "get send express error,when select table 'express_send' the number of affected rows is 0,id=" + id;
                Log.error.error(message);
                throw new ExpressException(message);
            }
            return converterPo2Vo(expressSend, new SendExpressResponseVo());
        } catch (Exception e) {
            String message = "throw exception when get send express";
            Log.error.error(message, e);
            throw new ExpressException(message, e);
        }
    }

    /**
     * 初始化订单对象
     *
     * @param expressId
     * @return
     */
    public OrderInfo initOrderInfo(Long expressId) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setExpressId(expressId);
        orderInfo.setExpressType(ExpressTypeEnum.SEND.getFlag());
        orderInfo.setStatus(OrderStatusEnum.UNPAID.getCode());
        orderInfo.setAmount(calcSendExpressAmount());
        orderInfo.setOrderNo(UUID.randomUUID().toString());
        return orderInfo;
    }

    /**
     * 封装快件对应的物流公司
     *
     * @param expressSend
     */
    public void boxExpressCompany(Express expressSend) {
        ExpressCompany expressCompany = expressCompanyMapper.selectByPrimaryKey(expressSend.getCompanyId());
        expressSend.setCompanyCode(expressCompany.getCode());
        expressSend.setCompanyName(expressCompany.getName());
    }

    /**
     * todo 寄件金额计算
     *
     * @return
     */
    public BigDecimal calcSendExpressAmount() {
        return BigDecimal.ZERO;
    }


}
