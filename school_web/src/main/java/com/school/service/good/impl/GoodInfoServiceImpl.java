package com.school.service.good.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.dao.good.GoodInfoMapper;
import com.school.domain.entity.good.GoodInfo;
import com.school.service.base.impl.BaseServiceImpl;
import com.school.service.good.GoodInfoService;

@Service
public class GoodInfoServiceImpl extends BaseServiceImpl<GoodInfo, GoodInfoMapper> implements GoodInfoService {

	@Autowired
	private GoodInfoMapper goodInfoMapper;
	
	@Override
	public GoodInfo getOne(Long id){
		System.out.println(id);
		return goodInfoMapper.selectByPrimaryKey(id);
	}

}
