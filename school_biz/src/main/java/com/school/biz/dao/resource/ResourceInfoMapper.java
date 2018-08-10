package com.school.biz.dao.resource;

import java.util.List;
import java.util.Map;

import com.school.biz.dao.base.BaseDao;
import com.school.biz.domain.entity.resource.ResourceInfo;
import com.school.biz.domain.entity.resource.ResourceInfoVo;

public interface ResourceInfoMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(ResourceInfo record);

    int insertSelective(ResourceInfo record);

    ResourceInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ResourceInfo record);

    int updateByPrimaryKey(ResourceInfo record);
    
    List<ResourceInfoVo> queryPage(Map<String, Object> paramMap);

	ResourceInfoVo detail(Long id);

	List<ResourceInfo> getAllParentResources();

	/**
	 * 根据用户id查询拥有的资源
	 */
	List<ResourceInfo> getOwnResources(Long userId);

	/**
	 * 判断资源名称是否不重复（如果是修改，则还需要判断是否真的更改）
	 */
	boolean checkResNameIsNotExist(String resName);

	/**
	 * 判断资源Url是否不重复（如果是修改，则还需要判断是否真的更改）
	 */
	boolean checkResUrlIsNotExist(String resUrl);
}