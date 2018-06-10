package com.school.service.express.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.dao.express.ExpressCompanyMapper;
import com.school.domain.entity.express.ExpressCompany;
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
	public void saveOrUpdate(ExpressCompany expressCompany) {
		if(expressCompany.getId() == null){
			this.save(expressCompany);
		}else{
			this.update(expressCompany);
		}
	}

}
