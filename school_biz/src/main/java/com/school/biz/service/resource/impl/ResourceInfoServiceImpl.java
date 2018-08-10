package com.school.biz.service.resource.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.biz.dao.resource.ResourceInfoMapper;
import com.school.biz.domain.entity.resource.ResourceInfo;
import com.school.biz.domain.entity.resource.ResourceInfoVo;
import com.school.biz.service.base.impl.BaseServiceImpl;
import com.school.biz.service.resource.ResourceInfoService;

@Service
public class ResourceInfoServiceImpl extends BaseServiceImpl<ResourceInfo, ResourceInfoMapper> implements ResourceInfoService {

	@Autowired
	private ResourceInfoMapper resourceInfoMapper;
	
	@Override
	public List<ResourceInfoVo> queryPage(Map<String, Object> paramMap) {
		return resourceInfoMapper.queryPage(paramMap);
	}
	
	@Override
	public void saveOrUpdate(ResourceInfo resourceInfo) {
		if(resourceInfo.getId() == null){
			this.save(resourceInfo);
		}else{
			this.update(resourceInfo);
		}
	}

	@Override
	public ResourceInfoVo detail(Long id) {
		return resourceInfoMapper.detail(id);
	}

	@Override
	public List<ResourceInfo> getAllParentResources() {
		return resourceInfoMapper.getAllParentResources();
	}

	@Override
	public List<ResourceInfo> getOwnResources(Long userId) {
		return resourceInfoMapper.getOwnResources(userId);
	}

	@Override
	public boolean checkResNameIsNotExist(Long resId, String resName) {
		if(resId != null){
			// 如果是修改，但是没修改登录账号，则直接返回false
			ResourceInfo resourceInfo = resourceInfoMapper.selectByPrimaryKey(resId);
			if(resourceInfo != null && resName.equals(resourceInfo.getResName())){
				return true;
			}
		}
		return resourceInfoMapper.checkResNameIsNotExist(resName);
	}

	@Override
	public boolean checkResUrlIsNotExist(Long resId, String resUrl) {
		if(resId != null){
			// 如果是修改，但是没修改登录账号，则直接返回false
			ResourceInfo resourceInfo = resourceInfoMapper.selectByPrimaryKey(resId);
			if(resourceInfo != null && resUrl.equals(resourceInfo.getResUrl())){
				return true;
			}
		}
		return resourceInfoMapper.checkResUrlIsNotExist(resUrl);
	}

}
