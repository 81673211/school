package com.school.biz.service.base.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.school.biz.dao.base.BaseDao;
import com.school.biz.exception.FuServiceException;
import com.school.biz.service.base.BaseService;
import com.school.biz.util.GenericsUtils;

import lombok.extern.slf4j.Slf4j;



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
public class BaseServiceImpl<M,T> implements BaseService<M, T>{
	
	@Autowired
	private SqlSessionTemplate sessionTemplate;
	/**
	 * @Description: 保存
	 * @return void
	 */
    public M save(M m) throws FuServiceException {
    	try {

			BaseDao daoImpl = (BaseDao) sessionTemplate.getMapper(GenericsUtils.getSuperClassGenricType(getClass(), 1));
    		daoImpl.insertSelective(m);
    		return m;
		} catch (Exception e) {
			throw serviceExp(e);
		}
    	
    }
    
    /**
	 * @Description: 单表实体更新
	 * @return 更新实体
	 * @throws FuServiceException
	 */
    public M update(M m) throws FuServiceException{
    	try {
			
    		BaseDao daoImpl = (BaseDao) sessionTemplate.getMapper(GenericsUtils.getSuperClassGenricType(getClass(), 1));
    		daoImpl.updateByPrimaryKeySelective(m);
    		return m;
		} catch (Exception e) {
			throw serviceExp(e);
		}
    }
    
	/**
	 * @Description: 单表实体查询
	 * @return 查询实体
	 * @throws FuServiceException
	 */
    public M get(Long id) throws FuServiceException {
    	try {
			
    		BaseDao daoImpl = (BaseDao) sessionTemplate.getMapper(GenericsUtils.getSuperClassGenricType(getClass(), 1));
    		return (M) daoImpl.selectByPrimaryKey(id);
    		
		} catch (Exception e) {
			throw serviceExp(e);
		}
    }
    
	/**
	 * @Description: 单表实体删除
	 * @param id 主键id
	 * @return void  
	 * @throws FuServiceException
	 */
    public void deleteById(Long id) throws FuServiceException {
    	try {
			
    		BaseDao daoImpl = (BaseDao) sessionTemplate.getMapper(GenericsUtils.getSuperClassGenricType(getClass(), 1));
    		daoImpl.deleteByPrimaryKey(id);
    		
		} catch (Exception e) {
			throw serviceExp(e);
		}
    }
    
    protected FuServiceException serviceExp(Exception e)  {
		
		 log.error("mybatis service error : {}",this.getClass().getName(),e);
	
		 return FuServiceException.createErr(910000);
	}


}
