package com.school.biz.dao.customer;

import java.util.List;
import java.util.Map;

import com.school.biz.dao.base.BaseDao;
import com.school.biz.domain.entity.customer.Customer;
import org.apache.ibatis.annotations.Param;

public interface CustomerMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Long id);

    Customer selectByPhone(String phone);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    List<Customer> queryPage(Map<String, Object> paramMap);

    Customer selectByOpenId(@Param("openId") String openId);

    List<String> selectRegistered();
}