package com.school.web.express;

import com.school.common.model.Response;
import com.school.service.express.ExpressReceiveService;
import com.school.service.express.ExpressSendService;
import com.school.vo.request.CreateReceiveExpressVo;
import com.school.vo.request.CreateSendExpressVo;
import com.school.web.base.BaseEasyWebController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
     * @param expressVo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/0/create", method = RequestMethod.POST)
    public Response createSendExpress(@Validated CreateSendExpressVo expressVo, BindingResult bindingResult) {
        Response valid = checkValid(bindingResult);
        if (valid.getStatus() != HTTP_SUCCESS) {
            return valid;
        }
        try {
            expressSendService.createSendExpress(expressVo);
            return valid.writeSuccess("创建寄件快件成功");
        } catch (Exception e) {
            return valid.writeFailure("创建寄件快件失败");
        }
    }

    /**
     * 收件
     * @param expressVo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/1/create", method = RequestMethod.POST)
    public Response createReceiveExpress(@Validated CreateReceiveExpressVo expressVo, BindingResult bindingResult) {
        Response valid = checkValid(bindingResult);
        if (valid.getStatus() != HTTP_SUCCESS) {
            return valid;
        }
        try {
            expressReceiveService.createReceiveExpress(expressVo);
            return valid.writeSuccess("创建收件快件成功");
        } catch (Exception e) {
            return valid.writeFailure("创建收件快件失败");
        }
    }

}
