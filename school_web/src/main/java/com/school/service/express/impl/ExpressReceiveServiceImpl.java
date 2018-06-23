package com.school.service.express.impl;

import com.school.dao.express.ExpressCompanyMapper;
import com.school.dao.express.ExpressReceiveMapper;
import com.school.dao.order.OrderInfoMapper;
import com.school.domain.entity.express.Express;
import com.school.domain.entity.express.ExpressCompany;
import com.school.domain.entity.express.ExpressReceive;
import com.school.domain.entity.order.OrderInfo;
import com.school.enumeration.ExpressTypeEnum;
import com.school.enumeration.OrderStatusEnum;
import com.school.exception.ExpressException;
import com.school.exception.ExpressStatusException;
import com.school.service.base.impl.BaseServiceImpl;
import com.school.service.express.ExpressReceiveService;
import com.school.util.core.Log;
import com.school.vo.BaseVo;
import com.school.vo.request.ReceiveExpressVo;
import com.school.vo.response.ReceiveExpressListResponseVo;
import com.school.vo.response.ReceiveExpressResponseVo;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jame
 */
@Service
@Transactional(rollbackFor = ExpressException.class)
public class ExpressReceiveServiceImpl extends BaseServiceImpl<ExpressReceive, ExpressReceiveMapper> implements ExpressReceiveService {

    @Autowired
    private ExpressCompanyMapper expressCompanyMapper;
    @Autowired
    private ExpressReceiveMapper expressReceiveMapper;
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public void createReceiveExpress(ReceiveExpressVo expressVo) throws ExpressException {
        try {
            ExpressReceive expressReceive = converterVo2Po(expressVo, ExpressReceive.class);
            boxExpressCompany(expressReceive);
            Long expressId = expressReceiveMapper.insertSelective(expressReceive);
            if (!(expressId > 0L)) {
                Log.error.error("create receive express error,when insert table 'express_receive' the number of affected rows is 0");
                throw new ExpressException("create receive express error,when insert table 'express_receive' the number of affected rows is 0");
            }
            //创建收件无需新增订单，放到修改后选择入柜方式才生成订单
//            if (!(orderInfoMapper.insertSelective(initOrderInfo(expressId)) > 0)) {
//                Log.error.error("create receive express error,when insert table 'order_info' the number of affected rows is 0");
//                throw new ExpressException("create receive express error,when insert table 'order_info' the number of affected rows is 0");
//            }
        } catch (Exception e) {
            Log.error.error("throw exception when create receive express", e);
            throw new ExpressException("throw exception when create receive express", e);
        }
    }

    @Override
    public void modifyReceiveExpress(ReceiveExpressVo expressVo) throws ExpressException {
        try {
            ExpressReceive expressReceive = converterVo2Po(expressVo, ExpressReceive.class);
            //0自提；1入柜，null表示还未选择过
            Integer expressWay = expressReceiveMapper.selectByPrimaryKey(expressReceive.getId()).getExpressWay();
            if (!(expressReceiveMapper.updateByPrimaryKeySelective(expressReceive) > 0)) {
                Log.error.error("modify receive express error,when update table 'express_receive' the number of affected rows is 0");
                throw new ExpressException("modify receive express error,when update table 'express_receive' the number of affected rows is 0");
            }
            if (expressWay == null) {
                //修改后选择入柜方式才生成订单
                if (!(orderInfoMapper.insertSelective(initOrderInfo(expressReceive.getId())) > 0)) {
                    Log.error.error("create receive express error,when insert table 'order_info' the number of affected rows is 0");
                    throw new ExpressException("create receive express error,when insert table 'order_info' the number of affected rows is 0");
                }
            }

        } catch (Exception e) {
            Log.error.error("throw exception when modify receive express", e);
            throw new ExpressException("throw exception when modify receive express", e);
        }
    }


    @Override
    public BaseVo getReceiveExpress(Long id) throws ExpressException {
        try {
            ExpressReceive expressReceive = expressReceiveMapper.selectByPrimaryKey(id);
            if (expressReceive == null) {
                String message = "get receive express error,when select table 'express_receive' the number of affected rows is 0,id=" + id;
                Log.error.error(message);
                throw new ExpressException(message);
            }
            return converterPo2Vo(expressReceive, new ReceiveExpressResponseVo());
        } catch (Exception e) {
            String message = "throw exception when get receive express";
            Log.error.error(message, e);
            throw new ExpressException(message, e);
        }
    }


    @Override
    public void updateReceiveExpressStatus(Long id, Integer status) throws ExpressException {
        try {
            expressStatusCheck(id, ExpressTypeEnum.RECEIVE.getFlag(), true, status);
        } catch (ExpressStatusException e) {
            String msg = "update receive express status error,express status check failed";
            Log.error.error(msg, e);
            throw new ExpressException(msg, e);
        }
        ExpressReceive expressReceive = new ExpressReceive();
        expressReceive.setId(id);
        expressReceive.setExpressStatus(status);
        int count = expressReceiveMapper.updateByPrimaryKeySelective(expressReceive);
        if (!(count > 0)) {
            String msg = "update receive express status failed,when update table 'express_receive' the number of affected rows is 0";
            Log.error.error(msg);
            throw new ExpressException(msg);
        }
    }


    @Override
    public List<BaseVo> selectExpressList(Integer[] status, String phone) throws ExpressException {
        List<BaseVo> list = new ArrayList<>();
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("status", status);
            param.put("phone", phone);
            List<ExpressReceive> receiveList = expressReceiveMapper.selectByParams(param);
            if (!receiveList.isEmpty()) {
                for (ExpressReceive expressReceive : receiveList) {
                    BaseVo baseVo = converterPo2Vo(expressReceive, new ReceiveExpressListResponseVo());
                    list.add(baseVo);
                }
            }
        } catch (Exception e) {
            String msg = "throw exception when get receive express list";
            Log.error.error(msg, e);
            throw new ExpressException(e);
        }
        return list;
    }

    /**
     * 初始化订单对象
     *
     * @param expressId
     * @return
     */
    private OrderInfo initOrderInfo(Long expressId) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setExpressId(expressId);
        orderInfo.setExpressType(ExpressTypeEnum.SEND.getFlag());
        orderInfo.setStatus(OrderStatusEnum.UNPAID.getCode());
        orderInfo.setAmount(calcSendExpressAmount());
        orderInfo.setOrderNo(RandomStringUtils.randomAlphanumeric(20));
        return orderInfo;
    }

    /**
     * 封装快件对应的物流公司
     *
     * @param expressSend
     */
    private void boxExpressCompany(Express expressSend) throws ExpressException {
        Map<String, Object> param = new HashMap<>();
        if (expressSend.getCompanyCode() != null) {
            param.put("companyCode", expressSend.getCompanyCode());
        } else if (expressSend.getCompanyName() != null) {
            param.put("companyName", expressSend.getCompanyName());
        }
        List<ExpressCompany> list = expressCompanyMapper.selectByParams(param);
        if (list.isEmpty()) {
            String errorMsg = "未找到对应的快递公司，快递公司编号:" + expressSend.getCompanyCode() + "，快递公司名称:" + expressSend.getCompanyName();
            Log.error.error(errorMsg);
            throw new ExpressException(errorMsg);
        }
        ExpressCompany expressCompany = list.get(0);
        expressSend.setCompanyId(expressCompany.getId());
        expressSend.setCompanyCode(expressCompany.getCode());
        expressSend.setCompanyName(expressCompany.getName());
    }

    /**
     * todo 收件金额计算
     *
     * @return
     */
    private BigDecimal calcSendExpressAmount() {
        return BigDecimal.ZERO;
    }

}
