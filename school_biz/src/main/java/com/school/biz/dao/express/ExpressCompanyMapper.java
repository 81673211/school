package com.school.biz.dao.express;

import com.school.biz.dao.base.BaseDao;
import com.school.biz.domain.entity.express.ExpressCompany;

import java.util.List;
import java.util.Map;

public interface ExpressCompanyMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(ExpressCompany record);

    int insertSelective(ExpressCompany record);

    ExpressCompany selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExpressCompany record);

    int updateByPrimaryKey(ExpressCompany record);

    List<ExpressCompany> queryPage(Map<String, Object> paramMap);

    List<ExpressCompany> findAll();

    List<ExpressCompany> findAllCooperate();

    ExpressCompany findByCode(String code);
}