package com.school.manager.controller.system;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.school.biz.constant.ConstantUrl;
import com.school.biz.domain.entity.help.Suggestion;
import com.school.biz.enumeration.ExpressTypeEnum;
import com.school.biz.exception.FuBusinessException;
import com.school.biz.service.help.SuggestionService;
import com.school.biz.util.pager.PageInfo;
import com.school.manager.controller.base.BaseEasyWebController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/suggestion")
public class SuggestionController extends BaseEasyWebController {

	@Autowired
	private SuggestionService suggestionService;
	
	{
		listView = "system/suggestion";
	}
	
	/**
	 * 分页查询
	 */
	@Override
	protected void onList(PageInfo pageInfo, Map<String, Object> searchParams, HttpServletRequest request,
						  ModelAndView mav) throws FuBusinessException {
		try {
			mav.addObject("listData",JSON.toJSON(suggestionService.queryPage(searchParams)));
			mav.addObject(PAGE_PARAM_PAGECOUNT, pageInfo.getTotalPage());
			mav.addObject(PAGE_PARAM_TOTALCOUNT, pageInfo.getTotalRecord());
			
			mav.addObject(ConstantUrl.DETAIL_URL, ConstantUrl.SUGGESTION_DETAIL_URL);// 详情url
		} catch (Exception e) {
			log.error("意见分页查询出现错误："+e.getMessage());
			throw webExp(e);
		}
	}
	
	/**
	 * 查看详情
	 */
	@RequestMapping("/detail.do")
	public ModelAndView detail(Long id){
		try{
    		ModelAndView mv = new ModelAndView("system/suggestionDetail");
    		Suggestion suggestionInfo = suggestionService.get(id);
    		mv.addObject("suggestion", JSON.toJSON(suggestionInfo));
    		return mv;
    	}catch(Exception e){
    		throw webExp(e);
    	}
	}
	
}
