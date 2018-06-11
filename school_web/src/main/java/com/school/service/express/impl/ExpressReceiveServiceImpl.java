package com.school.service.express.impl;

import com.school.common.ExpressTypeEnum;
import com.school.dao.express.ExpressCompanyMapper;
import com.school.dao.express.ExpressReceiveMapper;
import com.school.dao.order.OrderInfoMapper;
import com.school.domain.entity.express.Express;
import com.school.domain.entity.express.ExpressCompany;
import com.school.domain.entity.express.ExpressReceive;
import com.school.domain.entity.order.OrderInfo;
import com.school.enumeration.OrderStatusEnum;
import com.school.exception.ExpressException;
import com.school.service.base.impl.BaseServiceImpl;
import com.school.service.express.ExpressReceiveService;
import com.school.util.core.Log;
import com.school.vo.request.CreateReceiveExpressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author jame
 */
@Service
public class ExpressReceiveServiceImpl extends BaseServiceImpl<ExpressReceive, ExpressReceiveMapper> implements ExpressReceiveService {

    @Autowired
    private ExpressCompanyMapper expressCompanyMapper;
    @Autowired
    private ExpressReceiveMapper expressReceiveMapper;
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public void createReceiveExpress(CreateReceiveExpressVo expressVo) throws ExpressException {
        try {
            ExpressReceive expressReceive = converterVo2Po(expressVo, ExpressReceive.class);
            boxExpressCompany(expressReceive);
            Long expressId = expressReceiveMapper.insertSelective(expressReceive);
            if (!(expressId > 0L)) {
                Log.error.error("create receive express error,when insert table 'express_receive' the number of affected rows is 0");
                throw new ExpressException("create receive express error,when insert table 'express_receive' the number of affected rows is 0");
            }
            if (!(orderInfoMapper.insertSelective(initOrderInfo(expressId)) > 0)) {
                Log.error.error("create receive express error,when insert table 'order_info' the number of affected rows is 0");
                throw new ExpressException("create receive express error,when insert table 'order_info' the number of affected rows is 0");
            }
        } catch (Exception e) {
            Log.error.error("throw exception when create receive express", e);
            throw new ExpressException("throw exception when create receive express", e);
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
     * todo 收件金额计算
     *
     * @return
     */
    public BigDecimal calcSendExpressAmount() {
        return BigDecimal.ZERO;
    }

}
