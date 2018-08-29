package com.school.biz.service.region;

import java.util.List;

import com.school.biz.domain.entity.region.Region;

/**
 * @author jame
 */
public interface RegionService {

    /**
     * 通过parentId查询区域列表映射，parentId==null则查询parentId=0
     *
     * @param parentId
     * @return
     */
    List<Region> selectRegionList(Long parentId);

    List<Region> findAll();
}
