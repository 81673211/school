package com.school.dao.express;

import java.util.List;
import java.util.Map;

import com.school.dao.base.BaseDao;
import com.school.domain.entity.express.ExpressCompany;

public interface ExpressCompanyMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(ExpressCompany record);

    int insertSelective(ExpressCompany record);

    ExpressCompany selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExpressCompany record);

    int updateByPrimaryKey(ExpressCompany record);
    
    List<ExpressCompany> queryPage(Map<String, Object> paramMap);
}