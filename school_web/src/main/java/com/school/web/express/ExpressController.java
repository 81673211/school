package com.school.web.express;

import com.school.common.model.DataResponse;
import com.school.common.model.Response;
import com.school.service.express.ExpressCompanyService;
import com.school.service.express.ExpressReceiveService;
import com.school.service.express.ExpressSendService;
import com.school.vo.BaseVo;
import com.school.vo.request.*;
import com.school.web.base.BaseEasyWebController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping(value = "/1/up-status", method = RequestMethod.POST)
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
    public Response selectReceiveExpressList(@RequestParam(value = "status[]", required = false) Integer[] status,
                                             @Validated BaseVo vo, BindingResult bindingResult) {
        DataResponse<List> response = new DataResponse<>();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        try {
            List list = expressReceiveService.selectExpressList(status, vo.getOpenid());
            response.writeSuccess("查询列表成功", list);
        } catch (Exception e) {
            response.writeFailure("查询列表失败");
        }
        return response;
    }


    /**
     * 寄件  查询寄件列表
     *
     * @param status
     * @return
     */
    @RequestMapping(value = "/0/list", method = RequestMethod.GET)
    public Response selectExpressList(@RequestParam(value = "status[]", required = false) Integer[] status,
                                      @Validated BaseVo vo, BindingResult bindingResult) {
        DataResponse<List> response = new DataResponse<>();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        try {
            List list = expressSendService.selectExpressList(status, vo.getOpenid());
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
