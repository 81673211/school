package com.school.web.express;

import com.school.common.model.DataResponse;
import com.school.common.model.Response;
import com.school.domain.entity.customer.Customer;
import com.school.enumeration.ReceiveExpressStatusEnum;
import com.school.exception.ExpressException;
import com.school.service.customer.CustomerService;
import com.school.service.express.ExpressCompanyService;
import com.school.service.express.ExpressReceiveService;
import com.school.service.express.ExpressSendService;
import com.school.service.region.RegionService;
import com.school.service.wechat.OauthService;
import com.school.vo.BaseVo;
import com.school.vo.request.*;
import com.school.web.base.BaseEasyWebController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
    private OauthService oauthService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RegionService regionService;

    /**
     * 寄件
     *
     * @param expressVo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/0/create", method = RequestMethod.POST)
    public Response createSendExpress(@Validated SendExpressCreateVo expressVo, BindingResult bindingResult) {
        Response response = new Response();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        try {
            expressSendService.createSendExpress(expressVo);
            return response.writeSuccess("创建寄件快件成功");
        } catch (Exception e) {
            return response.writeFailure("创建寄件快件失败");
        }
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
            expressReceiveService.createReceiveExpress(expressVo);
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
            expressSendService.modifySendExpress(expressVo);
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
            expressReceiveService.modifyReceiveExpress(expressVo);
            return response.writeSuccess("编辑寄件快件成功");
        } catch (Exception e) {
            return response.writeFailure("编辑寄件快件失败");
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
        DataResponse<BaseVo> response = new DataResponse<>();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        try {
            BaseVo responseVo = expressSendService.getSendExpress(vo.getId());
            return response.writeSuccess("获取寄件快件成功", responseVo);
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
        DataResponse<BaseVo> response = new DataResponse<>();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        try {
            BaseVo responseVo = expressReceiveService.getReceiveExpress(vo.getId());
            return response.writeSuccess("获取收件快件成功", responseVo);
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
            throws ExpressException {
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
            List list = expressReceiveService.selectExpressList(statuses, phone);
            mav.addObject("list", list);
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
            List list = expressSendService.selectExpressList(statuses, openId);
            modelAndView.addObject("list", list);
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
        try {
            List list = expressCompanyService.selectList();
            response.writeSuccess("查询列表成功", list);
        } catch (Exception e) {
            response.writeFailure("查询列表失败");
        }
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
            List<BaseVo> regionList = regionService.selectRegionList(null);
            List<BaseVo> companyList = expressCompanyService.selectList();
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
