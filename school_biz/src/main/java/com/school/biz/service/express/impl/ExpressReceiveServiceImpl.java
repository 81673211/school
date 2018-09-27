package com.school.biz.service.express.impl;

import com.school.biz.dao.express.ExpressCompanyMapper;
import com.school.biz.dao.express.ExpressReceiveMapper;
import com.school.biz.dao.region.RegionMapper;
import com.school.biz.domain.entity.express.Express;
import com.school.biz.domain.entity.express.ExpressCompany;
import com.school.biz.domain.entity.express.ExpressReceive;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.domain.entity.region.Region;
import com.school.biz.domain.entity.user.AdminUser;
import com.school.biz.domain.vo.PushMessageVo;
import com.school.biz.enumeration.*;
import com.school.biz.exception.ExpressException;
import com.school.biz.service.base.impl.BaseServiceImpl;
import com.school.biz.service.calc.CalcCostService;
import com.school.biz.service.express.ExpressReceiveService;
import com.school.biz.service.log.ExpressLogService;
import com.school.biz.service.order.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jame
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ExpressReceiveServiceImpl extends BaseServiceImpl<ExpressReceive, ExpressReceiveMapper>
        implements ExpressReceiveService {

    @Autowired
    private ExpressCompanyMapper expressCompanyMapper;
    @Autowired
    private ExpressReceiveMapper expressReceiveMapper;
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private CalcCostService calcCostService;
    @Autowired
    private ExpressLogService expressLogService;

    @Override
    public String createHelpReceiveExpress(ExpressReceive expressReceive) {
        try {
            createReceiveExpress(expressReceive);
            return orderInfoService.createReceiveOrder(expressReceive.getId(), expressReceive.getExpressWay());
        } catch (Exception e) {
            String message = "throw exception when create help receive express";
            log.error(message, e);
            throw new RuntimeException(message, e);
        }
    }

    @Override
    public void createReceiveExpress(ExpressReceive expressReceive) {
        try {
            String code = expressReceive.getCode();
            List list = new ArrayList();
            if (StringUtils.isNotBlank(code)) {
                Map<String, Object> codeMap = new HashMap<>(1);
                codeMap.put("code", code);
                list = expressReceiveMapper.selectByParams(codeMap);
            }
            if (!CollectionUtils.isEmpty(list)) {
                ExpressReceive receive = (ExpressReceive) list.get(0);
                if (receive.getExpressStatus() != (ReceiveExpressStatusEnum.INEFFECTIVE.getFlag()) ||
                        receive.getExpressStatus() != (ReceiveExpressStatusEnum.CANCEL.getFlag())) {
                    String msg = "edit receive express error,because the express status already pass,code:" + code;
                    log.error(msg);
                    throw new RuntimeException(msg);
                } else {
                    expressReceive.setId(receive.getId());
                    expressReceive.setExpressStatus(ReceiveExpressStatusEnum.INEFFECTIVE.getFlag());
                    expressReceiveMapper.updateByPrimaryKeySelective(expressReceive);
                    expressLogService.log(expressReceive, ExpressLogActionEnum.RECEIVE_EXPRESS_UPDATE);
                }
            } else {
                boxExpressCompany(expressReceive);
                Long count = expressReceiveMapper.insertSelective(expressReceive);
                if (!(count > 0L)) {
                    String message =
                            "create receive express error,when insert table 'express_receive' the number of affected rows is 0";
                    log.error(message);
                    throw new ExpressException(message);
                }
                expressLogService.log(expressReceive, ExpressLogActionEnum.RECEIVE_EXPRESS_CREATE);
            }
        } catch (Exception e) {
            String message = "throw exception when create receive express";
            log.error(message, e);
            throw new RuntimeException(message, e);
        }
    }

    @Override
    public void modifyReceiveExpress(ExpressReceive expressReceive) {
        //配送
        if (expressReceive.getExpressWay() == ReceiveExpressDistributionTypeEnum.DISTRIBUTION_BOX.getFlag()) {
            expressReceive.setExpressStatus(ReceiveExpressStatusEnum.WAIT_INTO_BOX.getFlag());
        } else if (expressReceive.getExpressWay() == ReceiveExpressDistributionTypeEnum.DISTRIBUTION_DOOR.getFlag()) {
            expressReceive.setExpressStatus(ReceiveExpressStatusEnum.DOORING.getFlag());
        } else {
            expressReceive.setExpressStatus(ReceiveExpressStatusEnum.WAIT_SELF.getFlag());
        }

        if (!(expressReceiveMapper.updateByPrimaryKeySelective(expressReceive) > 0)) {
            String message =
                    "modify receive express error,when update table 'express_receive' the number of affected rows is 0";
            log.error(message);
            throw new RuntimeException(message);
        }
    }

    @Override
    public ExpressReceive getReceiveExpress(Long id) {
        ExpressReceive expressReceive = expressReceiveMapper.selectByPrimaryKey(id);
        if (expressReceive == null) {
            String message =
                    "get receive express error,when select table 'express_receive' the number of affected rows is 0,id="
                            + id;
            log.error(message);
            throw new RuntimeException(message);
        }
//            initProvinceCityDistrict(expressReceive);
        return expressReceive;
    }

    @Override
    public void updateReceiveExpressStatus(Long id, Integer status) {
        ExpressReceive expressReceive = new ExpressReceive();
        expressReceive.setId(id);
        expressReceive.setExpressStatus(status);
        int count = expressReceiveMapper.updateByPrimaryKeySelective(expressReceive);
        if (count <= 0) {
            String msg =
                    "update receive express status failed,when update table 'express_receive' the number of affected rows is 0";
            log.error(msg);
            throw new RuntimeException(msg);
        }
    }

    @Override
    public void updateReceiveExpress(Long expressId) {
        ExpressReceive expressReceive = expressReceiveMapper.selectByPrimaryKey(expressId);
        //帮我收件
        Integer expressWay = expressReceive.getExpressWay();
        BigDecimal expressWeight = expressReceive.getExpressWeight();
        if (expressReceive.getExpressType().equals(ReceiveExpressTypeEnum.HELP_RECEIVE.getFlag())) {
            expressReceive.setExpressStatus(ReceiveExpressStatusEnum.WAIT_PICKUP.getFlag());
            expressReceive.setServiceAmt(calcCostService.calcHelpReceiveDistributionCost(expressWay, expressWeight));
        } else {
            //快递点收件
            Integer status;
            if (expressWay == ReceiveExpressDistributionTypeEnum.DISTRIBUTION_BOX.getFlag()) {
                status = ReceiveExpressStatusEnum.WAIT_INTO_BOX.getFlag();
            } else if (expressWay == ReceiveExpressDistributionTypeEnum.DISTRIBUTION_DOOR.getFlag()) {
                status = ReceiveExpressStatusEnum.DOORING.getFlag();
            } else {
                throw new RuntimeException("配送方式异常");
            }
            expressReceive.setExpressStatus(status);
            expressReceive.setServiceAmt(calcCostService.calcReceiveDistributionCost(expressWay, expressWeight));
        }
        int count = expressReceiveMapper.updateByPrimaryKeySelective(expressReceive);
        expressLogService.log(expressReceive, ExpressLogActionEnum.RECEIVE_EXPRESS_UPDATE);
        if (count <= 0) {
            String msg =
                    "update receive express status failed,when update table 'express_receive' the number of affected rows is 0";
            log.error(msg);
            throw new RuntimeException(msg);
        }
    }

    @Override
    public void bindCustomerByPhone(String phone, Long customerId) {
        expressReceiveMapper.bindCustomerByPhone(phone, customerId);
    }

    @Override
    public List<ExpressReceive> selectExpressList(Integer[] status, Long customerId) {
        Map<String, Object> param = new HashMap<>();
        param.put("status", status);
        param.put("customerId", customerId);
        List<ExpressReceive> receiveList = expressReceiveMapper.selectByParams(param);
        if (!receiveList.isEmpty()) {
            for (ExpressReceive expressReceive : receiveList) {
//                    initProvinceCityDistrict(expressReceive);
            }
        }
        return receiveList;
    }

    @Override
    public ExpressReceive initProvinceCityDistrict(ExpressReceive expressReceive) {
        Region province = (Region) regionMapper.selectByPrimaryKey(expressReceive.getSenderProvinceId());
        Region city = (Region) regionMapper.selectByPrimaryKey(expressReceive.getSenderCityId());
        Region district = (Region) regionMapper.selectByPrimaryKey(expressReceive.getSenderDistrictId());
        expressReceive.setSenderProvince(province.getAreaName());
        expressReceive.setSenderCity(city.getAreaName());
        expressReceive.setSenderDistrict(district.getAreaName());
        return expressReceive;
    }

    /**
     * 封装快件对应的物流公司
     *
     * @param expressSend
     */
    private void boxExpressCompany(Express expressSend) throws ExpressException {
        Map<String, Object> param = new HashMap<>();
        if (expressSend.getCompanyId() != null && expressSend.getCompanyId() != 0) {
            param.put("companyId", expressSend.getCompanyId());
        } else if (expressSend.getCompanyCode() != null) {
            param.put("companyCode", expressSend.getCompanyCode());
        } else if (expressSend.getCompanyName() != null) {
            param.put("companyName", expressSend.getCompanyName());
        }
        List<ExpressCompany> list = expressCompanyMapper.selectByParams(param);
        if (list.isEmpty()) {
            String errorMsg = "未找到对应的快递公司，快递公司编号:" + expressSend.getCompanyCode() + "，快递公司名称:" + expressSend
                    .getCompanyName();
            log.error(errorMsg);
            throw new ExpressException(errorMsg);
        }
        ExpressCompany expressCompany = list.get(0);
        expressSend.setCompanyId(expressCompany.getId());
        expressSend.setCompanyCode(expressCompany.getCode());
        expressSend.setCompanyName(expressCompany.getName());
    }

    @Override
    public BigDecimal getOrderPrice(Long expressId, ExpressTypeEnum expressType) {
        OrderInfo orderInfo;
        if (ExpressTypeEnum.SEND.getFlag() == expressType.getFlag()) {
            orderInfo = orderInfoService.findByExpressSendId(expressId).get(0);
        } else {
            orderInfo = orderInfoService.findByExpressReceiveId(expressId).get(0);
        }
        return orderInfo.getAmount();
    }

    @Override
    public List<ExpressReceive> queryPage(Map<String, Object> paramMap) {
        return expressReceiveMapper.queryForManagerPage(paramMap);
    }

    @Override
    public void saveOrUpdate(ExpressReceive expressReceive, AdminUser adminUser) {
        if (expressReceive.getId() == null) {
            this.save(expressReceive);
            expressLogService.log(expressReceive, ExpressLogActionEnum.RECEIVE_EXPRESS_CREATE, adminUser);
        } else {
            this.update(expressReceive);
            expressLogService.log(get(expressReceive.getId()), ExpressLogActionEnum.RECEIVE_EXPRESS_UPDATE, adminUser);
        }
    }

    @Override
    public List<PushMessageVo> findPushOpenMessageByExpressStatus(ReceiveExpressStatusEnum statusEnum) {
        List<PushMessageVo> result = new ArrayList<>();
        try {
            if (statusEnum.equals(ReceiveExpressStatusEnum.INEFFECTIVE)) {
                List<Map> mapList = expressReceiveMapper.findPushOpenIdByExpressStatus(statusEnum.getFlag());
                for (Map map : mapList) {
                    PushMessageVo vo = new PushMessageVo();
                    vo.setOpenId(map.get("open_id").toString());
                    vo.setCreateTime(map.get("created_time").toString());
                    vo.setDesc(PushMessageEnum.RECEIVE_INEFFECTIVE);
                    result.add(vo);
                }
            } else {
                log.error("findPushOpenMessageByExpressStatus wrong status:" + statusEnum.getFlag());
            }
        } catch (Exception e) {
            log.error("findPushOpenMessageByExpressStatus error", e);
        }
        return result;
    }

    @Override
    public Integer updateIneffectiveToCancel() {
        return expressReceiveMapper.updateIneffectiveToCancel(SendExpressStatusEnum.CANCEL.getFlag(), SendExpressStatusEnum.INEFFECTIVE.getFlag(), 2);
    }

    @Override
    public void updateServiceAmt(BigDecimal payAmount, Long expressId) {
        expressReceiveMapper.addServiceAmt(payAmount, expressId);
        expressReceiveMapper.addReOrderServiceAmt(payAmount, expressId);
    }
}
