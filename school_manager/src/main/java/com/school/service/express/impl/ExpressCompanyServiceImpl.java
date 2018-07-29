package com.school.service.express.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.dao.express.ExpressCompanyMapper;
import com.school.domain.entity.express.ExpressCompany;
import com.school.domain.entity.user.AdminUser;
import com.school.service.base.impl.BaseServiceImpl;
import com.school.service.express.ExpressCompanyService;

@Service
public class ExpressCompanyServiceImpl extends BaseServiceImpl<ExpressCompany, ExpressCompanyMapper> implements ExpressCompanyService {

	@Autowired
	private ExpressCompanyMapper expressCompanyMapper;
	
	@Override
	public List<ExpressCompany> queryPage(Map<String, Object> paramMap) {
		return expressCompanyMapper.queryPage(paramMap);
	}
	
	@Override
	public void saveOrUpdate(ExpressCompany expressCompany,AdminUser user) {
		if(expressCompany.getId() == null){
			expressCompany.setCreator(user.getAdminName());
			expressCompany.setCreatedTime(new Date());
			expressCompany.setModifier(user.getAdminName());
			expressCompany.setModifiedTime(new Date());
			this.save(expressCompany);
		}else{
			expressCompany.setModifier(user.getAdminName());
			expressCompany.setModifiedTime(new Date());
			this.update(expressCompany);
		}
	}

	@Override
	public List<ExpressCompany> findAll() {
		return expressCompanyMapper.findAll();
	}

	@Override
	public ExpressCompany findByCode(String code) {
		return expressCompanyMapper.findByCode(code);
	}

}
