package com.school.biz.dao.security;

import com.school.biz.dao.base.BaseDao;
import com.school.biz.domain.entity.security.MenuInfo;
import com.school.biz.domain.entity.security.LeftMenu;

import java.util.List;
import java.util.Map;

public interface MenuInfoMapper extends BaseDao{
    int deleteByPrimaryKey(Long id);

    int insert(MenuInfo record);

    int insertSelective(MenuInfo record);

    MenuInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MenuInfo record);

    int updateByPrimaryKey(MenuInfo record);
    
    List<LeftMenu> getMyLeftMenus(Map<String, Object> map);

	List<MenuInfo> getAll();

	/**
	 * 根据传入的menuId获取包含的菜单
	 */
	List<Long> getOwnMenuIdList(Long menuId);
}