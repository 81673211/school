package com.school.biz.service.customer;

import java.util.List;
import java.util.Map;

import com.school.biz.dao.customer.CustomerMapper;
import com.school.biz.domain.entity.customer.Customer;
import com.school.biz.domain.vo.CustomerProfileEditVo;
import com.school.biz.service.base.BaseService;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 12/06/2018 14:13
 */
public interface CustomerService extends BaseService<Customer, CustomerMapper> {

    /**
     * 客户关注公众号时为客户注册.
     */
    void subscribe(String openId);

    void unsubscribe(String openId);

    Customer getByOpenId(String openId);

    void sendVerifyCode(String phone, String openId);

    void update(CustomerProfileEditVo request);

    Customer create(String openId);

    //------ from manager ------

    List<Customer> queryPage(Map<String,Object> paramMap);

    void saveOrUpdate(Customer customer);

    Customer getByPhone(String phone);
}
