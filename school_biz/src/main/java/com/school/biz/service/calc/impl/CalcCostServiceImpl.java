package com.school.biz.service.calc.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.biz.constant.RedisKeyNS;
import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.enumeration.ReceiveExpressDistributionTypeEnum;
import com.school.biz.enumeration.SendExpressCollectTypeEnum;
import com.school.biz.service.calc.CalcCostService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jame
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class CalcCostServiceImpl implements CalcCostService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public BigDecimal calcReceiveDistributionCost(int expressWay, BigDecimal expressWeight) {
        if (expressWay == ReceiveExpressDistributionTypeEnum.DISTRIBUTION_DOOR.getFlag()) {
            return calcHelpReceiveDistributionCost(expressWay, expressWeight).subtract(BigDecimal.valueOf(1));
        } else {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public BigDecimal calcSendDistributionCost(Integer expressWay, BigDecimal expressWeight) {
        if (expressWay.equals(SendExpressCollectTypeEnum.DOOR.getFlag())) {
            double weight = expressWeight.doubleValue();
            if (weight < 10) {
                return BigDecimal.ZERO;
            } else {
                return BigDecimal.valueOf((weight - 10) * 2).setScale(0, BigDecimal.ROUND_UP).add(
                        BigDecimal.valueOf(11));
            }
        } else {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public BigDecimal calcSendTransportCost(ExpressSend expressSend) {
        Long districtId = expressSend.getReceiverDistrictId();
        Long companyId = expressSend.getCompanyId();
        String companyShortName;
        if (companyId == 2) {
            companyShortName = ":sf";
        } else if (companyId == 12) {
            companyShortName = ":ys";
        } else {
            companyShortName = ":other";
        }
        String cacheFee = (String) redisTemplate.opsForHash()
                                                .get(RedisKeyNS.CACHE_SEND_EXPRESS_FEE,
                                                     districtId + companyShortName);
        BigDecimal fee;
        if (StringUtils.isBlank(cacheFee)) {
            fee = BigDecimal.valueOf(12.0);
        } else {
            fee = new BigDecimal(cacheFee);
        }
        return fee;
    }

    @Override
    public BigDecimal calcHelpReceiveDistributionCost(int type, BigDecimal expressWeight) {
        //兼容老数据
        double weight = expressWeight == null ? BigDecimal.ONE.doubleValue() : expressWeight.doubleValue();
        if (ReceiveExpressDistributionTypeEnum.DISTRIBUTION_BOX.getFlag() == type) {
            if (weight <= 2) {
                return BigDecimal.valueOf(1.5);
            } else if (2 < weight && weight < 5) {
                return BigDecimal.valueOf(2.5);
            } else if (5 <= weight && weight < 10) {
                return BigDecimal.valueOf(5);
            } else if (weight >= 10) {
                return BigDecimal.valueOf((weight - 10) * 2).setScale(0, BigDecimal.ROUND_UP).add(
                        BigDecimal.valueOf(10));
            } else {
                log.error("calcHelpReceiveDistributionCost error,wrong expressWeight:" + expressWeight
                        .toString());
                return BigDecimal.valueOf(20);
            }
        } else if (ReceiveExpressDistributionTypeEnum.DISTRIBUTION_DOOR.getFlag() == type) {
            if (weight <= 2) {
                return BigDecimal.valueOf(2.5);
            } else if (2 < weight && weight < 5) {
                return BigDecimal.valueOf(3);
            } else if (5 <= weight && weight < 10) {
                return BigDecimal.valueOf(6);
            } else if (weight >= 10) {
                return BigDecimal.valueOf((weight - 10) * 2).setScale(0, BigDecimal.ROUND_UP).add(
                        BigDecimal.valueOf(11));
            } else {
                log.error("calcHelpReceiveDistributionCost error,wrong expressWeight:" + expressWeight
                        .toString());
                return BigDecimal.valueOf(20);
            }
        } else {
            log.error("calcHelpReceiveDistributionCost error,wrong type:" + type);
            return BigDecimal.valueOf(20);
        }
    }
}
