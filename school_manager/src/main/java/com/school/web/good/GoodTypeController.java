package com.school.web.good;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.school.common.model.AjaxResult;
import com.school.constant.ConstantUrl;
import com.school.constant.StatusManage;
import com.school.domain.entity.good.GoodType;
import com.school.service.good.GoodTypeService;
import com.school.util.core.exception.FuBusinessException;
import com.school.util.core.pager.PageInfo;
import com.school.util.core.utils.StrUtil;
import com.school.web.base.BaseEasyWebController;

@Slf4j
@Controller
@RequestMapping(value = "/good/goodType")
public class GoodTypeController extends BaseEasyWebController {

	@Autowired
	private GoodTypeService goodTypeService;
	
	{
		listView = "good/goodType";
	}
	
	/**
	 * 分页查询
	 */
	@Override
	protected void onList(PageInfo pageInfo, Map<String, Object> searchParams, HttpServletRequest request,
			ModelAndView mav) throws FuBusinessException {
		try {
			mav.addObject("listData",JSON.toJSON(goodTypeService.queryPage(searchParams)));
			mav.addObject("goodTypeStatusMap",JSON.toJSON(StatusManage.goodTypeStatusMap));
			mav.addObject(PAGE_PARAM_PAGECOUNT, pageInfo.getTotalPage());
			mav.addObject(PAGE_PARAM_TOTALCOUNT, pageInfo.getTotalRecord());
			
			mav.addObject(ConstantUrl.DETAIL_URL, ConstantUrl.GOOD_TYPE_DETAIL_URL);// 详情url
			mav.addObject(ConstantUrl.EDIT_URL, ConstantUrl.GOOD_TYPE_EDIT_URL);// 编辑url
			mav.addObject(ConstantUrl.DEL_URL,ConstantUrl.GOOD_TYPE_DEL_URL);// 删除url
		} catch (Exception e) {
			log.error("商品分类分页查询出现错误："+e.getMessage());
			throw webExp(e);
		}
	}
	
	/**
	 * 查看详情
	 */
	@RequestMapping("/detail.do")
	public ModelAndView detail(Long id){
		try{
    		ModelAndView mv = new ModelAndView("good/goodTypeDetail");
    		GoodType goodType = goodTypeService.get(id);
    		mv.addObject("goodType", JSON.toJSON(goodType));
    		mv.addObject("goodTypeStatusMap",JSON.toJSON(StatusManage.goodTypeStatusMap));
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
		mav.setViewName("good/goodTypeEdit");
		GoodType goodType = goodTypeService.get(id);
		mav.addObject("goodType", JSON.toJSON(goodType));
		mav.addObject("goodTypeStatusMap",JSON.toJSON(StatusManage.goodTypeStatusMap));
		return mav;
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save.do")
	public AjaxResult save(GoodType goodType){
		try{
			if(StrUtil.isBlank(goodType.getTypeName())){
				return AjaxResult.fail("请填写类型名称");
			}
			goodTypeService.saveOrUpdate(goodType);
			return AjaxResult.success("保存成功", JSON.toJSON(goodType));
		}catch(Exception e){
			log.error("保存商品类型出错：" + e.getMessage());
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
			goodTypeService.deleteById(id);
			return AjaxResult.success("删除成功");
		} catch (Exception e) {
			log.error("删除商品类型出错：" + e.getMessage());
			return AjaxResult.fail("删除失败");
		}
	}
}
