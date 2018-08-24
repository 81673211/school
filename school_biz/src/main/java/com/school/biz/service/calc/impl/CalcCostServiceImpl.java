package com.school.biz.service.calc.impl;

import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.enumeration.DistributionTypeEnum;
import com.school.biz.service.calc.CalcCostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author jame
 */
@Service
@Slf4j
public class CalcCostServiceImpl implements CalcCostService {

    @Override
    public BigDecimal calcReceiveDistributionCost(Integer expressWay) {
        if (expressWay.equals(DistributionTypeEnum.DISTRIBUTION.getFlag())) {
            return BigDecimal.valueOf(0.2);
        } else {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public BigDecimal calcSendDistributionCost(Integer expressWay) {
        if (expressWay.equals(DistributionTypeEnum.DISTRIBUTION.getFlag())) {
            return BigDecimal.valueOf(0.1);
        } else {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public BigDecimal calcSendTransportCost(ExpressSend expressSend) {
        return BigDecimal.valueOf(0.01);
    }
}
