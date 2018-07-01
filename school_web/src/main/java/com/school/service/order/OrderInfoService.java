package com.school.service.order;

import com.school.dao.order.OrderInfoMapper;
import com.school.domain.entity.order.OrderInfo;
import com.school.service.base.BaseService;

public interface OrderInfoService extends BaseService<OrderInfo, OrderInfoMapper> {

	OrderInfo findByOrderNo(String orderNo);

}
