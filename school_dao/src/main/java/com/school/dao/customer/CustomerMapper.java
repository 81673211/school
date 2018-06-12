package com.school.dao.customer;

import java.util.List;
import java.util.Map;

import com.school.dao.base.BaseDao;
import com.school.domain.entity.customer.Customer;
import com.school.domain.entity.order.OrderInfo;

public interface CustomerMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);
    
    List<Customer> queryPage(Map<String, Object> paramMap);


    Customer selectByOpenId(String openId);
}