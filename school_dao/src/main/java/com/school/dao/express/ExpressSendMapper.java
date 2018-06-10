package com.school.dao.express;

import com.school.dao.base.BaseDao;
import com.school.domain.entity.express.ExpressSend;

import java.util.List;
import java.util.Map;

/**
 * @author jame
 */
public interface ExpressSendMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(ExpressSend record);

    Long insertSelective(ExpressSend record);

    ExpressSend selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExpressSend record);

    int updateByPrimaryKey(ExpressSend record);

    List<ExpressSend> queryPage(Map<String, Object> paramMap);
}