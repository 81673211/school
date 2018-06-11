package com.school.web.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.school.web.base.BaseEasyWebController;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 11/06/2018 17:22
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseEasyWebController {

    @RequestMapping("/subscribe")
    public ModelAndView subscribe() {
        return null;
    }
}
