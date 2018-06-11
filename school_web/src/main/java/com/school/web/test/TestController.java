package com.school.web.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.school.domain.entity.good.GoodInfo;
import com.school.service.base.AccessTokenService;
import com.school.service.good.GoodInfoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/test")
public class TestController {
	
	@Autowired
	private GoodInfoService goodInfoService;
	@Autowired
	private AccessTokenService accessTokenService;

	@RequestMapping("/test")
	public void test(){
		String accessToken = accessTokenService.get();
		System.out.println(accessToken);
	}
}
