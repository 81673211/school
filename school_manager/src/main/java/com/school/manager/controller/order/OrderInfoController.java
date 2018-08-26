package com.school.manager.controller.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.school.biz.constant.ConstantUrl;
import com.school.biz.domain.entity.express.ExpressCompany;
import com.school.biz.domain.entity.express.ExpressReceive;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.enumeration.ExpressTypeEnum;
import com.school.biz.enumeration.OrderStatusEnum;
import com.school.biz.enumeration.ReceiveExpressStatusEnum;
import com.school.biz.exception.FuBusinessException;
import com.school.biz.service.order.OrderInfoService;
import com.school.biz.util.pager.PageInfo;
import com.school.manager.controller.base.BaseEasyWebController;
import com.school.manager.vo.AjaxResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/order")
public class OrderInfoController extends BaseEasyWebController {

	@Autowired
	private OrderInfoService orderInfoService;
	
	{
		listView = "order/order";
	}
	
	/**
	 * 分页查询
	 */
	@Override
	protected void onList(PageInfo pageInfo, Map<String, Object> searchParams, HttpServletRequest request,
						  ModelAndView mav) throws FuBusinessException {
		try {
			mav.addObject("listData",JSON.toJSON(orderInfoService.queryPage(searchParams)));
			mav.addObject("orderStatusMap",JSON.toJSON(OrderStatusEnum.getAllStatusEnum()));
			mav.addObject("expressTypes", JSON.toJSON(ExpressTypeEnum.getAllTypeEnum()));
			mav.addObject(PAGE_PARAM_PAGECOUNT, pageInfo.getTotalPage());
			mav.addObject(PAGE_PARAM_TOTALCOUNT, pageInfo.getTotalRecord());
			
			mav.addObject(ConstantUrl.DETAIL_URL, ConstantUrl.ORDER_DETAIL_URL);// 详情url
		} catch (Exception e) {
			log.error("订单分页查询出现错误："+e.getMessage());
			throw webExp(e);
		}
	}
	
	/**
	 * 查看详情
	 */
	@RequestMapping("/detail.do")
	public ModelAndView detail(Long id){
		try{
    		ModelAndView mv = new ModelAndView("order/orderDetail");
    		OrderInfo orderInfo = orderInfoService.get(id);
    		mv.addObject("order", JSON.toJSON(orderInfo));
    		mv.addObject("orderStatusMap",JSON.toJSON(OrderStatusEnum.getAllStatusEnum()));
    		return mv;
    	}catch(Exception e){
    		throw webExp(e);
    	}
	}
	
}
