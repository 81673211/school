package com.school.biz.dao.express;

import com.school.biz.dao.base.BaseDao;
import com.school.biz.domain.entity.express.ExpressReceive;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * @author jame
 */
public interface ExpressReceiveMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(ExpressReceive record);

    Long insertSelective(ExpressReceive record);

    ExpressReceive selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExpressReceive record);

    int updateByPrimaryKey(ExpressReceive record);

    int bindCustomerByPhone(@Param("phone") String phone, @Param("customerId") Long customerId);

    List<ExpressReceive> queryPage(Map<String, Object> paramMap);
    
    /**
     * 管理台列表
     */
    List<ExpressReceive> queryForManagerPage(Map<String, Object> paramMap);


}