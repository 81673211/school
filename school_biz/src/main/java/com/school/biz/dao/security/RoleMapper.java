package com.school.biz.dao.security;

import com.school.biz.dao.base.BaseDao;
import com.school.biz.domain.entity.security.Role;
import com.school.biz.domain.entity.security.MenuJson;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseDao{
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    List<Role> queryPage(Map<String, Object> map);

	List<Role> getAll();

	/**
	 * 根据roleId获取资源ztree节点数据
	 */
	List<MenuJson> getZNodes(Map map);

	/**
	 * 根据roleId删除原有权限
	 */
	void delResourceByRoleId(Long roleId);

	/**
	 * 插入role_resource中间表
	 */
	void saveRoleResource(Map map);
	
	/**
	 * 批量插入role_resource中间表
	 */
	void saveRoleResourceOnce(Map map);

	/**
	 * 检查角色名称是否重复
	 */
	boolean checkIsNotExist(String roleName);
}