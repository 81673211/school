package com.school.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.school.base.BaseAbstractTest;
import com.school.dao.order.OrderInfoMapper;
import com.school.dao.security.RoleMapper;
import com.school.domain.entity.order.OrderInfo;

public class OrderInfoTest extends BaseAbstractTest {

	@Autowired
	private OrderInfoMapper orderInfoMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Test
	public void testName() throws Exception {
		Map<String, Object> paramMap = new HashMap<String,Object>();
		OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(1L);
		System.out.println(JSON.toJSON(orderInfo));
	}
	
	@Test
	public void testName2() throws Exception {
		System.out.println(JSON.toJSON(roleMapper.selectByPrimaryKey(1L)));
	}
}
