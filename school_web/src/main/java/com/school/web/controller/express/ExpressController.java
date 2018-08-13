package com.school.web.controller.express;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.school.biz.domain.entity.customer.Customer;
import com.school.biz.domain.entity.express.ExpressCompany;
import com.school.biz.domain.entity.express.ExpressReceive;
import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.domain.entity.region.Region;
import com.school.biz.enumeration.ExpressTypeEnum;
import com.school.biz.enumeration.ReceiveExpressStatusEnum;
import com.school.biz.service.calc.CalcCostService;
import com.school.biz.service.customer.CustomerService;
import com.school.biz.service.express.ExpressCompanyService;
import com.school.biz.service.express.ExpressReceiveService;
import com.school.biz.service.express.ExpressSendService;
import com.school.biz.service.region.RegionService;
import com.school.web.controller.base.BaseEasyWebController;
import com.school.web.vo.request.ExpressGetVo;
import com.school.web.vo.request.ExpressStatusModifyVo;
import com.school.web.vo.request.ReceiveExpressCreateVo;
import com.school.web.vo.request.ReceiveExpressModifyVo;
import com.school.web.vo.request.SendExpressCreateVo;
import com.school.web.vo.request.SendExpressModifyVo;
import com.school.web.vo.response.DataResponse;
import com.school.web.vo.response.ReceiveExpressListResponseVo;
import com.school.web.vo.response.Response;
import com.school.web.vo.response.SendExpressListResponseVo;

import lombok.extern.slf4j.Slf4j;

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
    private CalcCostService calcCostService;

    /**
     * 寄件
     *
     * @param expressVo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/0/create", method = RequestMethod.POST)
    public Response createSendExpress(@Validated SendExpressCreateVo expressVo, BindingResult bindingResult)
            throws InvocationTargetException, IllegalAccessException {
        DataResponse<String> response = new DataResponse<>();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        ExpressSend expressSend = new ExpressSend();
        BeanUtils.copyProperties(expressSend, expressVo);
        Customer customer = customerService.getByOpenId(expressVo.getOpenId());
        expressSend.setCustomerId(customer.getId());
        expressSend.setSenderPhone(customer.getPhone());
        expressSend.setSenderName(customer.getName());
        String orderNo = expressSendService.createSendExpress(expressSend);
        return response.writeSuccess("创建寄件快件成功", orderNo);
    }

    /**
     * 收件
     *
     * @param expressVo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/1/create", method = RequestMethod.POST)
    public Response createReceiveExpress(@Validated ReceiveExpressCreateVo expressVo, BindingResult bindingResult) {
        Response response = new Response();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        if (expressVo.getCompanyCode() == null || expressVo.getCompanyName() == null || expressVo.getCompanyId() == null) {
            return response.writeFailure(PARAM_ERROR);
        }
        try {
            ExpressReceive expressReceive = new ExpressReceive();
            BeanUtils.copyProperties(expressReceive, expressVo);
            expressReceiveService.createReceiveExpress(expressReceive);
            return response.writeSuccess("创建收件快件成功");
        } catch (Exception e) {
            return response.writeFailure("创建收件快件失败");
        }
    }

    /**
     * 寄件 编辑
     *
     * @param expressVo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/0/modify", method = RequestMethod.POST)
    public Response modifySendExpress(@Validated SendExpressModifyVo expressVo, BindingResult bindingResult) {
        Response response = new Response();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        try {
            ExpressSend expressSend = new ExpressSend();
            BeanUtils.copyProperties(expressSend, expressVo);
            expressSendService.modifySendExpress(expressSend);
            return response.writeSuccess("编辑寄件快件成功");
        } catch (Exception e) {
            return response.writeFailure("编辑寄件快件失败");
        }
    }


    /**
     * 收件 编辑
     *
     * @param expressVo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/1/modify", method = RequestMethod.POST)
    public Response modifySendExpress(@Validated ReceiveExpressModifyVo expressVo, BindingResult bindingResult) {
        Response response = new Response();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        try {
            ExpressReceive expressReceive = new ExpressReceive();
            BeanUtils.copyProperties(expressReceive, expressVo);
            expressReceiveService.modifyReceiveExpress(expressReceive);
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


    /**
     * 寄件   修改寄件的快件状态
     *
     * @param vo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/0/up-status", method = RequestMethod.POST)
    public Response updateSendExpressStatus(@Validated ExpressStatusModifyVo vo, BindingResult bindingResult) {
        Response response = new Response();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        try {
            expressSendService.updateSendExpressStatus(vo.getId(), vo.getStatus());
            response.writeSuccess("修改寄件状态成功");
        } catch (Exception e) {
            response.writeFailure("修改寄件状态失败");
        }
        return response;
    }


    /**
     * 收件   修改收件的快件状态
     *
     * @param vo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/1/updateStatus", method = RequestMethod.POST)
    public Response updateReceiveExpressStatus(@Validated ExpressStatusModifyVo vo, BindingResult bindingResult) {
        Response response = new Response();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        try {
            expressReceiveService.updateReceiveExpressStatus(vo.getId(), vo.getStatus());
            response.writeSuccess("修改收件状态成功");
        } catch (Exception e) {
            response.writeFailure("修改收件状态失败");
        }
        return response;
    }


    /**
     * 收件  查询收件列表
     *
     * @param status
     * @return
     */
    @RequestMapping(value = "/1/list", method = RequestMethod.GET)
    public ModelAndView selectReceiveExpressList(@RequestParam(value = "status", required = false) String status,
                                                 @RequestParam(value = "openId") String openId)
            throws InvocationTargetException, IllegalAccessException {
        ModelAndView mav = new ModelAndView();
        Customer customer = customerService.getByOpenId(openId);
        String phone = customer.getPhone();
        if (StringUtils.isBlank(phone)) {
            mav.setViewName("redirect:/customer/profile?openId=" + openId);
        } else {
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
                    BeanUtils.copyProperties(vo, expressReceive);
                    vo.setDistributionCost(calcCostService.calcReceiveDistributionCost(expressReceive));
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
                    BeanUtils.copyProperties(vo, expressSend);
                    vo.setOrderPrice(expressSendService.getOrderPrice(expressSend.getId(), ExpressTypeEnum.SEND));
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
        response.writeSuccess("查询列表成功", expressCompanyService.findAll());
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
            List<Region> regionList = regionService.selectRegionList(null);
            List<ExpressCompany> companyList = expressCompanyService.findAll();
            modelAndView.addObject("openId", openId);
            modelAndView.addObject("regionList", regionList);
            modelAndView.addObject("companyList", companyList);
            modelAndView.setViewName("sending");
        } catch (Exception e) {
            modelAndView.setViewName("redirect:/error");
        }
        return modelAndView;
    }


}
