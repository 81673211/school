package com.school.web.express;

import com.school.common.model.DataResponse;
import com.school.common.model.Response;
import com.school.domain.entity.customer.Customer;
import com.school.exception.ExpressException;
import com.school.service.customer.CustomerService;
import com.school.service.express.ExpressCompanyService;
import com.school.service.express.ExpressReceiveService;
import com.school.service.express.ExpressSendService;
import com.school.service.wechat.OauthService;
import com.school.vo.BaseVo;
import com.school.vo.request.ReceiveExpressCreateVo;
import com.school.vo.request.ReceiveExpressModifyVo;
import com.school.vo.request.SendExpressCreateVo;
import com.school.vo.request.SendExpressModifyVo;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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

    /**
     * 寄件
     *
     * @param expressVo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/0/create", method = RequestMethod.POST)
    public Response createSendExpress(@Validated SendExpressCreateVo expressVo, BindingResult bindingResult) {
        Response response = checkValid(bindingResult);
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
        Response response = checkValid(bindingResult);
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
        Response response = checkValid(bindingResult);
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
        Response response = checkValid(bindingResult);
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
     * @param id
     * @return
     */
    @RequestMapping(value = "/0/get", method = RequestMethod.GET)
    public Response getSendExpress(@RequestParam(value = "id") Long id) {
        DataResponse<BaseVo> response = new DataResponse<>();
        if (id == null) {
            return response.writeFailure(PARAM_ERROR);
        }
        try {
            BaseVo responseVo = expressSendService.getSendExpress(id);
            return response.writeSuccess("获取寄件快件成功", responseVo);
        } catch (Exception e) {
            return response.writeFailure("获取寄件快件失败");
        }
    }


    /**
     * 获取 收件信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/1/get", method = RequestMethod.GET)
    public Response getReceiveExpress(@RequestParam(value = "id") Long id) {
        DataResponse<BaseVo> response = new DataResponse<>();
        if (id == null) {
            return response.writeFailure(PARAM_ERROR);
        }
        try {
            BaseVo responseVo = expressReceiveService.getReceiveExpress(id);
            return response.writeSuccess("获取收件快件成功", responseVo);
        } catch (Exception e) {
            return response.writeFailure("获取收件快件失败");
        }
    }


    /**
     * 寄件   修改寄件的快件状态
     *
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "/0/up-status", method = RequestMethod.POST)
    public Response updateSendExpressStatus(@RequestParam(value = "id") Long id,
                                            @RequestParam(value = "status") Integer status) {
        Response response = new Response();
        if (id == null || status == null) {
            return response.writeFailure(PARAM_ERROR);
        }
        try {
            expressSendService.updateSendExpressStatus(id, status);
            response.writeSuccess("修改寄件状态成功");
        } catch (Exception e) {
            response.writeFailure("修改寄件状态失败");
        }
        return response;
    }


    /**
     * 收件   修改收件的快件状态
     *
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "/1/up-status", method = RequestMethod.POST)
    public Response updateReceiveExpressStatus(@RequestParam(value = "id") Long id,
                                               @RequestParam(value = "status") Integer status) {
        Response response = new Response();
        if (id == null || status == null) {
            return response.writeFailure(PARAM_ERROR);
        }
        try {
            expressReceiveService.updateReceiveExpressStatus(id, status);
            response.writeSuccess("修改收件状态成功");
        } catch (Exception e) {
            response.writeFailure("修改收件状态失败");
        }
        return response;
    }


    /**
     * 收件  查询收件列表
     *
     * @param state
     * @return
     */
    @RequestMapping(value = "/1/list", method = RequestMethod.GET)
    public ModelAndView selectReceiveExpressList(@RequestParam(value = "state") String state,
                                                 @RequestParam(value = "code") String code)
            throws ExpressException {
        log.info("----code--:{}", code);
        log.info("----state--:{}", state);
        ModelAndView mav = new ModelAndView("received");
        if (StringUtils.isBlank(code)) {
            throw new RuntimeException("参数错误");
        }
        Customer customer = customerService.getByOpenId(oauthService.getOAuthToken(code).getOpenId());
        String phone = customer.getPhone();
        if (StringUtils.isBlank(phone)) {
            mav.setViewName("redirect:/customer/profile");
        } else {
            String[] split = state.split(",");
            Integer[] statuses = new Integer[split.length];
            for (int i = 0; i < split.length; i++) {
                statuses[i] = Integer.parseInt(split[i]);
            }
            List list = expressReceiveService.selectExpressList(statuses, phone);
            mav.addObject("list", list);
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
    public Response selectExpressList(@RequestParam(value = "status[]", required = false) Integer[] status,
                                      @RequestParam(value = "phone") String phone) {
        DataResponse<List> response = new DataResponse<>();
        if (StringUtils.isBlank(phone)) {
            return response.writeFailure(PARAM_ERROR);
        }
        try {
            List list = expressSendService.selectExpressList(status, phone);
            response.writeSuccess("查询列表成功", list);
        } catch (Exception e) {
            response.writeFailure("查询列表失败");
        }
        return response;
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
}
