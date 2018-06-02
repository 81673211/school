package com.school.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.school.base.BaseAbstractTest;
import com.school.cache.CommonService;
import com.school.cache.RedisClientTemplate;
import com.school.dao.good.GoodInfoMapper;
import com.school.domain.dto.good.CacheGoodTypeDto;
import com.school.domain.entity.good.GoodInfo;

public class RedisTest extends BaseAbstractTest {

	@Autowired
	private GoodInfoMapper goodInfoMapper;
	
	@Autowired
	private CommonService commonService;
	
	@Test
	public void testName() throws Exception {
		GoodInfo goodInfo = goodInfoMapper.selectByPrimaryKey(1L);
		System.out.println(goodInfo);
	}
	
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	@Test
	public void testRedis() throws Exception {
		String result = redisClientTemplate.get("test");
		System.out.println(result);
	}
	
	@Test
	public void testRedis2() throws Exception {
		System.out.println("--------xxxxx--------");
		CacheGoodTypeDto cacheGoodTypeDto = commonService.loadGoodType(2L);
		System.out.println(JSON.toJSON(cacheGoodTypeDto));
		
	}
	
}
