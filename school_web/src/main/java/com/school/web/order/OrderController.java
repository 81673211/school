package com.school.web.order;

import java.util.EnumSet;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.school.common.OrderStatusEnum;
import com.school.service.order.OrderService;
import com.school.util.core.exception.FuBusinessException;
import com.school.util.core.pager.PageInfo;
import com.school.web.base.BaseEasyWebController;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 07/06/2018 12:41
 */
@Controller
public class OrderController extends BaseEasyWebController {

    @Autowired
    private OrderService orderService;

    @Override
    protected void onList(PageInfo pageInfo, Map<String, Object> searchParams, HttpServletRequest request,
                          ModelAndView mav) throws FuBusinessException {
//        mav.addObject("listData", JSON.toJSON(orderService.query()));
        mav.addObject(PAGE_PARAM_PAGECOUNT, pageInfo.getTotalPage());
        mav.addObject(PAGE_PARAM_TOTALCOUNT, pageInfo.getTotalRecord());
        EnumSet<OrderStatusEnum> orderStatusEnums = EnumSet.allOf(OrderStatusEnum.class);
    }


}
