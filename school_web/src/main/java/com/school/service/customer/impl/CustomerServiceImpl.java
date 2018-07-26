package com.school.service.customer.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.dao.customer.CustomerMapper;
import com.school.domain.entity.customer.Customer;
import com.school.service.customer.CustomerService;
import com.school.service.message.SmsService;
import com.school.web.customer.request.CustomerProfileEditRequest;

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
    public static final int EXPIRE_TIME = 5;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void subscribe(String openId) {
        LOGGER.info("openId:{}", openId);
        Customer customer = customerMapper.selectByOpenId(openId);
        LOGGER.info("customer is not null? {}", customer != null);
        if (customer != null) {
            if (!customer.isSubscribed()) {
                customer.setSubscribed(true);
                customer.setSubscribedTime(new Date());
                customerMapper.updateByPrimaryKey(customer);
            }
        } else {
            customer = new Customer();
            customer.setOpenId(openId);
            customer.setSubscribed(true);
            customer.setSubscribedTime(new Date());
            customerMapper.insert(customer);
        }
    }

    @Override
    public void unsubscribe(String openId) {
        Customer customer = customerMapper.selectByOpenId(openId);
        if (customer != null && customer.isSubscribed()) {
            customer.setSubscribed(false);
            customerMapper.updateByPrimaryKey(customer);
        }
    }

    @Override
    public Customer getByOpenId(String openId) {
        return customerMapper.selectByOpenId(openId);
    }

    @Override
    public void sendVerifyCode(String phone) {
        String verifyCode = redisTemplate.opsForValue().get(phone);
        if (verifyCode != null) {
            redisTemplate.opsForValue().set(phone, verifyCode, EXPIRE_TIME, TimeUnit.MINUTES);
            smsService.sendVerifyCode(phone, verifyCode);
        } else {
            verifyCode = "9999";
            redisTemplate.opsForValue().set(phone, verifyCode, EXPIRE_TIME, TimeUnit.MINUTES);
            smsService.sendVerifyCode(phone, verifyCode);
        }
    }

    @Override
    public void update(CustomerProfileEditRequest request) {
        Customer customer = getByOpenId(request.getOpenId());
        String phone = customer.getPhone();
        redisTemplate.opsForValue().get(phone);
        try {
            BeanUtils.copyProperties(customer, request);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        customerMapper.updateByPrimaryKey(customer);
    }
}
