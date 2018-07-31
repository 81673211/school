package com.school.service.calc;

import com.school.domain.entity.express.ExpressReceive;
import com.school.domain.entity.express.ExpressSend;
import com.school.exception.CalcCostException;
import com.school.util.core.Log;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author jame
 */
@Service
public class CalcCostServiceImpl implements CalcCostService {

    @Override
    public BigDecimal calcReceiveDistributionCost(ExpressReceive expressReceive) throws CalcCostException {
        try {
            return new BigDecimal(0.01);
        } catch (Exception e) {
            String msg = "calc receive distribution cost error";
            Log.error.error(msg, e);
            throw new CalcCostException(msg, e);
        }
    }

    @Override
    public BigDecimal calcSendDistributionCost(ExpressSend expressSend) throws CalcCostException {
        try {
            return new BigDecimal(0.01);
        } catch (Exception e) {
            String msg = "calc send distribution cost error";
            Log.error.error(msg, e);
            throw new CalcCostException(msg, e);
        }
    }
}
