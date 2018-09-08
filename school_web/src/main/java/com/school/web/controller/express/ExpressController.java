package com.school.web.controller.express;

import com.school.biz.domain.entity.customer.Customer;
import com.school.biz.domain.entity.express.ExpressCompany;
import com.school.biz.domain.entity.express.ExpressReceive;
import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.domain.entity.region.Region;
import com.school.biz.enumeration.*;
import com.school.biz.service.calc.CalcCostService;
import com.school.biz.service.customer.CustomerService;
import com.school.biz.service.express.ExpressCompanyService;
import com.school.biz.service.express.ExpressReceiveService;
import com.school.biz.service.express.ExpressSendService;
import com.school.biz.service.order.OrderInfoService;
import com.school.biz.service.region.RegionService;
import com.school.biz.service.wechat.TemplateService;
import com.school.web.controller.base.BaseEasyWebController;
import com.school.web.vo.request.ExpressGetVo;
import com.school.web.vo.request.HelpReceiveExpressCreateVo;
import com.school.web.vo.request.ReceiveExpressModifyVo;
import com.school.web.vo.request.SendExpressCreateVo;
import com.school.web.vo.response.DataResponse;
import com.school.web.vo.response.ReceiveExpressListResponseVo;
import com.school.web.vo.response.Response;
import com.school.web.vo.response.SendExpressListResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jame
 */
@Slf4j
@RestController
@RequestMapping(value = "/express")
@Validated
public class ExpressController extends BaseEasyWebController {

    @Autowired
    private ExpressReceiveService expressReceiveService;
    @Autowired
    private ExpressSendService expressSendService;
    @Autowired
    private ExpressCompanyService expressCompanyService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private TemplateService templateService;
    @Autowired
    private CalcCostService calcCostService;
    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 寄件
     *
     * @param expressVo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/0/create", method = RequestMethod.POST)
    public Response createSendExpress(@Validated SendExpressCreateVo expressVo, BindingResult bindingResult) {
        DataResponse<String> response = new DataResponse<>();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        ExpressSend expressSend = new ExpressSend();
        BeanUtils.copyProperties(expressVo, expressSend);
        Customer customer = customerService.getByOpenId(expressVo.getOpenId());
        expressSend.setCustomerId(customer.getId());
        expressSend.setSenderPhone(customer.getPhone());
        expressSend.setSenderName(customer.getName());
        expressSend.setExpressStatus(SendExpressStatusEnum.CREATE.getFlag());
        String orderNo = expressSendService.createSendExpress(expressSend);
        return response.writeSuccess("创建寄件快件成功", orderNo);
    }

//    /**
//     * 收件
//     *
//     * @param expressVo
//     * @param bindingResult
//     * @return
//     */
//    @RequestMapping(value = "/1/create", method = RequestMethod.POST)
//    public Response createReceiveExpress(@Validated ReceiveExpressCreateVo expressVo,
//                                         BindingResult bindingResult) {
//        Response response = new Response();
//        checkValid(bindingResult, response);
//        if (response.getStatus() != HTTP_SUCCESS) {
//            return response;
//        }
//        if (expressVo.getCompanyCode() == null || expressVo.getCompanyName() == null
//                || expressVo.getCompanyId() == null) {
//            return response.writeFailure(PARAM_ERROR);
//        }
//        try {
//            ExpressReceive expressReceive = new ExpressReceive();
//            BeanUtils.copyProperties(expressVo, expressReceive);
//            expressReceiveService.createReceiveExpress(expressReceive);
//            return response.writeSuccess("创建收件快件成功");
//        } catch (Exception e) {
//            return response.writeFailure("创建收件快件失败");
//        }
//    }
//
//    /**
//     * 寄件 编辑
//     *
//     * @param expressVo
//     * @param bindingResult
//     * @return
//     */
//    @RequestMapping(value = "/0/modify", method = RequestMethod.POST)
//    public Response modifySendExpress(@Validated SendExpressModifyVo expressVo, BindingResult bindingResult) {
//        Response response = new Response();
//        checkValid(bindingResult, response);
//        if (response.getStatus() != HTTP_SUCCESS) {
//            return response;
//        }
//        try {
//            ExpressSend expressSend = new ExpressSend();
//            BeanUtils.copyProperties(expressVo, expressSend);
//            expressSendService.modifySendExpress(expressSend);
//            return response.writeSuccess("编辑寄件快件成功");
//        } catch (Exception e) {
//            return response.writeFailure("编辑寄件快件失败");
//        }
//    }

