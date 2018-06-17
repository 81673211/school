package com.school.service.express.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.dao.express.ExpressSendMapper;
import com.school.domain.entity.express.ExpressSend;
import com.school.service.base.impl.BaseServiceImpl;
import com.school.service.express.ExpressSendService;

@Service
public class ExpressSendServiceImpl extends BaseServiceImpl<ExpressSend, ExpressSendMapper> implements ExpressSendService {

	@Autowired
	private ExpressSendMapper expressSendMapper;
	
	@Override
	public List<ExpressSend> queryPage(Map<String, Object> paramMap) {
		return expressSendMapper.queryForManagerPage(paramMap);
	}
	
	@Override
	public void saveOrUpdate(ExpressSend expressSend) {
		if(expressSend.getId() == null){
			this.save(expressSend);
		}else{
			this.update(expressSend);
		}
	}

}
