package com.school.service.express;

import com.school.dao.express.ExpressCompanyMapper;
import com.school.domain.entity.express.ExpressCompany;
import com.school.domain.entity.user.AdminUser;
import com.school.service.base.BaseService;

import java.util.List;
import java.util.Map;

public interface ExpressCompanyService extends BaseService<ExpressCompany, ExpressCompanyMapper> {

	List<ExpressCompany> queryPage(Map<String,Object> paramMap);

	void saveOrUpdate(ExpressCompany expressCompany,AdminUser sessionUser);

	List<ExpressCompany> findAll();

	ExpressCompany findByCode(String code);

}