    /**
     * 收件 编辑
     *
     * @param expressVo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/1/modify", method = RequestMethod.POST)
    public Response modifySendExpress(@Validated ReceiveExpressModifyVo expressVo,
                                      BindingResult bindingResult) {
        Response response = new Response();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        try {
            ExpressReceive expressReceive = new ExpressReceive();
            BeanUtils.copyProperties(expressVo, expressReceive);
            expressReceiveService.modifyReceiveExpress(expressReceive);
            expressReceive = expressReceiveService.get(expressVo.getId());
            templateService.send(WechatTemplateEnum.RECEIVE_EXPRESS_DISTRIBUTION_SELF.getType(),
                    expressVo.getOpenId(), expressReceive, ExpressTypeEnum.RECEIVE.getFlag());
            return response.writeSuccess("编辑收件快件成功");
        } catch (Exception e) {
            return response.writeFailure("编辑收件快件失败");
        }
    }

    /**
     * 获取 寄件信息
     *
     * @param vo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/0/get", method = RequestMethod.GET)
    public Response getSendExpress(@Validated ExpressGetVo vo, BindingResult bindingResult) {
        DataResponse<ExpressSend> response = new DataResponse<>();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        try {
            return response.writeSuccess("获取寄件快件成功", expressSendService.getSendExpress(vo.getId()));
        } catch (Exception e) {
            return response.writeFailure("获取寄件快件失败");
        }
    }

    /**
     * 获取 收件信息
     *
     * @param vo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/1/get", method = RequestMethod.GET)
    public Response getReceiveExpress(@Validated ExpressGetVo vo, BindingResult bindingResult) {
        DataResponse<ExpressReceive> response = new DataResponse<>();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        try {
            return response.writeSuccess("获取收件快件成功", expressReceiveService.getReceiveExpress(vo.getId()));
        } catch (Exception e) {
            return response.writeFailure("获取收件快件失败");
        }
    }

//    /**
//     * 寄件   修改寄件的快件状态
//     *
//     * @param vo
//     * @param bindingResult
//     * @return
//     */
//    @RequestMapping(value = "/0/up-status", method = RequestMethod.POST)
//    public Response updateSendExpressStatus(@Validated ExpressStatusModifyVo vo, BindingResult bindingResult) {
//        Response response = new Response();
//        checkValid(bindingResult, response);
//        if (response.getStatus() != HTTP_SUCCESS) {
//            return response;
//        }
//        try {
//            expressSendService.updateSendExpressStatus(vo.getId(), vo.getStatus());
//            response.writeSuccess("修改寄件状态成功");
//        } catch (Exception e) {
//            response.writeFailure("修改寄件状态失败");
//        }
//        return response;
//    }

//    /**
//     * 收件   修改收件的快件状态
//     *
//     * @param vo
//     * @param bindingResult
//     * @return
//     */
//    @RequestMapping(value = "/1/updateStatus", method = RequestMethod.POST)
//    public Response updateReceiveExpressStatus(@Validated ExpressStatusModifyVo vo,
//                                               BindingResult bindingResult) {
//        Response response = new Response();
//        checkValid(bindingResult, response);
//        if (response.getStatus() != HTTP_SUCCESS) {
//            return response;
//        }
//        try {
//            expressReceiveService.updateReceiveExpressStatus(vo.getId(), vo.getStatus());
//            response.writeSuccess("修改收件状态成功");
//        } catch (Exception e) {
//            response.writeFailure("修改收件状态失败");
//        }
//        return response;
//    }

