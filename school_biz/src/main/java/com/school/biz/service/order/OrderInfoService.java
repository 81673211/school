package com.school.biz.service.order;

import java.util.List;
import java.util.Map;

import com.school.biz.dao.order.OrderInfoMapper;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.service.base.BaseService;

public interface OrderInfoService extends BaseService<OrderInfo, OrderInfoMapper> {

	OrderInfo findByOrderNo(String orderNo);

	String createSendOrder(Long expressId);

	String createReceiveOrder(Long expressId);

	OrderInfo findByExpressReceiveId(Long expressId);

	OrderInfo findByExpressSendId(Long expressId);

	List<OrderInfo> getNotPayOrder();

	void orderSuccess(OrderInfo orderInfo);

	void orderPaying(OrderInfo orderInfo);

	void orderFailed(OrderInfo orderInfo);

	//-------- from manager ------
	List<OrderInfo> queryPage(Map<String,Object> paramMap);

	void saveOrUpdate(OrderInfo orderInfo);
}
