package com.school.service.region;

import com.school.domain.entity.region.Region;
import com.school.exception.RegionException;
import com.school.service.base.BaseService;
import com.school.vo.BaseVo;

import java.util.List;

/**
 * @author jame
 */
public interface RegionService extends BaseService<Region, com.school.dao.region.RegionMapper> {

    /**
     * 通过parentId查询区域列表映射，parentId==null则查询parentId=0
     *
     * @param parentId
     * @return
     * @throws RegionException
     */
    List<BaseVo> selectRegionList(Long parentId) throws RegionException;
}
