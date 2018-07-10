package com.school.web.order;

import com.school.common.model.Response;
import com.school.service.order.OrderInfoService;
import com.school.vo.request.OrderCreateVo;
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
@RequestMapping(value = "/order")
@Validated
public class OrderController extends BaseEasyWebController {

    @Autowired
    private OrderInfoService orderInfoService;

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
            orderInfoService.createSendOrder(vo);
            return response.writeSuccess("创建订单成功");
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
            orderInfoService.createReceiveOrder(vo);
            return response.writeSuccess("创建订单成功");
        } catch (Exception e) {
            return response.writeFailure("创建订单失败");
        }
    }

}
