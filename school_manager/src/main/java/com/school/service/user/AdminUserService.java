package com.school.service.user;

import java.util.List;
import java.util.Map;

import com.school.common.model.AjaxResult;
import com.school.dao.user.AdminUserMapper;
import com.school.domain.entity.user.AdminUser;
import com.school.domain.vo.user.AdminUserVo;
import com.school.service.base.BaseService;

public interface AdminUserService extends BaseService<AdminUser, AdminUserMapper> {

	AdminUser getLoginUserByLoginNameAndLoginPassword(String userName,String password);

	List<AdminUserVo> queryPage(Map<String,Object> paramMap);

	void saveOrUpdate(AdminUser adminUser);

	AdminUserVo detail(Long id);

	boolean checkIsNotExist(Long userId,String loginName);

	AdminUser findByLoginName(String loginName);

}
