package com.school.service.express;

import java.util.List;
import java.util.Map;

import com.school.dao.express.ExpressSendMapper;
import com.school.domain.entity.express.ExpressSend;
import com.school.service.base.BaseService;

public interface ExpressSendService extends BaseService<ExpressSend, ExpressSendMapper> {

	List<ExpressSend> queryPage(Map<String,Object> paramMap);

	void saveOrUpdate(ExpressSend expressSend);

}
