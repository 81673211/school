package com.school.web.component.init;

import java.math.BigDecimal;
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
@Lazy(value = false)
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
        flushRegion();
        cacheFee(regions);
        log.info("region cost:{}", System.currentTimeMillis() - start);
    }


    private void flushRegion() {
        Set<String> keys = redisTemplate.keys(RedisKeyNS.CACHE_REGION_CHILDREN + "*");
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
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
                    feeMap.put(region.getId() + ":other", region.getOtherFirFee().toString());
                    feeMap.put(region.getId() + ":sf", sfFee.toString());
                    feeMap.put(region.getId() + ":ys", region.getYsFirFee().toString());
                    feeMap.put(region.getId() + ":zt", region.getZtFirFee().toString());
                    feeMap.put(region.getId() + ":yd", region.getYdFirFee().toString());
                    feeMap.put(region.getId() + ":bs", region.getBsFirFee().toString());
                }

            }
            redisTemplate.opsForHash().putAll(RedisKeyNS.CACHE_SEND_EXPRESS_FEE, feeMap);
            log.info("初始化运费缓存成功");
        }
    }
}
