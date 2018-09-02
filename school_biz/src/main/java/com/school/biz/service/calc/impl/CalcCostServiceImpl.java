package com.school.biz.service.calc.impl;

import com.school.biz.constant.RedisKeyNS;
import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.enumeration.DistributionTypeEnum;
import com.school.biz.service.calc.CalcCostService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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
    public BigDecimal calcReceiveDistributionCost(Integer expressWay) {
        if (expressWay.equals(DistributionTypeEnum.DISTRIBUTION.getFlag())) {
            return BigDecimal.valueOf(1);
        } else {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public BigDecimal calcSendDistributionCost(Integer expressWay, BigDecimal expressWeight) {
        if (expressWay.equals(DistributionTypeEnum.DISTRIBUTION.getFlag())) {
            double weight = expressWeight.doubleValue();
            if (weight <= 2) {
                return BigDecimal.valueOf(2.5);
            } else if (2 < weight && weight < 5) {
                return BigDecimal.valueOf(3);
            } else if (5 <= weight && weight < 10) {
                return BigDecimal.valueOf(6);
            } else if (weight >= 10) {
                return BigDecimal.valueOf((weight - 10) * 2).setScale(0, BigDecimal.ROUND_UP).add(BigDecimal.valueOf(11));
            } else {
                log.error("calcSendDistributionCost error,wrong expressWeight:" + expressWeight.toString());
                return BigDecimal.valueOf(20);
            }
        } else {
            return BigDecimal.ZERO;
        }
    }


    @Override
    public BigDecimal calcSendTransportCost(ExpressSend expressSend) {
        Long districtId = expressSend.getReceiverDistrictId();
        Long companyId = expressSend.getCompanyId();
        String cacheFee = (String) redisTemplate.opsForHash()
                .get(RedisKeyNS.CACHE_SEND_EXPRESS_FEE, districtId + (companyId == 2 ? ":sf" : ":other"));
        BigDecimal fee;
        if (StringUtils.isBlank(cacheFee)) {
            fee = BigDecimal.valueOf(12.0);
        } else {
            fee = new BigDecimal(cacheFee);
        }
        return fee;
    }

    @Override
    public BigDecimal calcHelpReceiveDistributionCost(String type, BigDecimal expressWeight) {
        double weight = expressWeight.doubleValue();
        if ("box".equals(type)) {
            if (weight <= 2) {
                return BigDecimal.valueOf(1.5);
            } else if (2 < weight && weight < 5) {
                return BigDecimal.valueOf(2.5);
            } else if (5 <= weight && weight < 10) {
                return BigDecimal.valueOf(5);
            } else if (weight >= 10) {
                return BigDecimal.valueOf((weight - 10) * 2).setScale(0, BigDecimal.ROUND_UP).add(BigDecimal.valueOf(10));
            } else {
                log.error("calcHelpReceiveDistributionCost error,wrong expressWeight:" + expressWeight.toString());
                return BigDecimal.valueOf(20);
            }
        } else if ("door".equals(type)) {
            if (weight <= 2) {
                return BigDecimal.valueOf(2.5);
            } else if (2 < weight && weight < 5) {
                return BigDecimal.valueOf(3);
            } else if (5 <= weight && weight < 10) {
                return BigDecimal.valueOf(6);
            } else if (weight >= 10) {
                return BigDecimal.valueOf((weight - 10) * 2).setScale(0, BigDecimal.ROUND_UP).add(BigDecimal.valueOf(11));
            } else {
                log.error("calcHelpReceiveDistributionCost error,wrong expressWeight:" + expressWeight.toString());
                return BigDecimal.valueOf(20);
            }
        } else {
            log.error("calcHelpReceiveDistributionCost error,wrong type:" + type);
            return BigDecimal.valueOf(20);
        }
    }
}
