package com.school.service.good.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.dao.good.GoodTypeMapper;
import com.school.domain.entity.good.GoodType;
import com.school.service.base.impl.BaseServiceImpl;
import com.school.service.good.GoodTypeService;

@Service
public class GoodTypeServiceImpl extends BaseServiceImpl<GoodType, GoodTypeMapper> implements GoodTypeService {

	@Autowired
	private GoodTypeMapper goodTypeMapper;
	
	@Override
	public List<GoodType> queryPage(Map<String, Object> paramMap) {
		return goodTypeMapper.queryPage(paramMap);
	}
	
	@Override
	public void saveOrUpdate(GoodType goodType) {
		if(goodType.getId() == null){
			this.save(goodType);
		}else{
			this.update(goodType);
		}
	}

	@Override
	public List<GoodType> getEffectAll() {
		return goodTypeMapper.getEffectAll();
	}

}
