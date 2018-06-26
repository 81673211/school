package com.school.service.region.impl;

import com.school.dao.region.RegionMapper;
import com.school.domain.entity.region.Region;
import com.school.exception.RegionException;
import com.school.service.base.impl.BaseServiceImpl;
import com.school.service.region.RegionService;
import com.school.util.core.Log;
import com.school.vo.BaseVo;
import com.school.vo.response.RegionListResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jame
 */
@Service
public class RegionServiceImpl extends BaseServiceImpl<Region, RegionMapper> implements RegionService {

    @Autowired
    private RegionMapper regionMapper;

    @Override
    public List<BaseVo> selectRegionList(Long parentId) throws RegionException {
        List<BaseVo> result = new ArrayList<>();
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("parentId", parentId);
            List<Region> list = regionMapper.selectByParams(param);
            for (Region region : list) {
                result.add(converterPo2Vo(region, new RegionListResponseVo()));
            }
        } catch (Exception e) {
            String msg = "throw exception when get region list";
            Log.error.error(msg, e);
            throw new RegionException(msg, e);
        }
        return result;
    }
}
