package com.school.service.base.impl;


import com.school.service.base.BaseService;
import com.school.vo.request.BaseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>Title: BaseServiceImpl</p>
 * <p>Description: Service基础实现类，实现单表的增删该查</p>
 * @param <M> 表对应的实体
 * @param <T> 表对应的mapper接口（xxMapper.xml中的命名空间）
 * 
 * @author liliang
 * 
 */
@Slf4j
@SuppressWarnings("unchecked")
@Transactional(rollbackFor = Exception.class)
public class BaseServiceImpl<M,T> implements BaseService<M, T> {

	/**
	 * 将vo对象转换为可持久化po对象
	 *
	 * @param vo
	 * @return
	 */
	public M converterVo2Po(BaseVo vo, Class<M> po) throws IllegalAccessException, InstantiationException {
		M m = po.newInstance();
		BeanUtils.copyProperties(vo, m);
		return m;
	}



}
