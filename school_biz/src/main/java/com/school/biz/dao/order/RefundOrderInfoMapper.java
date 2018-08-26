package com.school.biz.dao.order;

import java.util.List;
import java.util.Map;

import com.school.biz.dao.base.BaseDao;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.domain.entity.order.RefundOrderInfo;

public interface RefundOrderInfoMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(RefundOrderInfo record);

    int insertSelective(RefundOrderInfo record);

    RefundOrderInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RefundOrderInfo record);

    int updateByPrimaryKey(RefundOrderInfo record);

	List<RefundOrderInfo> getNotRefundOrder();

	List<RefundOrderInfo> findSuccessRefundOrdersByExpressNo(String expressCode);

	List<OrderInfo> queryPage(Map<String, Object> paramMap);

	void fillExpressNo(Map<String, Object> map);
}