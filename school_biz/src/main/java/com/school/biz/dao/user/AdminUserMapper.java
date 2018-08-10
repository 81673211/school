package com.school.biz.dao.user;

import java.util.List;
import java.util.Map;

import com.school.biz.dao.base.BaseDao;
import com.school.biz.domain.entity.user.AdminUser;
import com.school.biz.domain.entity.user.AdminUserVo;

public interface AdminUserMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);
    
    AdminUser getLoginUserByLoginNameAndLoginPassword(Map<String, String> map);

	List<AdminUserVo> queryPage(Map<String, Object> paramMap);

	AdminUserVo detail(Long id);

	/**
	 * 检查用户名是否重复
	 */
	Boolean checkIsNotExist(String loginName);

	AdminUser findByLoginName(String loginName);
}