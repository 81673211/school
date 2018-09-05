package com.school.biz.component;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.school.biz.domain.entity.customer.Customer;
import com.school.biz.service.customer.CustomerService;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/9/5 21:16
 */
@Component
public class ExpressLogOperatorHelper {

    @Autowired
    private CustomerService customerService;

    public String getCustomerOperatorName(Long customerId) {
        String customerOperatorName = ":unknown@c";
        Customer customer = customerService.get(customerId);
        if (customer != null) {
            String name = customer.getName();
            if (StringUtils.isNotBlank(name)) {
                customerOperatorName = customerOperatorName.replace(":unknown", name);
            }
        }
        return customerOperatorName;
    }
}
