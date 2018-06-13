package com.school.service.customer.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.dao.customer.CustomerMapper;
import com.school.domain.entity.customer.Customer;
import com.school.service.customer.CustomerService;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 12/06/2018 15:50
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public void subscribe(String openId) {
        LOGGER.info("openId:{}", openId);
        Customer customer = customerMapper.selectByOpenId(openId);
        if (customer != null) {
            if (!customer.isSubscribe()) {
                customer.setSubscribe(true);
                customerMapper.updateByPrimaryKey(customer);
            }
        } else {
            customer = new Customer();
            customer.setOpenId(openId);
            customerMapper.insert(customer);
        }
    }
}
