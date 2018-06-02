package com.school.service.good.impl;

import com.school.dao.good.GoodBrandMapper;
import com.school.domain.entity.good.GoodBrand;
import com.school.service.base.impl.BaseServiceImpl;
import com.school.service.good.GoodBrandService;
import com.school.util.core.utils.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GoodBrandServiceImpl extends BaseServiceImpl<GoodBrand, GoodBrandMapper> implements GoodBrandService {

	@Autowired
	private GoodBrandMapper goodBrandMapper;
	
	@Override
	public List<GoodBrand> queryPage(Map<String, Object> paramMap) {
		return goodBrandMapper.queryPage(paramMap);
	}
	
	@Override
	public void saveOrUpdate(GoodBrand goodBrand) {
		if(goodBrand.getId() == null){
			goodBrand.setCreateTime(DateUtil.now());
			this.save(goodBrand);
		}else{
			this.update(goodBrand);
		}
	}

	@Override
	public List<GoodBrand> getAll() {
		return goodBrandMapper.getAll();
	}

}
