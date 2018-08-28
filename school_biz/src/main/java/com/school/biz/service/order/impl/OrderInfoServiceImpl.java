package com.school.biz.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.school.biz.constant.ConfigProperties;
import com.school.biz.constant.Constants;
import com.school.biz.dao.express.ExpressReceiveMapper;
import com.school.biz.dao.express.ExpressSendMapper;
import com.school.biz.dao.order.OrderInfoMapper;
import com.school.biz.dao.order.RefundOrderInfoMapper;
import com.school.biz.domain.entity.express.Express;
import com.school.biz.domain.entity.express.ExpressReceive;
import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.domain.entity.order.RefundOrderInfo;
import com.school.biz.enumeration.*;
import com.school.biz.extension.wxpay.sdk.WXPay;
import com.school.biz.extension.wxpay.sdk.WXPayConfigImpl;
import com.school.biz.extension.wxpay.sdk.WXPayConstants.SignType;
import com.school.biz.extension.wxpay.sdk.WXPayUtil;
import com.school.biz.service.base.impl.BaseServiceImpl;
import com.school.biz.service.calc.CalcCostService;
import com.school.biz.service.express.ExpressSendService;
import com.school.biz.service.order.OrderInfoService;
import com.school.biz.service.order.RefundOrderInfoService;
import com.school.biz.util.AmountUtils;
import com.school.biz.util.DateUtil;
import com.school.biz.util.IdWorkerUtil;
import com.school.biz.util.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class OrderInfoServiceImpl extends BaseServiceImpl<OrderInfo, OrderInfoMapper> implements OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private ExpressReceiveMapper expressReceiveMapper;
    @Autowired
    private CalcCostService calcCostService;
    @Autowired
    private RefundOrderInfoMapper refundOrderInfoMapper;
    @Autowired
    private RefundOrderInfoService refundOrderInfoService;
    @Autowired
    private ExpressSendMapper expressSendMapper;
    @Autowired
    private ExpressSendService expressSendService;

    @Override
    public OrderInfo findByOrderNo(String orderNo) {
        return orderInfoMapper.findByOrderNo(orderNo);
    }

    @Override
    public String createSendOrder(ExpressSend expressSend) {
        OrderInfo orderInfo = initOrderInfo(expressSend);
        if (!(orderInfoMapper.insertSelective(orderInfo) > 0)) {
            String message =
                    "create send order error,when insert table 'order_info' the number of affected rows is 0";
            log.error(message);
            throw new RuntimeException(message);
        } else {
            return orderInfo.getOrderNo();
        }
    }

    @Override
    public String createReSendOrder(ExpressSend expressSend) {
        List<OrderInfo> orderInfos = findByExpressSendId(expressSend.getId());
        if (checkReOrderExpressAlreadyPay(orderInfos)) {
            return null;
        }
        return createSendOrder(expressSend);
    }

    @Override
    public String createReceiveOrder(Long expressId) {
        List<OrderInfo> orderInfos = findByExpressReceiveId(expressId);
        if (checkExpressAlreadyPay(orderInfos)) {
            return null;
        }
        ExpressReceive expressReceive = expressReceiveMapper.selectByPrimaryKey(expressId);
        OrderInfo orderInfo = initOrderInfo(expressReceive);
        int result = orderInfoMapper.insertSelective(orderInfo);
        if (result <= 0) {
            String message =
                    "create receive order error,when insert table 'order_info' the number of affected rows is 0";
            log.error(message);
            throw new RuntimeException(message);
        }
        return orderInfo.getOrderNo();
    }

    private boolean checkExpressAlreadyPay(List<OrderInfo> orderInfos) {
        for (OrderInfo orderInfo : orderInfos) {
            int status = orderInfo.getStatus();
            if (OrderStatusEnum.SUCCESS.getCode().equals(status)) {
                log.error("快递已成功支付过，请勿重复支付.，orderNo:{}", orderInfo.getOrderNo());
                return true;
            }
        }
        return false;
    }

    private boolean checkReOrderExpressAlreadyPay(List<OrderInfo> orderInfos) {
        for (OrderInfo orderInfo : orderInfos) {
            int status = orderInfo.getStatus();
            if (OrderStatusEnum.SUCCESS.getCode().equals(status) &&
                    orderInfo.getOrderNo().startsWith(Constants.ORDER_NO_TYPE_REORDER)) {
                log.error("快递已成功支付补单，请勿重复支付.，orderNo:{}", orderInfo.getOrderNo());
                return true;
            }
        }
        return false;
    }

    @Override
    public List<OrderInfo> findByExpressReceiveId(Long expressId) {
        return findByExpressIdAndType(expressId, ExpressTypeEnum.RECEIVE.getFlag());
    }

    @Override
    public List<OrderInfo> findByExpressSendId(Long expressId) {
        return findByExpressIdAndType(expressId, ExpressTypeEnum.SEND.getFlag());
    }

    private List<OrderInfo> findByExpressIdAndType(Long expressId, int expressType) {
        return orderInfoMapper.findByExpressIdAndType(expressId, expressType);
    }

    /**
     * 初始化订单对象
     *
     * @param express
     * @return
     */
    private OrderInfo initOrderInfo(Express express) {
        OrderInfo orderInfo = new OrderInfo();
        if (express instanceof ExpressSend) {
            ExpressSend expressSend = (ExpressSend) express;
            orderInfo.setExpressType(ExpressTypeEnum.SEND.getFlag());
            orderInfo.setAmount(calcCostService.calcSendTransportCost(expressSend).add(
                    calcCostService.calcSendDistributionCost(expressSend.getExpressWay())));
            orderInfo.setTradeSummary("寄件快递费");
        } else if (express instanceof ExpressReceive) {
            ExpressReceive expressReceive = (ExpressReceive) express;
            orderInfo.setExpressType(ExpressTypeEnum.RECEIVE.getFlag());
            orderInfo.setAmount(calcCostService.calcReceiveDistributionCost(DistributionTypeEnum.DISTRIBUTION.getFlag()));
            orderInfo.setTradeSummary("收件服务费");
        } else {
            String errorMsg = "error express type.";
            log.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        orderInfo.setExpressId(express.getId());
        orderInfo.setExpressCode(express.getCode());
        orderInfo.setCustomerId(express.getCustomerId());
        orderInfo.setStatus(OrderStatusEnum.UNPAY.getCode());
        orderInfo.setOrderNo(IdWorkerUtil.generateOrderNo(Constants.ORDER_NO_TYPE_ORDER));
        orderInfo.setNotifyUrl(ConfigProperties.WXPAY_NOTIFY_URL);
        return orderInfo;
    }

    @Override
    public List<OrderInfo> getNotPayOrder() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tenMinutesAgo", DateUtil.offsiteMinute(new Date(), -10));
        return orderInfoMapper.getNotPayOrder(map);
    }

    /**
     * 将订单更新为成功
     *
     * @param orderInfo
     */
    @Override
    public void orderUpdateToSuccess(OrderInfo orderInfo) {
        if (orderInfo == null) {
            return;
        }
        orderInfo.setStatus(OrderStatusEnum.SUCCESS.getCode());
        orderInfo.setSucTime(new Date());
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
    }

    /**
     * 将订单更新为支付处理中
     *
     * @param orderInfo
     */
    @Override
    public void orderUpdateToPaying(OrderInfo orderInfo) {
        if (orderInfo == null) {
            return;
        }
        orderInfo.setStatus(OrderStatusEnum.PAYING.getCode());
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
    }

    /**
     * 将订单更新为失败
     *
     * @param orderInfo
     */
    @Override
    public void orderUpdateToFailed(OrderInfo orderInfo) {
        if (orderInfo == null) {
            return;
        }
        orderInfo.setStatus(OrderStatusEnum.FAILED.getCode());
        orderInfo.setSucTime(new Date());
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
    }

    @Override
    public List<OrderInfo> queryPage(Map<String, Object> paramMap) {
        return orderInfoMapper.queryPage(paramMap);
    }

    @Override
    public void saveOrUpdate(OrderInfo orderInfo) {
        if (orderInfo.getId() == null) {
            this.save(orderInfo);
        } else {
            this.update(orderInfo);
        }
    }

    @Override
    public void refund(HttpServletRequest request, Long expressSendId, BigDecimal refundFee) throws Exception {
        Long operId = SessionUtils.getSessionUser(request).getId();

        ExpressSend expressSend = expressSendMapper.selectByPrimaryKey(expressSendId);

        // 根据快递号查询其所有支付成功订单（按支付金额降序）
        List<OrderInfo> orderInfos = orderInfoMapper.findSuccessOrdersByExpressId(expressSend.getId());

        if (orderInfos == null || orderInfos.size() < 1) {
            throw new Exception("该快递没有成功交易记录");
        }

        // 判断退款费用是否合法
        boolean isRefundFeeIllegal = this.isRefundFeeIllegal(expressSend, orderInfos, refundFee);
        if (!isRefundFeeIllegal) {
            throw new Exception("退款金额不能大于（快递金额-服务费）");
        }

        /**
         *  退款逻辑：
         *      循环判断快递所有成功支付的订单，如果退款金额比该轮订单金额大，则退该订单全额，且继续循环；
         *  如果退款金额比该轮次订单金额小，则退退款金额，且跳出循环。
         */
        for (OrderInfo orderInfo : orderInfos) {
            if (refundFee.compareTo(orderInfo.getAmount()) == 1) {
                this.refundOrderInfo(orderInfo, orderInfo.getAmount(), operId);
            } else {
                this.refundOrderInfo(orderInfo, refundFee, operId);
                break;
            }
        }

    }

    /**
     * 判断退款金额是否合法
     */
    private boolean isRefundFeeIllegal(ExpressSend expressSend, List<OrderInfo> orderInfos, BigDecimal refundFee) {
        boolean flag = true;

        // 取出订单服务费
        BigDecimal serviceAmt = expressSend.getServiceAmt();

        // 最大可退金额=每笔可退金额之和-服务费
        BigDecimal refundCurrMaxAmt = new BigDecimal("0");
        // 取出每笔成功订单（订单金额-已退款金额），即每笔订单可退金额
        for (OrderInfo orderInfo : orderInfos) {
            refundCurrMaxAmt = refundCurrMaxAmt.add(orderInfo.getAmount().subtract(orderInfo.getRefundAmt()));
        }
        BigDecimal finalRefundAmt = refundCurrMaxAmt.subtract(serviceAmt);

        if (refundFee.compareTo(finalRefundAmt) == 1) {
            flag = false;
        }

        return flag;
    }

    private void refundOrderInfo(OrderInfo orderInfo, BigDecimal refundFee, Long operId) throws Exception {
        // 插入退款订单
        RefundOrderInfo refundOrderInfo = this.createRefundOrder(orderInfo, refundFee);
        refundOrderInfo.setOperId(operId);

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", orderInfo.getOrderNo());
        data.put("nonce_str", WXPayUtil.generateUUID());
        data.put("out_refund_no", refundOrderInfo.getRefundOrderNo());
        data.put("total_fee", AmountUtils.changeY2F(orderInfo.getAmount().toString()));
        data.put("refund_fee", AmountUtils.changeY2F(refundFee.toString()));

        try {
            WXPayConfigImpl config = WXPayConfigImpl.getInstance();
            WXPay wxpay = new WXPay(config);
            Map<String, String> result = wxpay.refund(data);
            log.info("result:" + JSON.toJSONString(result));
            if ("SUCCESS".equals(result.get("result_code"))) {
                String localSign = WXPayUtil.generateSignature(result, ConfigProperties.WXPAY_KEY,
                        SignType.HMACSHA256);
                if (!localSign.equals(result.get("sign"))) {
                    log.error("微信退款验签失败");
                }
                // 修改订单累计退款金额
                orderInfo.setRefundAmt(orderInfo.getRefundAmt().add(refundFee));
                orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
            } else {
                refundOrderInfoService.update(refundOrderInfo);
                throw new Exception("微信退款失败:" + result.get("err_code_des"));
            }
        } catch (Exception e) {
            log.error("微信退款失败：" + e.getMessage());
            throw new Exception("微信退款失败");
        }
    }

    private RefundOrderInfo createRefundOrder(OrderInfo orderInfo, BigDecimal refundFee) {
        RefundOrderInfo refundOrderInfo = new RefundOrderInfo();
        refundOrderInfo.setRefundOrderNo(IdWorkerUtil.generateOrderNo(Constants.ORDER_NO_TYPE_REFUND));

        refundOrderInfo.setOrderNo(orderInfo.getOrderNo());
        refundOrderInfo.setCustomerId(orderInfo.getCustomerId());
        refundOrderInfo.setExpressType(orderInfo.getExpressType());
        refundOrderInfo.setExpressId(orderInfo.getExpressId());
        refundOrderInfo.setExpressCode(orderInfo.getExpressCode());

        refundOrderInfo.setAmount(refundFee);

        refundOrderInfo.setCreatedTime(new Date());
        refundOrderInfo.setTradeSummary(orderInfo.getTradeSummary());
        refundOrderInfo.setStatus(RefundOrderStatusEnum.REFUNDING.getCode());

        refundOrderInfoMapper.insertSelective(refundOrderInfo);
        return refundOrderInfo;
    }

    /**
     * 补单
     *
     * @throws Exception
     */
    @Override
    public void reOrder(HttpServletRequest request, Long expressSendId, BigDecimal reOrderAmt) throws Exception {
    	ExpressSend expressSend = expressSendMapper.selectByPrimaryKey(expressSendId);
		if(expressSend == null){
			throw new Exception("订单号不存在");
		}
		// 更改快递状态为等待补单支付
		expressSend.setReOrderAmt(reOrderAmt);
		expressSend.setExpressStatus(SendExpressStatusEnum.SUPPLEMENT.getFlag());
		expressSendService.saveOrUpdate(expressSend);
    }

    /**
     * 是否退全款
     */
    @Override
    public boolean isRefundAll(ExpressSend expressSend) {
        boolean flag = true;

        // 取出订单服务费
        BigDecimal serviceAmt = expressSend.getServiceAmt();

        // 查询寄件总金额
        List<OrderInfo> orderInfos = orderInfoMapper.findSuccessOrdersByExpressId(expressSend.getId());
        BigDecimal orderTotalAmt = new BigDecimal("0");
        for (OrderInfo orderInfo : orderInfos) {
            orderTotalAmt = orderTotalAmt.add(orderInfo.getAmount());
        }

        // 查询退款总金额
        List<RefundOrderInfo> refundOrderInfos = refundOrderInfoMapper.findSuccessRefundOrdersByExpressNo(expressSend.getCode());
        BigDecimal refundTotalAmt = new BigDecimal("0");
        for (RefundOrderInfo refundOrderInfo : refundOrderInfos) {
            refundTotalAmt = refundTotalAmt.add(refundOrderInfo.getAmount());
        }

        // 如果 快递总金额-服务费>退款金额，则不是退全款
        if (orderTotalAmt.subtract(serviceAmt).compareTo(refundTotalAmt) == 1) {
            flag = false;
        }

        return flag;

    }

    @Override
    public BigDecimal findAllPriceByExpress(Long expressId) {
        return orderInfoMapper.findAllPriceByExpress(expressId);
    }

    @Override
    public void fillExpressNo(Long expressId, String expressNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("expressId", expressId);
        map.put("expressNo", expressNo);
        orderInfoMapper.fillExpressNo(map);
    }
}
