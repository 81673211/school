package com.school.cache.impl;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.cache.CacheKeyConstants;
import com.school.cache.CommonService;
import com.school.cache.RedisClientTemplate;
import com.school.dao.good.GoodTypeMapper;
import com.school.domain.dto.good.CacheGoodTypeDto;
import com.school.domain.entity.good.GoodType;
import com.school.util.core.utils.MD5Util;
import com.school.util.core.utils.ReflectUtil;
import com.school.util.core.utils.SerializeUtil;

@Slf4j
@Service
@Transactional
public class CommonServiceImpl implements CommonService {
    @Resource
    private RedisClientTemplate redisClientTemplate;
    
    @Resource
    private GoodTypeMapper goodTypeMapper;

    @Autowired
    private Environment env;
    
	/**环境变量*/
	private String profile(){
		return env.getProperty("spring.profiles.active"); 
	}
	
    /**
     * 系统配置的缓存key
     * @return
     */
    private String sysKey(){
        String profile = env.getProperty("spring.profiles.active");
        String key = MD5Util.getMD5("%4_SYSINFO*CCINFOKEY_09_p" + profile);
        return key;
    }

	@Override
	public void cacheGoodType(Long id) {
		GoodType goodType = goodTypeMapper.selectByPrimaryKey(id);
		CacheGoodTypeDto dto = new CacheGoodTypeDto();
		try {
			ReflectUtil.copy(goodType, dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		redisClientTemplate.hset(CacheKeyConstants.schoolGOODTYPEKEY.getBytes(), getGoodTypeBytes(id),
                SerializeUtil.serialize(dto));
	}

	@Override
	public CacheGoodTypeDto loadGoodType(Long id) {
		if(redisClientTemplate.exists(getGoodTypeBytes(id).toString())){
			return (CacheGoodTypeDto) redisClientTemplate.hget(CacheKeyConstants.schoolGOODTYPEKEY.getBytes(),getGoodTypeBytes(id));
		}
		cacheGoodType(id);
		return (CacheGoodTypeDto) redisClientTemplate.hget(CacheKeyConstants.schoolGOODTYPEKEY.getBytes(),getGoodTypeBytes(id));
	}
	
	private byte[] getGoodTypeBytes(Long id){
		return ("goodType" + id.toString()).getBytes();
	}

	@Override
	public void cacheAllGoodTypes() {
		
	}

	@Override
	public List<CacheGoodTypeDto> loadCacheAllGoodTypes() {
		return null;
	}

    
}
