package com.school.biz.service.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.school.biz.dao.order.OrderInfoMapper;
import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.service.base.BaseService;

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
	List<OrderInfo> queryPage(Map<String,Object> paramMap);

	void saveOrUpdate(OrderInfo orderInfo);

	void refund(HttpServletRequest request,String expressNo,BigDecimal refundFee) throws Exception;
}
