package com.school.cache;

import java.util.List;

import com.school.domain.dto.good.CacheGoodTypeDto;


/**
 * 公共服务封装
 * 
 * @version 1.0
 */
public interface CommonService {
	
	/**
	 * 缓存商品类型
	 * @throws Exception 
	 */
	public void cacheGoodType(Long id) throws Exception;
	
	public CacheGoodTypeDto loadGoodType(Long id);
	
	public void cacheAllGoodTypes();
	
	public List<CacheGoodTypeDto> loadCacheAllGoodTypes();
}
