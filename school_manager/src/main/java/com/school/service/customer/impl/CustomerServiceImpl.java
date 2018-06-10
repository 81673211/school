package com.school.service.customer.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.dao.customer.CustomerMapper;
import com.school.domain.entity.customer.Customer;
import com.school.service.base.impl.BaseServiceImpl;
import com.school.service.customer.CustomerService;

@Service
public class CustomerServiceImpl extends BaseServiceImpl<Customer, CustomerMapper> implements CustomerService {

	@Autowired
	private CustomerMapper customerMapper;
	
	@Override
	public List<Customer> queryPage(Map<String, Object> paramMap) {
		return customerMapper.queryPage(paramMap);
	}
	
	@Override
	public void saveOrUpdate(Customer customer) {
		if(customer.getId() == null){
			this.save(customer);
		}else{
			this.update(customer);
		}
	}

}
