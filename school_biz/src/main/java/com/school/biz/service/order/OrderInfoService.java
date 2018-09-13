package com.school.biz.service.order;

import com.school.biz.dao.order.OrderInfoMapper;
import com.school.biz.domain.entity.express.Express;
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

    String createSendReOrder(ExpressSend expressSend);

    String createServiceReOrder(Express express);

    String createFreightReOrder(Express express);

    String createReceiveOrder(Long expressId, String type);

    List<OrderInfo> findByExpressReceiveId(Long expressId);

    List<OrderInfo> findByExpressSendId(Long expressId);

    List<OrderInfo> getNotPayOrder();

    void orderUpdateToSuccess(OrderInfo orderInfo);

    void orderUpdateToPaying(OrderInfo orderInfo);

    void orderUpdateToFailed(OrderInfo orderInfo);

    //-------- from manager ------
    List<OrderInfo> queryPage(Map<String, Object> paramMap);

    void saveOrUpdate(OrderInfo orderInfo);

    // 退款
    void refund(HttpServletRequest request, Long expressSendId, BigDecimal refundFee) throws Exception;

    // 寄件补单
    void expressSendReOrder(HttpServletRequest request, Long expressSendId, BigDecimal reOrderAmt) throws Exception;
    
    // 收件补单
    void expressReceiveReOrder(HttpServletRequest request, Long expressSendId, BigDecimal reOrderAmt) throws Exception;


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

    /**
     * 填充订单中的快递号
     *
     * @param id
     * @param code
     */
    void fillExpressNo(Long id, String code);

    /**
     * 查找订单对应的补单记录
     *
     * @param id
     * @return
     */
    List selectSupplementIdsById(Long id);
}
