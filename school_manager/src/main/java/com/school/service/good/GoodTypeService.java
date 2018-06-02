package com.school.service.good;

import com.school.dao.good.GoodTypeMapper;
import com.school.domain.entity.good.GoodType;
import com.school.service.base.BaseService;

import java.util.List;
import java.util.Map;

public interface GoodTypeService extends BaseService<GoodType, GoodTypeMapper> {

	List<GoodType> queryPage(Map<String,Object> paramMap);

	void saveOrUpdate(GoodType goodType);

	/**
	 * 获取所有启用的商品类型
	 */
	List<GoodType> getEffectAll();
	
}
