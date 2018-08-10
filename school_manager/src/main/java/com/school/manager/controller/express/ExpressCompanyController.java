package com.school.manager.controller.express;

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
import com.school.biz.exception.FuBusinessException;
import com.school.biz.util.pager.PageInfo;
import com.school.manager.vo.AjaxResult;
import com.school.biz.domain.entity.express.ExpressCompany;
import com.school.biz.domain.entity.user.AdminUser;
import com.school.biz.service.express.ExpressCompanyService;
import com.school.manager.util.SessionUtils;
import com.school.manager.controller.base.BaseEasyWebController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/expressCompany")
public class ExpressCompanyController extends BaseEasyWebController {

	@Autowired
	private ExpressCompanyService expressCompanyService;
	
	{
		listView = "express/expressCompany";
	}
	
	/**
	 * 分页查询
	 */
	@Override
	protected void onList(PageInfo pageInfo, Map<String, Object> searchParams, HttpServletRequest request,
						  ModelAndView mav) throws FuBusinessException {
		try {
			mav.addObject("listData",JSON.toJSON(expressCompanyService.queryPage(searchParams)));
//			mav.addObject("expressCompanyStatusMap",JSON.toJSON(StatusManage.expressCompanyStatusMap));
			mav.addObject(PAGE_PARAM_PAGECOUNT, pageInfo.getTotalPage());
			mav.addObject(PAGE_PARAM_TOTALCOUNT, pageInfo.getTotalRecord());
			
			mav.addObject(ConstantUrl.DETAIL_URL, ConstantUrl.EXPRESS_COMPANY_DETAIL_URL);// 详情url
			mav.addObject(ConstantUrl.EDIT_URL, ConstantUrl.EXPRESS_COMPANY_EDIT_URL);// 编辑url
			mav.addObject(ConstantUrl.DEL_URL,ConstantUrl.EXPRESS_COMPANY_DEL_URL);// 删除url
		} catch (Exception e) {
			log.error("快递公司分页查询出现错误："+e.getMessage());
			throw webExp(e);
		}
	}
	
	/**
	 * 查看详情
	 */
	@RequestMapping("/detail.do")
	public ModelAndView detail(Long id){
		try{
    		ModelAndView mv = new ModelAndView("express/expressCompanyDetail");
    		ExpressCompany expressCompany = expressCompanyService.get(id);
    		mv.addObject("expressCompany", JSON.toJSON(expressCompany));
//    		mv.addObject("expressCompanyStatusMap",JSON.toJSON(StatusManage.expressCompanyStatusMap));
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
		mav.setViewName("express/expressCompanyEdit");
		ExpressCompany expressCompany = expressCompanyService.get(id);
		mav.addObject("expressCompany", JSON.toJSON(expressCompany));
//		mav.addObject("expressCompanyStatusMap",JSON.toJSON(StatusManage.expressCompanyStatusMap));
		return mav;
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save.do")
	public AjaxResult save(HttpServletRequest request,ExpressCompany expressCompany){
		try{
			if(StringUtils.isBlank(expressCompany.getName())){
				return AjaxResult.fail("请填写快递公司名称");
			}
			AdminUser sessionUser = SessionUtils.getSessionUser(request);
			expressCompanyService.saveOrUpdate(expressCompany,sessionUser);
			return AjaxResult.success("保存成功", JSON.toJSON(expressCompany));
		}catch(Exception e){
			log.error("保存快递公司出错：" + e.getMessage());
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
			expressCompanyService.deleteById(id);
			return AjaxResult.success("删除成功");
		} catch (Exception e) {
			log.error("删除快递公司出错：" + e.getMessage());
			return AjaxResult.fail("删除失败");
		}
	}
}
