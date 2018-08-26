package com.school.manager.controller.express;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.school.biz.constant.ConstantUrl;
import com.school.biz.exception.FuBusinessException;
import com.school.biz.util.pager.PageInfo;
import com.school.manager.vo.AjaxResult;
import com.school.biz.domain.entity.express.ExpressCompany;
import com.school.biz.domain.entity.express.ExpressSend;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.enumeration.SendExpressStatusEnum;
import com.school.biz.service.express.ExpressCompanyService;
import com.school.biz.service.express.ExpressSendService;
import com.school.biz.service.order.OrderInfoService;
import com.school.manager.controller.base.BaseEasyWebController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/express/expressSend")
public class ExpressSendController extends BaseEasyWebController {

	@Autowired
	private ExpressSendService expressSendService;
	
	@Autowired
	private ExpressCompanyService expressCompanyService;
	
	@Autowired
	private OrderInfoService orderInfoService;
	
	{
		listView = "express/expressSend";
	}
	
	/**
	 * 分页查询
	 */
	@Override
	protected void onList(PageInfo pageInfo, Map<String, Object> searchParams, HttpServletRequest request,
						  ModelAndView mav) throws FuBusinessException {
		try {
			mav.addObject("listData",JSON.toJSON(expressSendService.queryPage(searchParams)));
			mav.addObject("expressSendStatusMap",JSON.toJSON(SendExpressStatusEnum.getAllStatusEnum()));
			mav.addObject(PAGE_PARAM_PAGECOUNT, pageInfo.getTotalPage());
			mav.addObject(PAGE_PARAM_TOTALCOUNT, pageInfo.getTotalRecord());
			
			mav.addObject(ConstantUrl.DETAIL_URL, ConstantUrl.EXPRESS_SEND_DETAIL_URL);// 详情url
			mav.addObject(ConstantUrl.EDIT_URL, ConstantUrl.EXPRESS_SEND_EDIT_URL);// 编辑url
			mav.addObject(ConstantUrl.DEL_URL,ConstantUrl.EXPRESS_SEND_DEL_URL);// 删除url
			mav.addObject("refundUrl", ConstantUrl.EXPRESS_SEND_REFUND_URL);// 跳转退款url
			mav.addObject("reOrderUrl",ConstantUrl.EXPRESS_SEND_REORDER_URL);// 跳转补单url
		} catch (Exception e) {
			log.error("寄件查询出现错误："+e.getMessage());
			throw webExp(e);
		}
	}
	
	/**
	 * 查看详情
	 */
	@RequestMapping("/detail.do")
	public ModelAndView detail(Long id){
		try{
    		ModelAndView mv = new ModelAndView("express/expressSendDetail");
    		ExpressSend expressSend = expressSendService.get(id);
    		mv.addObject("expressSend", JSON.toJSON(expressSend));
    		mv.addObject("expressSendStatusMap",JSON.toJSON(SendExpressStatusEnum.getAllStatusEnum()));
    		return mv;
    	}catch(Exception e){
    		throw webExp(e);
    	}
	}
	
	/**
	 * 编辑页面
	 */
	@RequestMapping(value = "/edit.do")
	public ModelAndView edit(Long id, HttpServletRequest request){
		ModelAndView mav= new ModelAndView();
		mav.setViewName("express/expressSendEdit");
		ExpressSend expressSend = expressSendService.get(id);
		mav.addObject("expressSend", JSON.toJSON(expressSend));
		mav.addObject("expressSendStatusMap",JSON.toJSON(SendExpressStatusEnum.getAllStatusEnum()));
		// 查询所有快递公司
		List<ExpressCompany> expressCompanyList = expressCompanyService.findAllCooperate();
		mav.addObject("expressCompanyList",JSON.toJSON(expressCompanyList));
		return mav;
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save.do")
	public AjaxResult save(ExpressSend expressSend){
		try{
			ExpressCompany expressCompany = expressCompanyService.findByCode(expressSend.getCompanyCode());
			expressSend.setCompanyId(expressCompany.getId());
			expressSend.setCompanyName(expressCompany.getName());
			expressSendService.saveOrUpdate(expressSend);
			return AjaxResult.success("保存成功", JSON.toJSON(expressSend));
		}catch(Exception e){
			log.error("保存寄件出错：" + e.getMessage());
			return AjaxResult.fail("保存失败");
		}
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/del.do")
	public AjaxResult del(Long id){
		try {
			expressSendService.deleteById(id);
			return AjaxResult.success("删除成功");
		} catch (Exception e) {
			log.error("删除寄件出错：" + e.getMessage());
			return AjaxResult.fail("删除失败");
		}
	}
	
	/**
     * 退款页面
     */
    @RequestMapping(value = "/refund.do",method=RequestMethod.GET)
    public ModelAndView toRefund(Long id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        ExpressSend expressSend = expressSendService.get(id);
        
        mav.setViewName("express/refund");
        mav.addObject("expressSend",expressSend);
        return mav;
    }
	
	/**
	 * 退款申请
	 * @throws Exception 
	 */
    @ResponseBody
	@RequestMapping(value="/refund.do",method=RequestMethod.POST)
	public Object refund(HttpServletRequest request,String expressSendNo,BigDecimal refundAmt,BigDecimal currentOrderRefundAmt) throws Exception{
		try{
			if(StringUtils.isBlank(expressSendNo)){
				throw new Exception("快递单号不能为空");
			}
			if(refundAmt == null || !(refundAmt.compareTo(new BigDecimal(0)) >0)){
				throw new Exception("退款金额不正确");
			}
			
			orderInfoService.refund(request,expressSendNo,refundAmt);
			
			return AjaxResult.success("退款申请成功");
		}catch(Exception e){
			log.error("退款申请失败");
			return AjaxResult.fail(e.getMessage());
		}
	}
    
    /**
     * 补单页面
     */
    @RequestMapping(value = "/reOrder.do",method=RequestMethod.GET)
    public ModelAndView toReorder(Long id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        ExpressSend express = expressSendService.get(id);
        
        mav.setViewName("express/reOrder");
        mav.addObject("express",express);
        return mav;
    }
    
    /**
     * 补单
     */
    @ResponseBody
   	@RequestMapping(value="/reOrder.do",method=RequestMethod.POST)
    public Object reOrder(HttpServletRequest request,String expressNo,BigDecimal reOrderAmt){
    	try{
			if(StringUtils.isBlank(expressNo)){
				throw new Exception("快递单号不能为空");
			}
			if(reOrderAmt == null || !(reOrderAmt.compareTo(new BigDecimal(0)) >0)){
				throw new Exception("补单金额不正确");
			}
			
			orderInfoService.reOrder(request,expressNo,reOrderAmt);
			
			return AjaxResult.success("创建补单成功");
		}catch(Exception e){
			log.error("创建补单失败");
			return AjaxResult.fail(e.getMessage());
		}
    }
}
