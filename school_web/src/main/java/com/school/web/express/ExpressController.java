package com.school.web.express;

import com.school.common.model.DataResponse;
import com.school.common.model.Response;
import com.school.service.express.ExpressReceiveService;
import com.school.service.express.ExpressSendService;
import com.school.vo.BaseVo;
import com.school.vo.request.ReceiveExpressVo;
import com.school.vo.request.SendExpressVo;
import com.school.web.base.BaseEasyWebController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 寄件
     *
     * @param expressVo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/0/create", method = RequestMethod.POST)
    public Response createSendExpress(@Validated SendExpressVo expressVo, BindingResult bindingResult) {
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
    public Response createReceiveExpress(@Validated ReceiveExpressVo expressVo, BindingResult bindingResult) {
        Response response = checkValid(bindingResult);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
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
    public Response modifySendExpress(@Validated SendExpressVo expressVo, BindingResult bindingResult) {
        Response response = checkValid(bindingResult);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        if (expressVo.getId() == null) {
            return response.writeFailure("系统参数错误");
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
    public Response modifySendExpress(@Validated ReceiveExpressVo expressVo, BindingResult bindingResult) {
        Response response = checkValid(bindingResult);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        if (expressVo.getId() == null) {
            return response.writeFailure("系统参数错误");
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
    public Response getSendExpress(@PathVariable(value = "id") Long id) {
        DataResponse<BaseVo> response = new DataResponse<>();
        if (id == null) {
            return response.writeFailure("系统参数错误");
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
    public Response getReceiveExpress(@PathVariable(value = "id") Long id) {
        DataResponse<BaseVo> response = new DataResponse<>();
        if (id == null) {
            return response.writeFailure("系统参数错误");
        }
        try {
            BaseVo responseVo = expressReceiveService.getReceiveExpress(id);
            return response.writeSuccess("获取收件快件成功", responseVo);
        } catch (Exception e) {
            return response.writeFailure("获取收件快件失败");
        }
    }
}
