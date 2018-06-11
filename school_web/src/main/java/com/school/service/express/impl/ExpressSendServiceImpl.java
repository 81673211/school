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
@Transactional(rollbackFor = ExpressException.class)
public class ExpressSendServiceImpl extends BaseServiceImpl<ExpressSend, ExpressSendMapper> implements ExpressSendService {

    @Autowired
    private ExpressSendMapper expressSendMapper;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private ExpressCompanyMapper expressCompanyMapper;

    @Override
    public void createSendExpress(CreateSendExpressVo expressVo) throws ExpressException {
        try {
            ExpressSend expressSend = converterVo2Po(expressVo, ExpressSend.class);
            boxExpressCompany(expressSend);
            Long expressId = expressSendMapper.insertSelective(expressSend);
            if (!(expressId > 0L)) {
                Log.error.error("create send express error,when insert table 'express_send' the number of affected rows is 0");
                throw new ExpressException("create send express error,when insert table 'express_send' the number of affected rows is 0");
            }
            if (!(orderInfoMapper.insertSelective(initOrderInfo(expressId)) > 0)) {
                Log.error.error("create send express error,when insert table 'order_info' the number of affected rows is 0");
                throw new ExpressException("create send express error,when insert table 'order_info' the number of affected rows is 0");
            }
        } catch (Exception e) {
            Log.error.error("throw exception when create send express", e);
            throw new ExpressException("throw exception when create send express", e);
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
