package com.school.manager.controller.express;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.school.biz.constant.ConstantUrl;
import com.school.biz.domain.entity.customer.Customer;
import com.school.biz.domain.entity.express.ExpressCompany;
import com.school.biz.domain.entity.express.ExpressReceive;
import com.school.biz.enumeration.ExpressLogActionEnum;
import com.school.biz.enumeration.ExpressTypeEnum;
import com.school.biz.enumeration.HelpDistributionTypeEnum;
import com.school.biz.enumeration.ReceiveExpressDistributionTypeEnum;
import com.school.biz.enumeration.ReceiveExpressStatusEnum;
import com.school.biz.enumeration.ReceiveExpressTypeEnum;
import com.school.biz.exception.FuBusinessException;
import com.school.biz.service.customer.CustomerService;
import com.school.biz.service.express.ExpressCompanyService;
import com.school.biz.service.express.ExpressReceiveService;
import com.school.biz.service.log.ExpressLogService;
import com.school.biz.service.order.OrderInfoService;
import com.school.biz.util.pager.PageInfo;
import com.school.manager.controller.base.BaseEasyWebController;
import com.school.manager.util.SessionUtils;
import com.school.manager.vo.AjaxResult;

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
    private ExpressLogService expressLogService;
    
    @Autowired
    private OrderInfoService orderInfoService;

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
            mav.addObject("listData", JSON.toJSON(expressReceiveService.queryPage(searchParams)));
            mav.addObject("expressReceiveStatusMap", JSON.toJSON(ReceiveExpressStatusEnum.getAllStatusEnum()));
            mav.addObject("expressTypeMap", JSON.toJSON(ReceiveExpressTypeEnum.getAllTypeEnum()));
            mav.addObject("helpDistributionTypeMap", JSON.toJSON(HelpDistributionTypeEnum.getAllTypeEnum()));
            mav.addObject("expressWayMap", JSON.toJSON(ReceiveExpressDistributionTypeEnum.getAllTypeEnum()));

            List<ExpressCompany> companies = expressCompanyService.findAllCooperate();
            Map<String, Long> companyMap = new HashMap<>();
            for (ExpressCompany company : companies) {
                companyMap.put(company.getName(), company.getId());
            }
            mav.addObject("companyMap", JSON.toJSON(companyMap));
            mav.addObject(PAGE_PARAM_PAGECOUNT, pageInfo.getTotalPage());
            mav.addObject(PAGE_PARAM_TOTALCOUNT, pageInfo.getTotalRecord());

            mav.addObject(ConstantUrl.DETAIL_URL, ConstantUrl.EXPRESS_RECEIVE_DETAIL_URL);// 详情url
            mav.addObject(ConstantUrl.REFUND_URL, ConstantUrl.EXPRESS_RECEIVE_REFUND_URL);// 退款url
            mav.addObject(ConstantUrl.DETAIL_URL, ConstantUrl.EXPRESS_RECEIVE_DETAIL_URL);// 详情url
            mav.addObject(ConstantUrl.EDIT_URL, ConstantUrl.EXPRESS_RECEIVE_EDIT_URL);// 编辑url
            mav.addObject(ConstantUrl.DEL_URL, ConstantUrl.EXPRESS_RECEIVE_DEL_URL);// 删除url
            mav.addObject(ConstantUrl.RE_ORDER_URL, ConstantUrl.EXPRESS_RECEIVE_REORDER_URL);// 补单url
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
        List<ExpressCompany> expressCompanyList = expressCompanyService.findAllCooperate();
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
            log.error("expressReceiveSave:{}", JSON.toJSONString(expressReceive));
            ExpressCompany expressCompany = expressCompanyService.findByCode(expressReceive.getCompanyCode());
            expressReceive.setCompanyId(expressCompany.getId());
            expressReceive.setCompanyName(expressCompany.getName());
            Customer customer = customerService.getByPhone(expressReceive.getReceiverPhone());
            if (customer == null && expressReceive.getCustomerId() != null) {
                customer = customerService.get(expressReceive.getCustomerId());
            }
            if (customer != null) {
                expressReceive.setCustomerId(customer.getId());
                // 如果未填写收件人姓名、地址，则自动补全
                if (StringUtils.isBlank(expressReceive.getReceiverName())) {
                    expressReceive.setReceiverName(customer.getName());
                }
                if (StringUtils.isBlank(expressReceive.getReceiverAddr())) {
                    expressReceive.setReceiverAddr(customer.getAddr());
                }
            }
            expressReceiveService.saveOrUpdate(expressReceive, customer, SessionUtils.getSessionUser(request));
            return AjaxResult.success("保存成功", JSON.toJSON(expressReceive));
        } catch (Exception e) {
            log.error("保存收件出错：" + e.getMessage());
            return AjaxResult.fail("保存失败");
        }
    }

    @RequestMapping(value = "/supplement.do", method = RequestMethod.GET)
    public ModelAndView supplement(Long id) {
        ModelAndView view = new ModelAndView("express/receiveSupplement");
        view.addObject("expressReceive", expressReceiveService.getReceiveExpress(id));
        return view;
    }


    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping("/del.do")
    public AjaxResult del(Long id, HttpServletRequest request) {
        try {
            expressLogService.log(expressReceiveService.getReceiveExpress(id), ExpressLogActionEnum.RECEIVE_EXPRESS_DEL,
                                  SessionUtils.getSessionUser(request));
            expressReceiveService.deleteById(id);
            return AjaxResult.success("删除成功");
        } catch (Exception e) {
            log.error("删除收件出错：" + e.getMessage());
            return AjaxResult.fail("删除失败");
        }
    }
    
    /**
     * 补单页面
     */
    @RequestMapping(value = "/reOrder.do", method = RequestMethod.GET)
    public ModelAndView toReorder(Long id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        ExpressReceive expressReceive = expressReceiveService.get(id);

        mav.setViewName("express/expressReceiveReOrder");
        mav.addObject("expressReceive", expressReceive);
        return mav;
    }

    /**
     * 补单
     */
    @ResponseBody
    @RequestMapping(value = "/reOrder.do", method = RequestMethod.POST)
    public Object reOrder(HttpServletRequest request, Long expressReceiveId, BigDecimal reOrderServiceAmt) {
        try {
            if (expressReceiveId == null) {
                throw new Exception("快递ID不能为空");
            }
            if (reOrderServiceAmt == null || !(reOrderServiceAmt.compareTo(new BigDecimal(0)) > 0)) {
                throw new Exception("补单金额不正确");
            }

            orderInfoService.expressReceiveReOrder(request, expressReceiveId, reOrderServiceAmt);

            return AjaxResult.success("创建收件补单成功");
        } catch (Exception e) {
            log.error("创建收件补单失败：" + e.getMessage());
            return AjaxResult.fail(e.getMessage());
        }
    }



    /**
     * 退款页面
     */
    @RequestMapping(value = "/refund.do", method = RequestMethod.GET)
    public ModelAndView toRefund(Long id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("express/expressReceiveRefund");
        mav.addObject("expressReceive", expressReceiveService.get(id));
        return mav;
    }

    /**
     * 退款申请
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/refund.do", method = RequestMethod.POST)
    public Object refund(HttpServletRequest request, Long expressReceiveId, BigDecimal refundAmt) {
        try {
            if (expressReceiveId == null) {
                throw new Exception("快递ID不能为空");
            }
            if (refundAmt == null || refundAmt.compareTo(new BigDecimal(0)) <= 0) {
                throw new Exception("退款金额不正确");
            }

            orderInfoService.refund(request, expressReceiveId, ExpressTypeEnum.RECEIVE.getFlag(), refundAmt);

            return AjaxResult.success("退款申请成功");
        } catch (Exception e) {
            log.error("退款申请失败:" + e.getMessage());
            return AjaxResult.fail(e.getMessage());
        }
    }

}
