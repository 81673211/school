package com.school.web.customer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.school.common.model.AjaxResult;
import com.school.constant.ConstantUrl;
import com.school.domain.entity.customer.Customer;
import com.school.service.customer.CustomerService;
import com.school.util.core.exception.FuBusinessException;
import com.school.util.core.pager.PageInfo;
import com.school.web.base.BaseEasyWebController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/customer")
public class CustomerController extends BaseEasyWebController {

	@Autowired
	private CustomerService customerService;
	
	{
		listView = "customer/customer";
	}
	
	/**
	 * 分页查询
	 */
	@Override
	protected void onList(PageInfo pageInfo, Map<String, Object> searchParams, HttpServletRequest request,
			ModelAndView mav) throws FuBusinessException {
		try {
			mav.addObject("listData",JSON.toJSON(customerService.queryPage(searchParams)));
//			mav.addObject("customerStatusMap",JSON.toJSON(StatusManage.customerStatusMap));
			mav.addObject(PAGE_PARAM_PAGECOUNT, pageInfo.getTotalPage());
			mav.addObject(PAGE_PARAM_TOTALCOUNT, pageInfo.getTotalRecord());
			
			mav.addObject(ConstantUrl.DETAIL_URL, ConstantUrl.CUSTOMER_DETAIL_URL);// 详情url
			mav.addObject(ConstantUrl.EDIT_URL, ConstantUrl.CUSTOMER_EDIT_URL);// 编辑url
			mav.addObject(ConstantUrl.DEL_URL,ConstantUrl.CUSTOMER_DEL_URL);// 删除url
		} catch (Exception e) {
			log.error("客户分页查询出现错误："+e.getMessage());
			throw webExp(e);
		}
	}
	
	/**
	 * 查看详情
	 */
	@RequestMapping("/detail.do")
	public ModelAndView detail(Long id){
		try{
    		ModelAndView mv = new ModelAndView("customer/customerDetail");
    		Customer customer = customerService.get(id);
    		mv.addObject("customer", JSON.toJSON(customer));
//    		mv.addObject("customerStatusMap",JSON.toJSON(StatusManage.customerStatusMap));
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
		mav.setViewName("customer/customerEdit");
		Customer customer = customerService.get(id);
		mav.addObject("customer", JSON.toJSON(customer));
//		mav.addObject("customerStatusMap",JSON.toJSON(StatusManage.customerStatusMap));
		return mav;
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save.do")
	public AjaxResult save(Customer customer){
		try{
			if(StringUtils.isBlank(customer.getName())){
				return AjaxResult.fail("请填写名字");
			}
			customerService.saveOrUpdate(customer);
			return AjaxResult.success("保存成功", JSON.toJSON(customer));
		}catch(Exception e){
			log.error("保存客户出错：" + e.getMessage());
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
			customerService.deleteById(id);
			return AjaxResult.success("删除成功");
		} catch (Exception e) {
			log.error("删除客户出错：" + e.getMessage());
			return AjaxResult.fail("删除失败");
		}
	}
}
