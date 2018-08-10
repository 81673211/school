package com.school.biz.service.base;

import com.school.biz.exception.FuServiceException;

/**
 * <p>Title: BaseService</p>
 * <p>Description: Service基础接口，定义单表的增删该查方法</p>
 * @param <M> 表对应的实体
 * @param <T> 表对应的mapper接口（xxMapper.xml中的命名空间）
 *
 * @author liliang
 * @date 2016年5月20日 上午10:58:40
 * 
 */
public interface BaseService<M,T> {
	
	/**
	 * @Description: 保存、更新
	 * @return M
	 */
    M save(M m) throws FuServiceException;
    /**
	 * @Description: 更新
	 * @return M
	 */
    M update(M m) throws FuServiceException;
    
	/**
	 * @Description: 单表实体查询
	 * @return 查询实体
	 */
    M get(Long id) throws FuServiceException;
    
	/**
	 * @Description: 单表实体删除
	 * @return void
	 */
    void deleteById(Long id) throws FuServiceException;
    
	
}
