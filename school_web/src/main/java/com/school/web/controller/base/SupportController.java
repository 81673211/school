package com.school.web.controller.base;

import com.school.biz.service.express.ExpressService;
import com.school.web.vo.response.DataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author jame
 * @date 2018/8/30
 * @desc description
 */
@Controller
@RequestMapping("/")
@Slf4j
public class SupportController {

    @Autowired
    private ExpressService expressService;

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


    /**
     * 获取推送消息数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "push/data", method = RequestMethod.GET)
    public DataResponse pushMessageData() {
        DataResponse<List> response = new DataResponse<>();
        try {
            List list = expressService.findPushMessageData();
            response.setData(list);
            return response.writeSuccess("获取推送消息数据成功", list);
        } catch (Exception e) {
            log.error("获取推送消息数据失败", e);
            return response.writeFailure("获取推送消息数据失败");
        }
    }

}
