package com.school.biz.service.calc;

import java.math.BigDecimal;

import com.school.biz.domain.entity.express.ExpressReceive;
import com.school.biz.domain.entity.express.ExpressSend;

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
    BigDecimal calcReceiveDistributionCost(ExpressReceive expressReceive);

    /**
     * 计算寄件类型的配送费用
     *
     * @param expressSend
     * @return
     */
    BigDecimal calcSendDistributionCost(ExpressSend expressSend);
}
