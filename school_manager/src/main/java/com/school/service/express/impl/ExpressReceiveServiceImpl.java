package com.school.service.express.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.dao.express.ExpressReceiveMapper;
import com.school.domain.entity.express.ExpressReceive;
import com.school.service.base.impl.BaseServiceImpl;
import com.school.service.express.ExpressReceiveService;

@Service
public class ExpressReceiveServiceImpl extends BaseServiceImpl<ExpressReceive, ExpressReceiveMapper> implements ExpressReceiveService {

	@Autowired
	private ExpressReceiveMapper expressReceiveMapper;
	
	@Override
	public List<ExpressReceive> queryPage(Map<String, Object> paramMap) {
		return expressReceiveMapper.queryForManagerPage(paramMap);
	}
	
	@Override
	public void saveOrUpdate(ExpressReceive expressReceive) {
		if(expressReceive.getId() == null){
			this.save(expressReceive);
		}else{
			this.update(expressReceive);
		}
	}

}
