package com.school.biz.service.calc;

import com.school.biz.domain.entity.express.ExpressSend;

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
     * @param expressWay
     * @return
     */
    BigDecimal calcReceiveDistributionCost(Integer expressWay);

    /**
     * 计算帮我收件类型的配送费用
     *
     * @return
     */
    BigDecimal calcHelpReceiveDistributionCost();

    /**
     * 计算寄件类型的配送费用
     *
     * @param expressWay
     * @return
     */
    BigDecimal calcSendDistributionCost(Integer expressWay);

    /**
     * 计算寄件的运送费
     *
     * @param expressSend
     * @return
     */
    BigDecimal calcSendTransportCost(ExpressSend expressSend);
}
