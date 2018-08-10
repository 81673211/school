package com.school.biz.service.express.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.biz.dao.express.ExpressCompanyMapper;
import com.school.biz.domain.entity.express.ExpressCompany;
import com.school.biz.domain.entity.user.AdminUser;
import com.school.biz.service.base.impl.BaseServiceImpl;
import com.school.biz.service.express.ExpressCompanyService;

/**
 * @author jame
 */
@Service
public class ExpressCompanyServiceImpl extends BaseServiceImpl<ExpressCompany, ExpressCompanyMapper>
        implements ExpressCompanyService {

    @Autowired
    private ExpressCompanyMapper companyMapper;

    // -----------------
    @Override
    public List<ExpressCompany> queryPage(Map<String, Object> paramMap) {
        return companyMapper.queryPage(paramMap);
    }

    @Override
    public void saveOrUpdate(ExpressCompany expressCompany, AdminUser user) {
        if (expressCompany.getId() == null) {
            expressCompany.setCreator(user.getAdminName());
            expressCompany.setCreatedTime(new Date());
            expressCompany.setModifier(user.getAdminName());
            expressCompany.setModifiedTime(new Date());
            this.save(expressCompany);
        } else {
            expressCompany.setModifier(user.getAdminName());
            expressCompany.setModifiedTime(new Date());
            this.update(expressCompany);
        }
    }

    @Override
    public List<ExpressCompany> findAll() {
        return companyMapper.findAll();
    }

    @Override
    public ExpressCompany findByCode(String code) {
        return companyMapper.findByCode(code);
    }
}
