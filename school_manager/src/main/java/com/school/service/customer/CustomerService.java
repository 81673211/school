package com.school.service.customer;

import java.util.List;
import java.util.Map;

import com.school.dao.customer.CustomerMapper;
import com.school.domain.entity.customer.Customer;
import com.school.service.base.BaseService;

public interface CustomerService extends BaseService<Customer, CustomerMapper> {

	List<Customer> queryPage(Map<String,Object> paramMap);

	void saveOrUpdate(Customer customer);

	Customer getByPhone(String phone);

}
