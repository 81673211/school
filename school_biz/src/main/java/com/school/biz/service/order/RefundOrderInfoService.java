package com.school.biz.service.order;

import java.util.List;

import com.school.biz.dao.order.RefundOrderInfoMapper;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.domain.entity.order.RefundOrderInfo;
import com.school.biz.service.base.BaseService;

public interface RefundOrderInfoService extends BaseService<RefundOrderInfo, RefundOrderInfoMapper> {

	void refundOrderUpdateToSuccess(RefundOrderInfo refundOrderInfo);

	void refundOrderUpdateToRefunding(RefundOrderInfo refundOrderInfo);

	void refundOrderUpdateToFailed(RefundOrderInfo refundOrderInfo);

	List<RefundOrderInfo> getNotRefundOrder();
	
}