    /**
     * 收件  查询收件列表
     *
     * @param status
     * @return
     */
    @RequestMapping(value = "/1/list", method = RequestMethod.GET)
    public ModelAndView selectReceiveExpressList(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "openId") String openId) {
        ModelAndView mav = new ModelAndView();
        Customer customer = customerService.getByOpenId(openId);
        String phone = customer.getPhone();
        if (StringUtils.isBlank(phone)) {
            mav.setViewName("redirect:/customer/profile?openId=" + openId);
            return mav;
        }

        String[] split = status.split(",");
        Integer[] statuses = new Integer[split.length];
        for (int i = 0; i < split.length; i++) {
            statuses[i] = Integer.parseInt(split[i]);
        }
        List<ExpressReceive> receiveList = expressReceiveService.selectExpressList(statuses, phone);
        List<ReceiveExpressListResponseVo> receiveExpressListResponseVos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(receiveList)) {
            for (ExpressReceive expressReceive : receiveList) {
                ReceiveExpressListResponseVo vo = new ReceiveExpressListResponseVo();
                BeanUtils.copyProperties(expressReceive, vo);
                vo.setDistributionCost(calcCostService.calcReceiveDistributionCost(DistributionTypeEnum.DISTRIBUTION.getFlag()));
                receiveExpressListResponseVos.add(vo);
            }
        }
        mav.addObject("list", receiveExpressListResponseVos);
        mav.addObject("openId", openId);
        if (split.length == 1 && ReceiveExpressStatusEnum.FINISHED.getFlag() == Integer.parseInt(split[0])) {
            mav.setViewName("received");
        } else {
            mav.setViewName("receive");
        }
        return mav;
    }

    /**
     * 寄件  查询寄件列表
     *
     * @param status
     * @return
     */
    @RequestMapping(value = "/0/list", method = RequestMethod.GET)
    public ModelAndView selectExpressList(@RequestParam(value = "status", required = false) String status,
                                          @RequestParam(value = "openId") String openId) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Customer customer = customerService.getByOpenId(openId);
            String phone = customer.getPhone();
            if (StringUtils.isBlank(phone)) {
                modelAndView.setViewName("redirect:/customer/profile?openId=" + openId);
                return modelAndView;
            }

            Integer[] statuses = null;
            if (StringUtils.isNotBlank(status)) {
                String[] split = status.split(",");
                statuses = new Integer[split.length];
                for (int i = 0; i < split.length; i++) {
                    statuses[i] = Integer.parseInt(split[i]);
                }
            }
            List<ExpressSend> list = expressSendService.selectExpressList(statuses, openId);
            List<SendExpressListResponseVo> vos = new ArrayList<>();
            if (!CollectionUtils.isEmpty(list)) {
                for (ExpressSend expressSend : list) {
                    SendExpressListResponseVo vo = new SendExpressListResponseVo();
                    BeanUtils.copyProperties(expressSend, vo);
//                    vo.setTransportPrice(
//                            expressSendService.getSendTransportPrice(expressSend));
//                    vo.setServiceAmt(expressSend.getServiceAmt());
                    vos.add(vo);
                }
            }
            modelAndView.addObject("list", vos);
            modelAndView.setViewName("sent");
        } catch (Exception e) {
            modelAndView.setViewName("redirect:/error");
        }
        return modelAndView;
    }

    /**
     * 快递公司列表
     *
     * @return
     */
    @RequestMapping(value = "/company", method = RequestMethod.GET)
    public Response selectList() {
        DataResponse<List> response = new DataResponse<>();
        response.writeSuccess("查询列表成功", expressCompanyService.findAllCooperate());
        return response;
    }

    /**
     * 寄件  页面跳转
     *
     * @param openId
     * @return
     */
    @RequestMapping(value = "/sending", method = RequestMethod.GET)
    public ModelAndView sending(@RequestParam(value = "openId") String openId) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            Customer customer = customerService.getByOpenId(openId);
            String phone = customer.getPhone();
            if (StringUtils.isBlank(phone)) {
                modelAndView.setViewName("redirect:/customer/profile?openId=" + openId);
                return modelAndView;
            }
            List<Region> regionList = regionService.selectRegionList(0L);
            modelAndView.addObject("openId", openId);
            modelAndView.addObject("regionList", regionList);
            modelAndView.setViewName("sending");
        } catch (Exception e) {
            modelAndView.setViewName("redirect:/error");
        }
        return modelAndView;
    }


    /**
     * 帮我收件  页面跳转
     *
     * @param openId
     * @return
     */
    @RequestMapping(value = "/help/receive", method = RequestMethod.GET)
    public ModelAndView helpReceiveExpress(@RequestParam(value = "openId") String openId) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Customer customer = customerService.getByOpenId(openId);
            String phone = customer.getPhone();
            if (StringUtils.isBlank(phone)) {
                modelAndView.setViewName("redirect:/customer/profile?openId=" + openId);
                return modelAndView;
            }
            List<ExpressCompany> companyList = expressCompanyService.findAll();
            modelAndView.addObject("openId", openId);
            modelAndView.addObject("companyList", companyList);
            modelAndView.setViewName("help_receive");
        } catch (Exception e) {
            modelAndView.setViewName("redirect:/error");
        }
        return modelAndView;
    }


    /**
     * 帮我收件
     *
     * @param expressVo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/1/help/create", method = RequestMethod.POST)
    public Response createHelpReceiveExpress(@Validated HelpReceiveExpressCreateVo expressVo,
                                             BindingResult bindingResult) {
        DataResponse<String> response = new DataResponse<>();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        try {
            ExpressReceive expressReceive = new ExpressReceive();
            BeanUtils.copyProperties(expressVo, expressReceive);
            Customer customer = customerService.getByOpenId(expressVo.getOpenId());
            expressReceive.setCustomerId(customer.getId());
            expressReceive.setReceiverAddr(customer.getAddr());
            expressReceive.setExpressWay(DistributionTypeEnum.DISTRIBUTION.getFlag());
            expressReceive.setExpressType(ReceiveExpressTypeEnum.HELP_RECEIVE.getFlag());
            expressReceive.setHelpDistributionType(expressVo.getDistributionType());
            expressReceive.setExpressStatus(ReceiveExpressStatusEnum.INEFFECTIVE.getFlag());
            String orderNo = expressReceiveService.createHelpReceiveExpress(expressReceive);
            return response.writeSuccess("处理帮我收件成功", orderNo);
        } catch (Exception e) {
            log.error("处理帮我收件失败,{}", e.getMessage());
            return response.writeFailure("处理帮我收件失败");
        }
    }


}
