package com.school.biz.service.express;

import java.util.List;
import java.util.Map;

import com.school.biz.dao.express.ExpressCompanyMapper;
import com.school.biz.domain.entity.express.ExpressCompany;
import com.school.biz.domain.entity.user.AdminUser;
import com.school.biz.service.base.BaseService;

/**
 * @author jame
 */
public interface ExpressCompanyService extends BaseService<ExpressCompany, ExpressCompanyMapper> {

    //--------- from manager ---------

    List<ExpressCompany> queryPage(Map<String, Object> paramMap);

    void saveOrUpdate(ExpressCompany expressCompany, AdminUser sessionUser);

    List<ExpressCompany> findAll();

    ExpressCompany findByCode(String code);
}
