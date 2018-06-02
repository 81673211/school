package com.school.service.security.impl;

import com.school.dao.security.MenuInfoMapper;
import com.school.domain.entity.security.MenuInfo;
import com.school.domain.vo.security.LeftMenu;
import com.school.service.base.impl.BaseServiceImpl;
import com.school.service.security.MenuInfoService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/** 
 * @author  作者：方梁
 * @date 创建时间：2016年12月12日 下午4:00:15
 * @description   
 */
@Service
public class MenuInfoServiceImpl extends BaseServiceImpl<MenuInfo, MenuInfoMapper> implements MenuInfoService {
	@Autowired
	private MenuInfoMapper menuInfoMapper;
	@Override
	public List<LeftMenu> getMyLeftMenus(Map<String, Object> map) {
		return menuInfoMapper.getMyLeftMenus(map);
	}
	@Override
	public List<MenuInfo> getAll() {
		return menuInfoMapper.getAll();
	}
	@Override
	public List<Long> getOwnMenuIdList(Object menuIdObj) {
		if(menuIdObj != null && StringUtils.isNotBlank((String)menuIdObj)){
			return menuInfoMapper.getOwnMenuIdList(Long.valueOf((String) menuIdObj));
		}
		return null;
	}

}
