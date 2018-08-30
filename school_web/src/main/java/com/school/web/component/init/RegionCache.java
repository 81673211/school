package com.school.web.component.init;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.school.biz.constant.RedisKeyNS;
import com.school.biz.domain.entity.region.Region;
import com.school.biz.service.region.RegionService;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/8/29 20:31
 */
@Component
@Lazy(value = true)
@Slf4j
public class RegionCache {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RegionService regionService;

    @PostConstruct
    public void init() {
        List<Region> regions = regionService.findAll();
        long start = System.currentTimeMillis();
        cacheRegion(regions);
        log.info("region cost:{}", System.currentTimeMillis() - start);
        cacheFee(regions);
    }

    private void cacheRegion(List<Region> regions) {
        Set<String> keys = redisTemplate.keys(RedisKeyNS.CACHE_REGION_CHILDREN + "*");
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }


        List<Region> tempRegions = new ArrayList<>(regions);
        //缓存省
        List<String> provinces = new ArrayList<>();
        List<Long> provinceIds = new ArrayList<>();

        for (int i = tempRegions.size() - 1; i >= 0; i--) {
            Region region = tempRegions.get(i);
            if (region.getParentId() == 0) {
                provinces.add(JSON.toJSONString(region));
                provinceIds.add(region.getId());
                tempRegions.remove(i);
            }
        }
        String regionProvinceKey = RedisKeyNS.CACHE_REGION_CHILDREN + "0";
        redisTemplate.opsForList().rightPushAll(regionProvinceKey, provinces.toArray(new String[] {}));

        //缓存市
        List<String> cities = new ArrayList<>();
        List<Long> cityIds = new ArrayList<>();

        for (Long provinceId : provinceIds) {
            for (int i = tempRegions.size() - 1; i >= 0; i--) {
                Region region = tempRegions.get(i);
                if (region.getParentId().equals(provinceId)) {
                    cities.add(JSON.toJSONString(region));
                    cityIds.add(region.getId());
                    tempRegions.remove(i);
                }
            }
            String regionCityKey = RedisKeyNS.CACHE_REGION_CHILDREN + provinceId;
            redisTemplate.opsForList().rightPushAll(regionCityKey, cities.toArray(new String[] {}));
            cities = new ArrayList<>();
        }

        //缓存区县
        List<String> districts = new ArrayList<>();

        for (Long cityId : cityIds) {
            for (int i = tempRegions.size() - 1; i >= 0 ; i--) {
                Region region = tempRegions.get(i);
                if (region.getParentId().equals(cityId)) {
                    districts.add(JSON.toJSONString(region));
                    tempRegions.remove(i);
                }
            }
            String regionDistrictKey = RedisKeyNS.CACHE_REGION_CHILDREN + cityId;
            redisTemplate.opsForList().rightPushAll(regionDistrictKey, districts.toArray(new String[] {}));
            districts = new ArrayList<>();
        }
    }

    private void cacheFee(List<Region> regions) {
        redisTemplate.delete(RedisKeyNS.CACHE_SEND_EXPRESS_FEE);
        boolean empty = CollectionUtils.isEmpty(regions);
        log.info("cache all send_express_fees, region size:{}", empty ? 0 : regions.size());
        if (!empty) {
            Map<String, String> feeMap = new HashMap<>();
            for (Region region : regions) {
                BigDecimal sfFee = region.getSfFirFee();
                if (sfFee != null) {
                    feeMap.put(region.getId() + ":sf", sfFee.toString());
                    feeMap.put(region.getId() + ":other", region.getOtherFirFee().toString());
                }

            }
            redisTemplate.opsForHash().putAll(RedisKeyNS.CACHE_SEND_EXPRESS_FEE, feeMap);
            log.info("初始化运费缓存成功");
        }
    }
}
