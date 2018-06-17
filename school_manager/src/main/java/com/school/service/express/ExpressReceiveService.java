package com.school.service.express;

import java.util.List;
import java.util.Map;

import com.school.dao.express.ExpressReceiveMapper;
import com.school.domain.entity.express.ExpressReceive;
import com.school.service.base.BaseService;

public interface ExpressReceiveService extends BaseService<ExpressReceive, ExpressReceiveMapper> {

	List<ExpressReceive> queryPage(Map<String,Object> paramMap);

	void saveOrUpdate(ExpressReceive expressReceive);

}
