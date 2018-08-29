package com.school.biz.dao.region;

import java.util.List;

import com.school.biz.dao.base.BaseDao;
import com.school.biz.domain.entity.region.Region;

/**
 * @author jame
 */
public interface RegionMapper extends BaseDao {

    List<Region> findAll();
}