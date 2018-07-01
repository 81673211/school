package com.school.dao.order;

import java.util.List;
import java.util.Map;

import com.school.dao.base.BaseDao;
import com.school.domain.entity.order.OrderInfo;

public interface OrderInfoMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);
    
    List<OrderInfo> queryPage(Map<String, Object> paramMap);
    
    OrderInfo findByOrderNo(String orderNo);
}