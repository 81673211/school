package com.school.biz.dao.express;

import com.school.biz.dao.base.BaseDao;
import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.domain.vo.express.ExpressSendVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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
    List<ExpressSendVo> queryForManagerPage(Map<String, Object> paramMap);

    /**
     * 通过寄件单号查询寄件
     */
    ExpressSend findByExpressSendNo(String expressSendNo);

    List<Map> findPushOpenIdByExpressStatus(@Param("status") Integer status);

    int updateIneffectiveToCancel(@Param("cancel") Integer cancel, @Param("ineffective") Integer ineffective, @Param("diff") Integer diff);

    /**
     * 累加服务费
     *
     * @param amount
     * @param id
     */
    void addServiceAmt(BigDecimal amount, Long id);
}