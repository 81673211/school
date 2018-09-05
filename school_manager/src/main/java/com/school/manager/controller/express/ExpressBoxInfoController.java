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
import com.school.biz.domain.entity.box.ExpressBoxInfo;
import com.school.biz.domain.entity.user.AdminUser;
import com.school.biz.enumeration.ExpressBoxInfoStatusEnum;
import com.school.biz.exception.FuBusinessException;
import com.school.biz.service.box.ExpressBoxInfoService;
import com.school.biz.util.pager.PageInfo;
import com.school.manager.controller.base.BaseEasyWebController;
import com.school.manager.util.SessionUtils;
import com.school.manager.vo.AjaxResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/expressBoxInfo")
public class ExpressBoxInfoController extends BaseEasyWebController {

	@Autowired
	private ExpressBoxInfoService expressBoxInfoService;
	
	{
		listView = "express/expressBoxInfo";
	}
	
	/**
	 * 分页查询
	 */
	@Override
	protected void onList(PageInfo pageInfo, Map<String, Object> searchParams, HttpServletRequest request,
						  ModelAndView mav) throws FuBusinessException {
		try {
			mav.addObject("listData",JSON.toJSON(expressBoxInfoService.queryPage(searchParams)));
			mav.addObject("expressBoxInfoStatusMap",JSON.toJSON(ExpressBoxInfoStatusEnum.getAllStatusEnum()));
//			mav.addObject("expressTypes", JSON.toJSON(ExpressTypeEnum.getAllTypeEnum()));
			mav.addObject(PAGE_PARAM_PAGECOUNT, pageInfo.getTotalPage());
			mav.addObject(PAGE_PARAM_TOTALCOUNT, pageInfo.getTotalRecord());
			
			mav.addObject(ConstantUrl.DETAIL_URL, ConstantUrl.EXPRESS_BOX_INFO_DETAIL_URL);// 详情url
			mav.addObject(ConstantUrl.EDIT_URL, ConstantUrl.EXPRESS_BOX_INFO_EDIT_URL);// 编辑url
			mav.addObject(ConstantUrl.DEL_URL,ConstantUrl.EXPRESS_BOX_INFO_DEL_URL);// 删除url
		} catch (Exception e) {
			log.error("柜子取件信息分页查询出现错误："+e.getMessage());
			throw webExp(e);
		}
	}
	
	/**
	 * 查看详情
	 */
	@RequestMapping("/detail.do")
	public ModelAndView detail(Long id){
		try{
    		ModelAndView mv = new ModelAndView("express/expressBoxInfoDetail");
    		ExpressBoxInfo expressBoxInfo = expressBoxInfoService.get(id);
    		mv.addObject("expressBoxInfo", JSON.toJSON(expressBoxInfo));
    		mv.addObject("expressBoxInfoStatusMap",JSON.toJSON(ExpressBoxInfoStatusEnum.getAllStatusEnum()));
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
		mav.setViewName("express/expressBoxInfoEdit");
		ExpressBoxInfo expressBoxInfo = expressBoxInfoService.get(id);
		mav.addObject("expressBoxInfo", JSON.toJSON(expressBoxInfo));
		mav.addObject("expressBoxInfoStatusMap",JSON.toJSON(ExpressBoxInfoStatusEnum.getAllStatusEnum()));
		return mav;
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save.do")
	public AjaxResult save(HttpServletRequest request,ExpressBoxInfo expressBoxInfo){
		try{
			if(StringUtils.isBlank(expressBoxInfo.getBoxNo())){
				return AjaxResult.fail("请填写柜子编号");
			}
			AdminUser sessionUser = SessionUtils.getSessionUser(request);
			expressBoxInfoService.saveOrUpdate(expressBoxInfo,sessionUser);
			return AjaxResult.success("保存成功", JSON.toJSON(expressBoxInfo));
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
			expressBoxInfoService.deleteById(id);
			return AjaxResult.success("删除成功");
		} catch (Exception e) {
			log.error("删除快递公司出错：" + e.getMessage());
			return AjaxResult.fail("删除失败");
		}
	}
	
}
