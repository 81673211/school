package com.school.service.good;

import com.school.dao.good.GoodInfoMapper;
import com.school.domain.entity.good.GoodInfo;
import com.school.service.base.BaseService;

public interface GoodInfoService extends BaseService<GoodInfo, GoodInfoMapper> {

	GoodInfo getOne(Long id);

}
