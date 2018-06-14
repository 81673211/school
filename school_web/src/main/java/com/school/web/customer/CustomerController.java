package com.school.web.customer;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @RequestMapping("/profile/edit")
    public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("--------------------------customerController--------edit----");
        response.sendRedirect("/index.html");
    }


}
