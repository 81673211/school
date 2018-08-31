package com.school.biz.service.calc.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.biz.constant.RedisKeyNS;
import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.enumeration.DistributionTypeEnum;
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
    public BigDecimal calcReceiveDistributionCost(Integer expressWay) {
        if (expressWay.equals(DistributionTypeEnum.DISTRIBUTION.getFlag())) {
            return BigDecimal.valueOf(1);
        } else {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public BigDecimal calcSendDistributionCost(Integer expressWay) {
        if (expressWay.equals(DistributionTypeEnum.DISTRIBUTION.getFlag())) {
            return BigDecimal.valueOf(1.5);
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
    public BigDecimal calcHelpReceiveDistributionCost() {
        return BigDecimal.valueOf(1.5);
    }
}
