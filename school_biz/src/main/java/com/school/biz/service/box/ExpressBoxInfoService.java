package com.school.biz.service.box;

import java.util.List;
import java.util.Map;

import com.school.biz.dao.box.ExpressBoxInfoMapper;
import com.school.biz.domain.entity.box.ExpressBoxInfo;
import com.school.biz.domain.entity.user.AdminUser;
import com.school.biz.service.base.BaseService;

public interface ExpressBoxInfoService extends BaseService<ExpressBoxInfo, ExpressBoxInfoMapper> {

    List<ExpressBoxInfo> queryPage(Map<String, Object> paramMap);

	void saveOrUpdate(ExpressBoxInfo expressBoxInfo, AdminUser sessionUser);

}
