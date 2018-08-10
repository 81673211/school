package com.school.biz.service.calc.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.school.biz.domain.entity.express.ExpressReceive;
import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.service.calc.CalcCostService;
import com.school.biz.util.Log;

/**
 * @author jame
 */
@Service
public class CalcCostServiceImpl implements CalcCostService {

    @Override
    public BigDecimal calcReceiveDistributionCost(ExpressReceive expressReceive) {
        try {
            return BigDecimal.valueOf(0.01);
        } catch (Exception e) {
            String msg = "calc receive distribution cost error";
            Log.error.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    @Override
    public BigDecimal calcSendDistributionCost(ExpressSend expressSend) {
        try {
            return BigDecimal.valueOf(0.01);
        } catch (Exception e) {
            String msg = "calc send distribution cost error";
            Log.error.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }
}
