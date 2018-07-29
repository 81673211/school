package com.school.service.express.impl;

import com.school.dao.customer.CustomerMapper;
import com.school.dao.express.ExpressCompanyMapper;
import com.school.dao.express.ExpressSendMapper;
import com.school.dao.order.OrderInfoMapper;
import com.school.dao.region.RegionMapper;
import com.school.domain.entity.customer.Customer;
import com.school.domain.entity.express.ExpressCompany;
import com.school.domain.entity.express.ExpressSend;
import com.school.domain.entity.order.OrderInfo;
import com.school.domain.entity.region.Region;
import com.school.enumeration.ExpressTypeEnum;
import com.school.exception.ExpressException;
import com.school.exception.ExpressStatusException;
import com.school.service.base.impl.BaseServiceImpl;
import com.school.service.express.ExpressSendService;
import com.school.service.order.OrderInfoService;
import com.school.util.core.Log;
import com.school.vo.BaseVo;
import com.school.vo.request.OrderCreateVo;
import com.school.vo.request.SendExpressCreateVo;
import com.school.vo.request.SendExpressModifyVo;
import com.school.vo.response.SendExpressListResponseVo;
import com.school.vo.response.SendExpressResponseVo;
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
public class ExpressSendServiceImpl extends BaseServiceImpl<ExpressSend, ExpressSendMapper> implements ExpressSendService {

    @Autowired
    private ExpressSendMapper expressSendMapper;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private ExpressCompanyMapper expressCompanyMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private OrderInfoService orderInfoService;

    @Override
    public void createSendExpress(SendExpressCreateVo expressVo) throws ExpressException {
        try {
            ExpressSend expressSend = converterVo2Po(expressVo, ExpressSend.class);
            boxExpressCompany(expressSend);
            boxCustomer(expressSend, expressVo.getOpenId());
            Long count = expressSendMapper.insertSelective(expressSend);
            if (!(count > 0L)) {
                String message = "create send express error,when insert table 'express_send' the number of affected rows is 0";
                Log.error.error(message);
                throw new ExpressException(message);
            }
            OrderCreateVo vo = new OrderCreateVo();
            vo.setExpressId(expressSend.getId());
            orderInfoService.createSendOrder(vo);
        } catch (Exception e) {
            String message = "throw exception when create send express";
            Log.error.error(message, e);
            throw new ExpressException(message, e);
        }
    }

    private void boxCustomer(ExpressSend expressSend, String openId) {
        Customer customer = customerMapper.selectByOpenId(openId);
        expressSend.setCustomerId(customer.getId());
        expressSend.setSenderPhone(customer.getPhone());
        expressSend.setSenderName(customer.getName());
    }

    @Override
    public void modifySendExpress(SendExpressModifyVo expressVo) throws ExpressException {
        try {
            ExpressSend expressSend = converterVo2Po(expressVo, ExpressSend.class);
            boxExpressCompany(expressSend);
            if (!(expressSendMapper.updateByPrimaryKeySelective(expressSend) > 0)) {
                String message = "modify send express error,when update table 'express_send' the number of affected rows is 0";
                Log.error.error(message);
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

    @Override
    public void updateSendExpressStatus(Long id, Integer status) throws ExpressException {
        try {
            expressStatusCheck(id, ExpressTypeEnum.SEND.getFlag(), true, status);
        } catch (ExpressStatusException e) {
            String msg = "update send express status error,express status check failed";
            Log.error.error(msg, e);
            throw new ExpressException(msg, e);
        }
        ExpressSend expressSend = new ExpressSend();
        expressSend.setId(id);
        expressSend.setExpressStatus(status);
        int count = expressSendMapper.updateByPrimaryKeySelective(expressSend);
        if (!(count > 0)) {
            String msg = "update send express status failed,when update table 'express_send' the number of affected rows is 0";
            Log.error.error(msg);
            throw new ExpressException(msg);
        }
    }


    @Override
    public List<BaseVo> selectExpressList(Integer[] status, String openid) throws ExpressException {
        List<BaseVo> list = new ArrayList<>();
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("status", status);
            param.put("phone", customerMapper.selectByOpenId(openid).getPhone());
            List<ExpressSend> receiveList = expressSendMapper.selectByParams(param);
            if (!receiveList.isEmpty()) {
                for (ExpressSend expressSend : receiveList) {
                    initProvinceCityDistrict(expressSend);
                    list.add(converterPo2Vo(expressSend, new SendExpressListResponseVo()));
                }
            }
        } catch (Exception e) {
            String msg = "throw exception when get receive express list";
            Log.error.error(msg, e);
            throw new ExpressException(e);
        }
        return list;
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


}
