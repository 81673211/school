package com.school.dao.express;

import com.school.dao.base.BaseDao;
import com.school.domain.entity.express.ExpressReceive;

import java.util.List;
import java.util.Map;

/**
 * @author jame
 */
public interface ExpressReceiveMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(ExpressReceive record);

    int insertSelective(ExpressReceive record);

    ExpressReceive selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExpressReceive record);

    int updateByPrimaryKey(ExpressReceive record);

    List<ExpressReceive> queryPage(Map<String, Object> paramMap);
}