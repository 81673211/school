package com.school.service.calc;

import com.school.domain.entity.express.ExpressReceive;
import com.school.domain.entity.express.ExpressSend;
import com.school.exception.CalcCostException;

import java.math.BigDecimal;

/**
 * @author jame
 * @date 2018/7/31
 * @desc description
 */
public interface CalcCostService {

    /**
     * 计算收件类型的配送费用
     *
     * @param expressReceive
     * @return
     */
    BigDecimal calcReceiveDistributionCost(ExpressReceive expressReceive) throws CalcCostException;

    /**
     * 计算寄件类型的配送费用
     *
     * @param expressSend
     * @return
     */
    BigDecimal calcSendDistributionCost(ExpressSend expressSend) throws CalcCostException;
}
