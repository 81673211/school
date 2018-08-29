package com.school.web.component.init;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
public class RegionCache {

    @Autowired
    private RedisTemplate<String, BigDecimal> redisTemplate;
    @Autowired
    private RegionService regionService;

    @PostConstruct
    public void init() {
        redisTemplate.delete(RedisKeyNS.CACHE_SEND_EXPRESS_FEE);
        List<Region> regions = regionService.selectRegionList(0L);
        boolean empty = CollectionUtils.isEmpty(regions);
        log.info("cache all send_express_fees, region size:{}", empty ? 0 : regions.size());
        if (!empty) {
            for (Region region : regions) {
//                redisTemplate.opsForHash().putAll();
            }
        }
    }
}
