package com.school.dao.good;

import java.util.List;
import java.util.Map;

import com.school.dao.base.BaseDao;
import com.school.domain.entity.good.GoodBrand;

public interface GoodBrandMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(GoodBrand record);

    int insertSelective(GoodBrand record);

    GoodBrand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodBrand record);

    int updateByPrimaryKey(GoodBrand record);
    
    List<GoodBrand> queryPage(Map<String, Object> paramMap);

    /**
     * 获取所有的品牌
     */
	List<GoodBrand> getAll();
}