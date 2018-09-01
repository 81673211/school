package com.school.biz.service.region.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.school.biz.constant.RedisKeyNS;
import com.school.biz.dao.region.RegionMapper;
import com.school.biz.domain.entity.region.Region;
import com.school.biz.service.region.RegionService;

/**
 * @author jame
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionMapper regionMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<Region> selectRegionList(Long parentId) {
        Long tempParentId = parentId == null ? 0 : parentId;
        List<Region> result = new ArrayList<>();
        String cacheParentId = RedisKeyNS.CACHE_REGION_CHILDREN + tempParentId;
        List<String> children = redisTemplate.opsForList().range(cacheParentId, 0, -1);
        if (CollectionUtils.isEmpty(children)) {
            Map<String, Object> param = new HashMap<>();
            param.put("parentId", tempParentId);
            List<Region> list = (List<Region>) regionMapper.selectByParams(param);
            if (!CollectionUtils.isEmpty(list)) {
                List<String> jsonVals = new ArrayList<>();
                for (Region region : list) {
                    jsonVals.add(JSON.toJSONString(region));
                }
                redisTemplate.opsForList().leftPushAll(cacheParentId, jsonVals.toArray(new String[] {}));
                result = list;
            }
        } else {
            for (String child : children) {
                Region region = JSON.parseObject(child, Region.class);
                result.add(region);
            }
        }
        return result;
    }

    @Override
    public List<Region> findAll() {
        return regionMapper.findAll();
    }

    @Override
    public Region get(Long id) {
        Region result;
        String cacheKey = RedisKeyNS.CACHE_REGION_ID + id;
        String cacheVal = redisTemplate.opsForValue().get(cacheKey);
        if (StringUtils.isBlank(cacheVal)) {
            Region region = (Region) regionMapper.selectByPrimaryKey(id);
            redisTemplate.opsForValue().set(cacheKey, JSON.toJSONString(region));
            result = region;
        } else {
            result = JSON.parseObject(cacheVal, Region.class);
        }
        return result;
    }
}
