package com.school.web.controller.order;

import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.service.express.ExpressSendService;
import com.school.biz.service.order.OrderInfoService;
import com.school.web.controller.base.BaseEasyWebController;
import com.school.web.vo.request.OrderCreateVo;
import com.school.web.vo.request.ReOrderCreateVo;
import com.school.web.vo.request.ReceiveSerivceReOrderCreateVo;
import com.school.web.vo.response.Response;
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
@RequestMapping(value = "/order")
@Validated
public class OrderController extends BaseEasyWebController {

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private ExpressSendService expressSendService;

    /**
     * 创建寄件订单
     *
     * @param vo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/0/create", method = RequestMethod.POST)
    public Response createSendOrder(@Validated OrderCreateVo vo, BindingResult bindingResult) {
        Response response = new Response();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        try {
            ExpressSend sendExpress = expressSendService.getSendExpress(vo.getExpressId());
            return response.writeSuccess(orderInfoService.createSendOrder(sendExpress));
        } catch (Exception e) {
            return response.writeFailure("创建订单失败");
        }
    }

    /**
     * 创建寄件补单订单
     *
     * @param vo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/0/reOrder/create", method = RequestMethod.POST)
    public Response createSendReOrder(@Validated ReOrderCreateVo vo, BindingResult bindingResult) {
        Response response = new Response();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        try {
            ExpressSend sendExpress = expressSendService.getSendExpress(vo.getExpressId());
            return response.writeSuccess(orderInfoService.createSendReOrder(sendExpress));
        } catch (Exception e) {
            return response.writeFailure("创建订单失败");
        }
    }


    /**
     * 创建收件订单
     *
     * @param vo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/1/create", method = RequestMethod.POST)
    public Response createReceiveOrder(@Validated OrderCreateVo vo, BindingResult bindingResult) {
        Response response = new Response();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        try {
            return response.writeSuccess(orderInfoService.createReceiveOrder(vo.getExpressId(), vo.getType()));
        } catch (Exception e) {
            log.error("创建订单失败，{}", e.getMessage());
            return response.writeFailure("创建订单失败, " + e.getMessage());
        }
    }

    /**
     * 创建收件补单订单
     *
     * @param vo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/1/serviceReOrder/create", method = RequestMethod.POST)
    public Response receiveServiceReOrder(@Validated ReceiveSerivceReOrderCreateVo vo, BindingResult bindingResult) {
        Response response = new Response();
        checkValid(bindingResult, response);
        if (response.getStatus() != HTTP_SUCCESS) {
            return response;
        }
        try {
            //todo
            ExpressSend sendExpress = expressSendService.getSendExpress(vo.getExpressId());
            return response.writeSuccess(orderInfoService.createSendReOrder(sendExpress));
        } catch (Exception e) {
            return response.writeFailure("创建订单失败");
        }
    }

}
