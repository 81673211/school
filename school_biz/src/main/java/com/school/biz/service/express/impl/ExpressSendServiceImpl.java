package com.school.biz.service.express.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.biz.dao.customer.CustomerMapper;
import com.school.biz.dao.express.ExpressCompanyMapper;
import com.school.biz.dao.express.ExpressSendMapper;
import com.school.biz.dao.region.RegionMapper;
import com.school.biz.domain.entity.express.ExpressCompany;
import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.domain.entity.region.Region;
import com.school.biz.enumeration.ExpressTypeEnum;
import com.school.biz.exception.ExpressException;
import com.school.biz.service.base.impl.BaseServiceImpl;
import com.school.biz.service.express.ExpressSendService;
import com.school.biz.service.order.OrderInfoService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jame
 */
@Slf4j
@Service
@Transactional(rollbackFor = ExpressException.class)
public class ExpressSendServiceImpl extends BaseServiceImpl<ExpressSend, ExpressSendMapper>
        implements ExpressSendService {

    @Autowired
    private ExpressSendMapper expressSendMapper;
    @Autowired
    private ExpressCompanyMapper expressCompanyMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private OrderInfoService orderInfoService;

    @Override
    public String createSendExpress(ExpressSend expressSend) {
        boxExpressCompany(expressSend);
        Long count = expressSendMapper.insertSelective(expressSend);
        if (!(count > 0L)) {
            String message = "create send express error,when insert table 'express_send' the number of affected rows is 0";
            log.error(message);
            throw new RuntimeException(message);
        }
        return orderInfoService.createSendOrder(expressSend.getId());
    }

    private void boxCustomer(ExpressSend expressSend, String openId) {

    }

    @Override
    public void modifySendExpress(ExpressSend expressSend) {
        try {
            boxExpressCompany(expressSend);
            if (!(expressSendMapper.updateByPrimaryKeySelective(expressSend) > 0)) {
                String message = "modify send express error,when update table 'express_send' the number of affected rows is 0";
                log.error(message);
                throw new ExpressException(message);
            }
//            // 修改完成后检查省市区快递公司等是否发生改变，改变了则重新创建订单，待修改；以及状态检查；修改不再创建订单
//            ExpressSend hasSendExpress = expressSendMapper.selectByPrimaryKey(expressSend.getId());
//            if (!hasSendExpress.getCompanyId().equals(expressSend.getCompanyId()) ||
//                    !hasSendExpress.getReceiverProvinceId().equals(expressSend.getReceiverProvinceId()) ||
//                    !hasSendExpress.getReceiverCityId().equals(expressSend.getReceiverCityId()) ||
//                    !hasSendExpress.getReceiverDistrictId().equals(expressSend.getReceiverDistrictId())) {
//                if (!(orderInfoMapper.insertSelective(initOrderInfo(expressSend.getId(),expressSend.getCustomerId())) > 0)) {
//                    String message = "create send express error,when insert table 'order_info' the number of affected rows is 0";
//                    Log.error.error(message);
//                    throw new ExpressException(message);
//                }
//            }
        } catch (Exception e) {
            String message = "throw exception when modify send express";
            log.error(message, e);
            throw new RuntimeException(message, e);
        }
    }

    @Override
    public ExpressSend getSendExpress(Long id) {
        ExpressSend expressSend = expressSendMapper.selectByPrimaryKey(id);
        if (expressSend == null) {
            String message = "get send express error,when select table 'express_send' the number of affected rows is 0,id=" + id;
            log.error(message);
            throw new RuntimeException(message);
        }
        return expressSend;
    }

    @Override
    public void updateSendExpressStatus(Long id, Integer status) {
        //TODO 快件状态检查，检查状态是否遵循定义的流程，如果按照非流程处理则抛出异常,比如：修改快件状态时，需要去提前检查快件当前状态是否满足修改的条件
        ExpressSend expressSend = new ExpressSend();
        expressSend.setId(id);
        expressSend.setExpressStatus(status);
        int count = expressSendMapper.updateByPrimaryKeySelective(expressSend);
        if (!(count > 0)) {
            String msg = "update send express status failed,when update table 'express_send' the number of affected rows is 0";
            log.error(msg);
            throw new RuntimeException(msg);
        }
    }


    @Override
    public List<ExpressSend> selectExpressList(Integer[] status, String openid) {
        Map<String, Object> param = new HashMap<>();
        param.put("status", status);
        param.put("phone", customerMapper.selectByOpenId(openid).getPhone());
        List<ExpressSend> receiveList = expressSendMapper.selectByParams(param);
        if (!receiveList.isEmpty()) {
            for (ExpressSend expressSend : receiveList) {
                initProvinceCityDistrict(expressSend);
            }
        }
        return receiveList;
    }


    @Override
    public ExpressSend initProvinceCityDistrict(ExpressSend expressSend) {
        Region province = (Region) regionMapper.selectByPrimaryKey(expressSend.getReceiverProvinceId());
        Region city = (Region) regionMapper.selectByPrimaryKey(expressSend.getReceiverCityId());
        Region district = (Region) regionMapper.selectByPrimaryKey(expressSend.getReceiverDistrictId());
        expressSend.setReceiverProvince(province.getAreaName());
        expressSend.setReceiverCity(city.getAreaName());
        expressSend.setReceiverDistrict(district.getAreaName());
        return expressSend;
    }

    /**
     * 封装快件对应的物流公司
     *
     * @param expressSend
     */
    private void boxExpressCompany(ExpressSend expressSend) {
        ExpressCompany expressCompany = expressCompanyMapper.selectByPrimaryKey(expressSend.getCompanyId());
        expressSend.setCompanyCode(expressCompany.getCode());
        expressSend.setCompanyName(expressCompany.getName());
    }

    @Override
    public BigDecimal getOrderPrice(Long expressId, ExpressTypeEnum expressType) {
        OrderInfo orderInfo;
        if (ExpressTypeEnum.RECEIVE.getFlag() == expressType.getFlag()) {
            orderInfo = orderInfoService.findByExpressReceiveId(expressId);
        } else {
            orderInfo = orderInfoService.findByExpressSendId(expressId);
        }
        return orderInfo.getAmount();
    }

    @Override
    public List<ExpressSend> queryPage(Map<String, Object> paramMap) {
        return expressSendMapper.queryForManagerPage(paramMap);
    }

    @Override
    public void saveOrUpdate(ExpressSend expressSend) {
        if(expressSend.getId() == null){
            this.save(expressSend);
        }else{
            this.update(expressSend);
        }
    }
}
