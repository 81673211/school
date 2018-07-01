package com.school.service.order.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.dao.order.OrderInfoMapper;
import com.school.domain.entity.order.OrderInfo;
import com.school.service.base.impl.BaseServiceImpl;
import com.school.service.order.OrderInfoService;

@Service
public class OrderInfoServiceImpl extends BaseServiceImpl<OrderInfo, OrderInfoMapper> implements OrderInfoService {

	@Autowired
	private OrderInfoMapper orderInfoMapper;

	@Override
	public OrderInfo findByOrderNo(String orderNo) {
		return orderInfoMapper.findByOrderNo(orderNo);
	}
	
	

}
