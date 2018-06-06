package com.school.web.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.school.domain.entity.good.GoodInfo;
import com.school.service.good.GoodInfoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/test")
public class TestController {
	
	@Autowired
	private GoodInfoService goodInfoService;

	@RequestMapping("/test")
	public void test(){
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
		Long id = 1L;
		GoodInfo goodInfo = goodInfoService.getOne(id);
		System.out.println(JSON.toJSON(goodInfo));
	}
}
