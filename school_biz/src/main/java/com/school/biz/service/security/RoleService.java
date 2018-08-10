package com.school.biz.service.security;

import com.school.biz.dao.security.RoleMapper;
import com.school.biz.domain.entity.security.Role;
import com.school.biz.domain.entity.security.MenuJson;
import com.school.biz.service.base.BaseService;

import java.util.List;
import java.util.Map;

/** 
 * @author  作者：方梁
 * @date 创建时间：2016年10月26日 下午1:39:13
 * @description   
 */
public interface RoleService extends BaseService<Role, RoleMapper> {
	/**
	 * 
	 * 方法描述：分页查询角色列表
	 * <br>创建人：方梁
	 * <br>创建时间：2016年10月26日 下午1:40:16
	 * <br>最后修改人：方梁
	 * <br>最后修改时间：2016年10月26日 下午1:40:16
	 * <br>修改备注：
	 * <br>@version 1.0
	 */
	public List<Role> queryPage(Map<String, Object> map);
	
	/**
	 * 
	 * 方法描述：修改或新增角色信息
	 * <br>创建人：方梁
	 * <br>创建时间：2016年10月26日 下午1:44:51
	 * <br>最后修改人：方梁
	 * <br>最后修改时间：2016年10月26日 下午1:44:51
	 * <br>修改备注：
	 * <br>@version 1.0
	 */
	public Role saveOrUpdate(Role role);

	public List<Role> getAll();

	/**
	 * 根据roleId获取资源ztree节点
	 */
	public List<MenuJson> getZNodes(Long id);

	public void saveRoleResource(Long roleId, String resIds);

	public boolean checkIsNotExist(Long roleId, String roleName);
	
}
