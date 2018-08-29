package com.school.biz.service.customer.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.biz.constant.RedisKeyNS;
import com.school.biz.dao.customer.CustomerMapper;
import com.school.biz.domain.entity.customer.Customer;
import com.school.biz.domain.vo.CustomerProfileEditVo;
import com.school.biz.service.base.impl.BaseServiceImpl;
import com.school.biz.service.customer.CustomerService;
import com.school.biz.service.express.ExpressReceiveService;
import com.school.biz.service.message.SmsService;
import com.school.biz.util.VerifyCodeUtil;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 12/06/2018 15:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class CustomerServiceImpl extends BaseServiceImpl<Customer, CustomerMapper> implements CustomerService {

    public static final int EXPIRE_TIME = 5;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private SmsService smsService;

    @Autowired
    private ExpressReceiveService expressReceiveService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<Customer> queryPage(Map<String, Object> paramMap) {
        return customerMapper.queryPage(paramMap);
    }

    @Override
    public void subscribe(String openId) {
        Customer customer = customerMapper.selectByOpenId(openId);
        if (customer != null) {
            if (!customer.isSubscribed()) {
                customer.setSubscribed(true);
                customer.setSubscribedTime(new Date());
                customerMapper.updateByPrimaryKey(customer);
            }
        } else {
            create(openId);
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
        if (StringUtils.isBlank(phone)) {
            throw new RuntimeException("手机号不能为空");
        }
        String cacheKey = RedisKeyNS.CACHE_CUSTOMER_PROFILE_VERIFY_CODE + phone;
        String verifyCode = redisTemplate.opsForValue().get(cacheKey);
        if (verifyCode != null) {
            log.info("verify_code:{}, phone:{}", verifyCode, phone);
            redisTemplate.opsForValue().set(cacheKey, verifyCode, EXPIRE_TIME, TimeUnit.MINUTES);
            smsService.sendVerifyCode(phone, verifyCode);
        } else {
            verifyCode = VerifyCodeUtil.obtain();
            log.info("verify_code:{}, phone:{}", verifyCode, phone);
            redisTemplate.opsForValue().set(cacheKey, verifyCode, EXPIRE_TIME, TimeUnit.MINUTES);
            smsService.sendVerifyCode(phone, verifyCode);
        }
    }

    @Override
    public void update(CustomerProfileEditVo request) {
        Customer customer = getByOpenId(request.getOpenId());
        checkVerifyCode(customer.getPhone(), request.getPhone(), request.getCode());
        String name = request.getName();
        if (StringUtils.isNotBlank(name)) {
            customer.setName(name);
        }
        String email = request.getEmail();
        if (StringUtils.isNotBlank(email)) {
            customer.setEmail(email);
        }
        String addr = request.getAddr();
        if (StringUtils.isNotBlank(addr)) {
            customer.setAddr(addr);
        }
        String requestPhone = request.getPhone();
        boolean bindExpress = false;
        if (StringUtils.isNotBlank(requestPhone)) {
            if (StringUtils.isBlank(customer.getPhone()) && StringUtils.isNotBlank(requestPhone)) {
                //绑定收件
                bindExpress = true;
            }
            customer.setPhone(requestPhone);
        }
        update(customer);
        if (bindExpress) {
            expressReceiveService.bindCustomerByPhone(requestPhone, customer.getId());
        }
    }

    private void checkVerifyCode(String phone, String reqPhone, String reqVerifyCode) {
        if (StringUtils.isBlank(phone)) {
            if (StringUtils.isBlank(reqPhone)) {
                throw new RuntimeException("手机号不能为空");
            }
            if (!Pattern.matches("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$",
                                 reqPhone)) {
                throw new RuntimeException("手机号格式错误");
            }
            if (StringUtils.isBlank(reqVerifyCode)) {
                throw new RuntimeException("验证码不能为空");
            }
            String cacheKey = RedisKeyNS.CACHE_CUSTOMER_PROFILE_VERIFY_CODE + reqPhone;
            String verifyCode = redisTemplate.opsForValue().get(cacheKey);
            if (verifyCode == null) {
                throw new RuntimeException("无效的验证码");
            }
            if (!reqVerifyCode.equals(verifyCode)) {
                throw new RuntimeException("验证码错误");
            } else {
                redisTemplate.delete(cacheKey);
            }
        }
    }

    @Override
    public Customer create(String openId) {
        Customer customer = new Customer();
        customer.setOpenId(openId);
        customer.setSubscribed(true);
        customer.setSubscribedTime(new Date());
        customerMapper.insert(customer);
        return getByOpenId(openId);
    }

    @Override
    public Customer getByPhone(String phone) {
        return customerMapper.selectByPhone(phone);
    }

    @Override
    public void saveOrUpdate(Customer customer) {
        if (customer.getId() == null) {
            this.save(customer);
        } else {
            this.update(customer);
        }
    }
}
