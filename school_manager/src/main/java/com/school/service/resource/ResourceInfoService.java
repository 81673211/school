package com.school.service.resource;

import java.util.List;
import java.util.Map;

import com.school.dao.resource.ResourceInfoMapper;
import com.school.domain.entity.resource.ResourceInfo;
import com.school.domain.vo.resource.ResourceInfoVo;
import com.school.service.base.BaseService;

public interface ResourceInfoService extends BaseService<ResourceInfo, ResourceInfoMapper> {

	List<ResourceInfoVo> queryPage(Map<String,Object> paramMap);

	void saveOrUpdate(ResourceInfo resourceInfo);

	ResourceInfoVo detail(Long id);

	List<ResourceInfo> getAllParentResources();

	/**
	 * 根据用户id查询拥有的资源
	 */
	List<ResourceInfo> getOwnResources(Long adminUserId);

	/**
	 * 判断资源名称是否不重复（如果是修改，则还需要判断是否真的更改）
	 */
	boolean checkResNameIsNotExist(Long resId, String resName);

	/**
	 * 判断资源Url是否不重复（如果是修改，则还需要判断是否真的更改）
	 */
	boolean checkResUrlIsNotExist(Long resId, String resUrl);
	
}
