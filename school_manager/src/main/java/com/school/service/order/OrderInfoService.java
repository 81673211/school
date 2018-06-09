package com.school.service.order;

import java.util.List;
import java.util.Map;

import com.school.dao.order.OrderInfoMapper;
import com.school.domain.entity.order.OrderInfo;
import com.school.service.base.BaseService;

public interface OrderInfoService extends BaseService<OrderInfo, OrderInfoMapper> {

	List<OrderInfo> queryPage(Map<String,Object> paramMap);

	void saveOrUpdate(OrderInfo orderInfo);

}
