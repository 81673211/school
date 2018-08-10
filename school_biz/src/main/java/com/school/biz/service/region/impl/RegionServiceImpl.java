package com.school.biz.service.region.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.biz.dao.region.RegionMapper;
import com.school.biz.domain.entity.region.Region;
import com.school.biz.service.region.RegionService;

/**
 * @author jame
 */
@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionMapper regionMapper;

    @Override
    public List<Region> selectRegionList(Long parentId) {
        Map<String, Object> param = new HashMap<>();
        param.put("parentId", parentId);
        return regionMapper.selectByParams(param);
    }
}
