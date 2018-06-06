package com.school.service.base.impl;

import com.school.util.core.ConstantCore;
import com.school.util.core.exception.FuServiceException;
import com.school.util.core.pager.PageInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;


@Slf4j
public class EasyBaseServiceImpl {
	
	
	/**
	 * @Description: 分页排序构建方法 
	 * @param paramMap 查询条件
	 * @param orderMap 排序条件 
	 */
	/*protected void buildPageSort(Map<String,Object> paramMap,Map<String,FuSort> orderMap) {
		
		List<String[]> sortList = Lists.newArrayList();
		
		if(orderMap != null) {
			
			for(Entry<String,FuSort> entry : orderMap.entrySet()){
				
				String[] sortMsgArr = new String[2];
				
				sortMsgArr[0] = CamelAndUnderlineUtil.camelToUnderline(entry.getKey());
				sortMsgArr[1] = entry.getValue().name();
				
				sortList.add(sortMsgArr);
			}
			
			//排序条件
			paramMap.put("sortList", sortList);
		}
	}*/
	
	protected void buildPageSort(Map<String,Object> paramMap,String strSort) {
		
		//排序条件
		paramMap.put("sortString", strSort);
	}
	
	/**
	 * @Description: 分页排序构建方法 
	 * @param paramMap 查询条件
	 */
	protected void buildPageSort(Map<String,Object> paramMap) throws FuServiceException {
		
		if(paramMap.get("page") == null) {
			throw FuServiceException.createErr(5200);
		} 
		
		PageInfo pageInfo = (PageInfo)paramMap.get(ConstantCore.EASY_PAGE);
		
		buildPageSort(paramMap,pageInfo.getSort());
	}
	
	/**
	 * @Description: 将结果集封装到PageInfo对象
	 * 
	 * @param searchParams 查询条件Map,主要用于获取Page对象
	 * @param list 数据库查询结果集
	 * 
	 * @return PageInfo 对象
	 */
	protected PageInfo findPage(Map searchParams,List list) {
		
		PageInfo pageInfo = null;
		
		if(searchParams.get(ConstantCore.EASY_PAGE) == null) {
			
			pageInfo = new PageInfo();
			
		} else {
			
			pageInfo = (PageInfo)searchParams.get(ConstantCore.EASY_PAGE);
		} 
		
		pageInfo.setContent(list);
		
		return pageInfo;
	}
	
	
    /**
	 * @Description: FuServiceException
	 * @author fujie
	 */
	protected FuServiceException serviceExp(Exception e)  {
		
		 log.error("mybatis service error : {}",this.getClass().getName(),e);
	
		 return FuServiceException.createErr(5200);
	}
	
	
}
