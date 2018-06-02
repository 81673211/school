package com.school.dao.good;

import java.util.List;
import java.util.Map;

import com.school.dao.base.BaseDao;
import com.school.domain.entity.good.GoodType;

public interface GoodTypeMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(GoodType record);

    int insertSelective(GoodType record);

    GoodType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodType record);

    int updateByPrimaryKey(GoodType record);

	List<GoodType> queryPage(Map<String, Object> paramMap);

	/**
	 * 获取所有启用的商品类型
	 */
	List<GoodType> getEffectAll();
}