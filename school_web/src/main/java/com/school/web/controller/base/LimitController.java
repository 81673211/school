package com.school.web.controller.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author jame
 * @date 2018/8/30
 * @desc description
 */
@Controller
@RequestMapping("/")
public class LimitController {

    /**
     * 错误浏览器  页面跳转
     *
     * @return
     */
    @RequestMapping(value = "limit", method = RequestMethod.GET)
    public ModelAndView limit() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("limit");
        return modelAndView;
    }

}
