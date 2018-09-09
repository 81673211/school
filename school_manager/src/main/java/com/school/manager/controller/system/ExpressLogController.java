package com.school.manager.controller.system;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.school.biz.constant.ConstantUrl;
import com.school.biz.domain.entity.log.ExpressLog;
import com.school.biz.enumeration.ExpressTypeEnum;
import com.school.biz.enumeration.OrderStatusEnum;
import com.school.biz.exception.FuBusinessException;
import com.school.biz.service.log.ExpressLogService;
import com.school.biz.util.pager.PageInfo;
import com.school.manager.controller.base.BaseEasyWebController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/expressLog")
public class ExpressLogController extends BaseEasyWebController {

	@Autowired
	private ExpressLogService expressLogService;
	
	{
		listView = "system/expressLog";
	}
	
	/**
	 * 分页查询
	 */
	@Override
	protected void onList(PageInfo pageInfo, Map<String, Object> searchParams, HttpServletRequest request,
						  ModelAndView mav) throws FuBusinessException {
		try {
			mav.addObject("listData",JSON.toJSON(expressLogService.queryPage(searchParams)));
			mav.addObject("expressTypes", JSON.toJSON(ExpressTypeEnum.getAllTypeEnum()));
			mav.addObject(PAGE_PARAM_PAGECOUNT, pageInfo.getTotalPage());
			mav.addObject(PAGE_PARAM_TOTALCOUNT, pageInfo.getTotalRecord());
			
			mav.addObject(ConstantUrl.DETAIL_URL, ConstantUrl.EXPRESS_LOG_DETAIL_URL);// 详情url
		} catch (Exception e) {
			log.error("日志分页查询出现错误："+e.getMessage());
			throw webExp(e);
		}
	}
	
	/**
	 * 查看详情
	 */
	@RequestMapping("/detail.do")
	public ModelAndView detail(Long id){
		try{
    		ModelAndView mv = new ModelAndView("system/expressLogDetail");
    		ExpressLog expressLogInfo = expressLogService.get(id);
    		mv.addObject("expressLog", JSON.toJSON(expressLogInfo));
    		mv.addObject("expressTypes", JSON.toJSON(ExpressTypeEnum.getAllTypeEnum()));
    		return mv;
    	}catch(Exception e){
    		throw webExp(e);
    	}
	}
	
}
