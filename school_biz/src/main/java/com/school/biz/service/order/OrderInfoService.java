package com.school.biz.service.order;

import com.school.biz.dao.order.OrderInfoMapper;
import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.service.base.BaseService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderInfoService extends BaseService<OrderInfo, OrderInfoMapper> {

    OrderInfo findByOrderNo(String orderNo);

    String createSendOrder(ExpressSend expressSend);

    String createReceiveOrder(Long expressId);

    OrderInfo findByExpressReceiveId(Long expressId);

    OrderInfo findByExpressSendId(Long expressId);

    List<OrderInfo> getNotPayOrder();

    void orderUpdateToSuccess(OrderInfo orderInfo);

    void orderUpdateToPaying(OrderInfo orderInfo);

    void orderUpdateToFailed(OrderInfo orderInfo);

    //-------- from manager ------
    List<OrderInfo> queryPage(Map<String, Object> paramMap);

    void saveOrUpdate(OrderInfo orderInfo);

    // 退款
    void refund(HttpServletRequest request, String expressNo, BigDecimal refundFee) throws Exception;

    // 补单a
    void reOrder(HttpServletRequest request, String expressNo, BigDecimal reOrderAmt) throws Exception;


    /**
     * 查找该快件的总的费用
     *
     * @param expressId
     * @return
     */
    BigDecimal findAllPriceByExpress(Long expressId);

    /**
     * 快件是否全额退款
     *
     * @param expressSend
     * @return
     */
    boolean isRefundAll(ExpressSend expressSend);
}
