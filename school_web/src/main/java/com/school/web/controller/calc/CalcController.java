package com.school.web.controller.calc;

import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.enumeration.ReceiveExpressDistributionTypeEnum;
import com.school.biz.enumeration.SendExpressCollectTypeEnum;
import com.school.biz.service.calc.CalcCostService;
import com.school.web.controller.base.BaseEasyWebController;
import com.school.web.vo.request.CalcHelpReceiveServiceAmtVo;
import com.school.web.vo.request.CalcSendServiceAmtVo;
import com.school.web.vo.request.SendExpressCalcVo;
import com.school.web.vo.response.DataResponse;
import com.school.web.vo.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author jame
 * @date 2018/8/5
 * @desc 计费
 */
@Slf4j
@RestController
@RequestMapping(value = "/calc")
@Validated
public class CalcController extends BaseEasyWebController {

    @Autowired
    private CalcCostService calcCostService;

    @RequestMapping(value = "/0", method = RequestMethod.GET)
    public Response getSendExpress(@Validated SendExpressCalcVo sendExpressCalcVo, BindingResult bindingResult) {
        DataResponse<BigDecimal> response = new DataResponse<>();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        try {
            ExpressSend expressSend = new ExpressSend();
            BeanUtils.copyProperties(sendExpressCalcVo, expressSend);
            return response.writeSuccess("寄件价格计算成功", calcCostService.calcSendTransportCost(expressSend));
        } catch (Exception e) {
            return response.writeFailure("寄件价格计算失败");
        }
    }


    @RequestMapping(value = "/0/serviceAmt", method = RequestMethod.GET)
    public Response getSendServiceAmt(@Validated CalcSendServiceAmtVo calcSendServiceAmtVo, BindingResult bindingResult) {
        DataResponse<BigDecimal> response = new DataResponse<>();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        return response.writeSuccess("获取寄件服务费成功",
                calcCostService.calcSendDistributionCost(SendExpressCollectTypeEnum.DOOR.getFlag(), calcSendServiceAmtVo.getExpressWeight()));
    }


    @RequestMapping(value = "/1/help/serviceAmt", method = RequestMethod.GET)
    public Response getHelpReceiveServiceAmt(@Validated CalcHelpReceiveServiceAmtVo serviceAmtVo, BindingResult bindingResult) {
        DataResponse<BigDecimal> response = new DataResponse<>();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        return response.writeSuccess("获取寄件服务费成功",
                calcCostService.calcHelpReceiveDistributionCost(serviceAmtVo.getDistributionType(), serviceAmtVo.getExpressWeight()));
    }
}
