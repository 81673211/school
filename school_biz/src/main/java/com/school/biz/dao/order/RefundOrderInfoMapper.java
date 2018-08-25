package com.school.biz.dao.order;

import java.util.List;

import com.school.biz.domain.entity.order.RefundOrderInfo;

public interface RefundOrderInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RefundOrderInfo record);

    int insertSelective(RefundOrderInfo record);

    RefundOrderInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RefundOrderInfo record);

    int updateByPrimaryKey(RefundOrderInfo record);

	List<RefundOrderInfo> getNotRefundOrder();
}