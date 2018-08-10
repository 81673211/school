package com.school.manager.controller.express;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.school.biz.constant.ConstantUrl;
import com.school.biz.exception.FuBusinessException;
import com.school.biz.service.customer.CustomerService;
import com.school.biz.util.pager.PageInfo;
import com.school.manager.vo.AjaxResult;
import com.school.biz.domain.bo.wechat.Template;
import com.school.biz.domain.entity.customer.Customer;
import com.school.biz.domain.entity.express.ExpressCompany;
import com.school.biz.domain.entity.express.ExpressReceive;
import com.school.biz.enumeration.ReceiveExpressStatusEnum;
import com.school.biz.service.express.ExpressCompanyService;
import com.school.biz.service.express.ExpressReceiveService;
import com.school.biz.service.wechat.TemplateService;
import com.school.manager.controller.base.BaseEasyWebController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/express/expressReceive")
public class ExpressReceiveController extends BaseEasyWebController {

    @Autowired
    private ExpressReceiveService expressReceiveService;

    @Autowired
    private ExpressCompanyService expressCompanyService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TemplateService templateService;

    {
        listView = "express/expressReceive";

    }

    /**
     * 分页查询
     */
    @Override
    protected void onList(PageInfo pageInfo, Map<String, Object> searchParams, HttpServletRequest request,
                          ModelAndView mav) throws FuBusinessException {
        try {
            List<ExpressReceive> list = expressReceiveService.queryPage(searchParams);
            mav.addObject("listData", JSON.toJSON(list));
            mav.addObject("expressReceiveStatusMap", JSON.toJSON(ReceiveExpressStatusEnum.getAllStatusEnum()));
            mav.addObject(PAGE_PARAM_PAGECOUNT, pageInfo.getTotalPage());
            mav.addObject(PAGE_PARAM_TOTALCOUNT, pageInfo.getTotalRecord());

            mav.addObject(ConstantUrl.DETAIL_URL, ConstantUrl.EXPRESS_RECEIVE_DETAIL_URL);// 详情url
            mav.addObject(ConstantUrl.EDIT_URL, ConstantUrl.EXPRESS_RECEIVE_EDIT_URL);// 编辑url
            mav.addObject(ConstantUrl.DEL_URL, ConstantUrl.EXPRESS_RECEIVE_DEL_URL);// 删除url
        } catch (Exception e) {
            log.error("收件查询出现错误：" + e.getMessage());
            throw webExp(e);
        }
    }

    /**
     * 查看详情
     */
    @RequestMapping("/detail.do")
    public ModelAndView detail(Long id) {
        try {
            ModelAndView mv = new ModelAndView("express/expressReceiveDetail");
            ExpressReceive expressReceive = expressReceiveService.get(id);
            mv.addObject("expressReceive", JSON.toJSON(expressReceive));
            mv.addObject("expressReceiveStatusMap", JSON.toJSON(ReceiveExpressStatusEnum.getAllStatusEnum()));
            return mv;
        } catch (Exception e) {
            throw webExp(e);
        }
    }

    /**
     * 编辑页面
     */
    @RequestMapping(value = "/edit.do")
    public ModelAndView edit(Long id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("express/expressReceiveEdit");
        ExpressReceive expressReceive = expressReceiveService.get(id);
        mav.addObject("expressReceive", JSON.toJSON(expressReceive));
        mav.addObject("expressReceiveStatusMap", JSON.toJSON(ReceiveExpressStatusEnum.getAllStatusEnum()));
        // 查询所有快递公司
        List<ExpressCompany> expressCompanyList = expressCompanyService.findAll();
        mav.addObject("expressCompanyList", JSON.toJSON(expressCompanyList));
        return mav;
    }

    /**
     * 保存
     */
    @ResponseBody
    @RequestMapping("/save.do")
    public AjaxResult save(HttpServletRequest request, ExpressReceive expressReceive) {
        try {
            ExpressCompany expressCompany = expressCompanyService.findByCode(expressReceive.getCompanyCode());
            expressReceive.setCompanyId(expressCompany.getId());
            expressReceive.setCompanyName(expressCompany.getName());
            Customer customer = customerService.getByPhone(expressReceive.getReceiverPhone());
            if (customer != null) {
                expressReceive.setCustomerId(customer.getId());
            }
            expressReceiveService.saveOrUpdate(expressReceive);
            if (customer != null && StringUtils.isNotBlank(customer.getOpenId())) {
                Template template = new Template();
                template.setToUser(customer.getOpenId());
                template.setId("ugYEPmc1T91iN9VpGR8oR_IqN5Aowm7mulR8ERH9BgM");
                template.setFirst("您有一个新的快递已到代理点");
                template.setRemark("请到待收快件页面选择自提或配送，如有疑问请致电：66776677");
                List<String> keywords = new ArrayList<>();
                keywords.add(expressCompany.getName());
                keywords.add(expressReceive.getCode());
                keywords.add(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
                keywords.add("东门外侧");
                keywords.add(ReceiveExpressStatusEnum.PROXY_RECIEVED.getMessage());
                template.setKeywords(keywords);
                templateService.send(template);
            }
            return AjaxResult.success("保存成功", JSON.toJSON(expressReceive));
        } catch (Exception e) {
            log.error("保存收件出错：" + e.getMessage());
            return AjaxResult.fail("保存失败");
        }
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping("/del.do")
    public AjaxResult del(Long id) {
        try {
            expressReceiveService.deleteById(id);
            return AjaxResult.success("删除成功");
        } catch (Exception e) {
            log.error("删除收件出错：" + e.getMessage());
            return AjaxResult.fail("删除失败");
        }
    }
}
