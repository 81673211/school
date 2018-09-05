package com.school.biz.service.box.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.biz.dao.box.ExpressBoxInfoMapper;
import com.school.biz.domain.entity.box.ExpressBoxInfo;
import com.school.biz.domain.entity.user.AdminUser;
import com.school.biz.service.base.impl.BaseServiceImpl;
import com.school.biz.service.box.ExpressBoxInfoService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ExpressBoxInfoServiceImpl extends BaseServiceImpl<ExpressBoxInfo, ExpressBoxInfoMapper> implements ExpressBoxInfoService {
	
	@Autowired
	private ExpressBoxInfoMapper expressBoxInfoMapper;
	
	@Override
    public List<ExpressBoxInfo> queryPage(Map<String, Object> paramMap) {
        return expressBoxInfoMapper.queryPage(paramMap);
    }

	@Override
    public void saveOrUpdate(ExpressBoxInfo expressBoxInfo, AdminUser user) {
        if (expressBoxInfo.getId() == null) {
            expressBoxInfo.setCreatedTime(new Date());
            expressBoxInfo.setOperId(user.getId());
            this.save(expressBoxInfo);
        } else {
        	expressBoxInfo.setOperId(user.getId());
            this.update(expressBoxInfo);
        }
    }
}
