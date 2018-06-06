package com.school.service.base;

import com.school.util.core.exception.FuServiceException;

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
	 * @param JPA实体对象   
	 * @return M  
	 * @throws 数据库操作异常
	 */
    M save(M m) throws FuServiceException;
    /**
	 * @Description: 更新
	 * @param 实体对象   
	 * @return M  
	 * @throws 数据库操作异常
	 */
    M update(M m) throws FuServiceException;
    
	/**
	 * @Description: 单表实体查询
	 * @param 主键Id   
	 * @return 查询实体  
	 * @throws 数据库操作异常
	 */
    M get(Long id) throws FuServiceException;
    
	/**
	 * @Description: 单表实体删除
	 * @param 主键Id   
	 * @return void  
	 * @throws 数据库操作异常
	 */
    void deleteById(Long id) throws FuServiceException;
    
	
}
