package com.school.biz.dao.express;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.school.biz.dao.base.BaseDao;
import com.school.biz.domain.entity.express.ExpressReceive;

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

    List<Map> findPushOpenIdByExpressStatus(@Param("status") Integer status);

    int updateIneffectiveToCancel(@Param("cancel") Integer cancel, @Param("ineffective") Integer ineffective,
                                  @Param("diff") Integer diff);

    /**
     * 累加服务费
     *
     */
    void addServiceAmt(@Param("amount") BigDecimal amount, @Param("id") Long id);

    /**
     * 累加补单服务费
     *
     */
    void addReOrderServiceAmt(@Param("amount") BigDecimal amount, @Param("id") Long id);

    long count(@Param("codes") List<String> codes);

    List<ExpressReceive> list(@Param("codes") List<String> codes);

    int batchInsert(List<ExpressReceive> expressReceives);
}