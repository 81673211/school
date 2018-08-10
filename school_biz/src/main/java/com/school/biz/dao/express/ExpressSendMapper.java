package com.school.biz.dao.express;

import com.school.biz.dao.base.BaseDao;
import com.school.biz.domain.entity.express.ExpressSend;

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
    
    /**
     * 管理台列表查询
     */
    List<ExpressSend> queryForManagerPage(Map<String, Object> paramMap);
}