package com.school.manager.controller.order;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.school.biz.constant.ConstantUrl;
import com.school.biz.domain.entity.order.RefundOrderInfo;
import com.school.biz.enumeration.ExpressTypeEnum;
import com.school.biz.enumeration.RefundOrderStatusEnum;
import com.school.biz.exception.FuBusinessException;
import com.school.biz.service.order.RefundOrderInfoService;
import com.school.biz.util.pager.PageInfo;
import com.school.manager.controller.base.BaseEasyWebController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/refundOrder")
public class RefundOrderInfoController extends BaseEasyWebController {

	@Autowired
	private RefundOrderInfoService refundOrderInfoService;
	
	{
		listView = "order/refundOrder";
	}
	
	/**
	 * 分页查询
	 */
	@Override
	protected void onList(PageInfo pageInfo, Map<String, Object> searchParams, HttpServletRequest request,
						  ModelAndView mav) throws FuBusinessException {
		try {
			mav.addObject("listData",JSON.toJSON(refundOrderInfoService.queryPage(searchParams)));
			mav.addObject("refundOrderStatusMap",JSON.toJSON(RefundOrderStatusEnum.getAllStatusEnum()));
			mav.addObject("expressTypes", JSON.toJSON(ExpressTypeEnum.getAllTypeEnum()));
			mav.addObject(PAGE_PARAM_PAGECOUNT, pageInfo.getTotalPage());
			mav.addObject(PAGE_PARAM_TOTALCOUNT, pageInfo.getTotalRecord());
			
			mav.addObject(ConstantUrl.DETAIL_URL, ConstantUrl.REFUND_ORDER_DETAIL_URL);// 详情url
		} catch (Exception e) {
			log.error("退款订单分页查询出现错误："+e.getMessage());
			throw webExp(e);
		}
	}
	
	/**
	 * 查看详情
	 */
	@RequestMapping("/detail.do")
	public ModelAndView detail(Long id){
		try{
    		ModelAndView mv = new ModelAndView("order/refundOrderDetail");
    		RefundOrderInfo refundOrderInfo = refundOrderInfoService.get(id);
    		mv.addObject("refundOrderInfo", JSON.toJSON(refundOrderInfo));
    		mv.addObject("refundOrderStatusMap",JSON.toJSON(RefundOrderStatusEnum.getAllStatusEnum()));
    		return mv;
    	}catch(Exception e){
    		throw webExp(e);
    	}
	}
	
}
