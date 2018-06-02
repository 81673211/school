package com.school.service.good;

import java.util.List;
import java.util.Map;

import com.school.dao.good.GoodBrandMapper;
import com.school.domain.entity.good.GoodBrand;
import com.school.service.base.BaseService;

public interface GoodBrandService extends BaseService<GoodBrand, GoodBrandMapper> {

	List<GoodBrand> queryPage(Map<String,Object> paramMap);

	void saveOrUpdate(GoodBrand goodBrand);

	List<GoodBrand> getAll();
	
}
