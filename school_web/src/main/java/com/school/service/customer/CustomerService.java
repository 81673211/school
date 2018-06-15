package com.school.service.customer;

import com.school.dao.customer.CustomerMapper;
import com.school.domain.entity.customer.Customer;
import com.school.service.base.BaseService;

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

    Customer getByOpenId(String openId);
}
