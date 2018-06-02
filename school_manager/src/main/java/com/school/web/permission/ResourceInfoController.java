package com.school.web.permission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.school.common.model.AjaxResult;
import com.school.constant.ConstantUrl;
import com.school.domain.entity.resource.ResourceInfo;
import com.school.domain.vo.resource.ResourceInfoVo;
import com.school.service.resource.ResourceInfoService;
import com.school.service.security.MenuInfoService;
import com.school.util.core.exception.FuBusinessException;
import com.school.util.core.pager.PageInfo;
import com.school.util.core.utils.StrUtil;
import com.school.web.base.BaseEasyWebController;

@Slf4j
@Controller
@RequestMapping(value = "/permission/resourceInfo")
public class ResourceInfoController extends BaseEasyWebController {

	@Autowired
	private ResourceInfoService resourceInfoService;
	
	@Autowired
	private MenuInfoService menuInfoService;
	
	{
		listView = "permission/resourceInfo";
	}
	
	/**
	 * 分页查询
	 */
	@Override
	protected void onList(PageInfo pageInfo, Map<String, Object> searchParams, HttpServletRequest request,
			ModelAndView mav) throws FuBusinessException {
		try {
			// 如果筛选框传入一级菜单，需要将该一级菜单转换为对应的二级菜单集合（包含自己）
			List<Long> ownMenuIdList = menuInfoService.getOwnMenuIdList(searchParams.get("menuId"));
			searchParams.put("ownMenuIdList", ownMenuIdList);
			mav.addObject("listData",JSON.toJSON(resourceInfoService.queryPage(searchParams)));
			mav.addObject(PAGE_PARAM_PAGECOUNT, pageInfo.getTotalPage());
			mav.addObject(PAGE_PARAM_TOTALCOUNT, pageInfo.getTotalRecord());
			
			mav.addObject(ConstantUrl.DETAIL_URL, ConstantUrl.RESOURCE_INFO_DETAIL_URL);// 详情url
			mav.addObject(ConstantUrl.EDIT_URL, ConstantUrl.RESOURCE_INFO_EDIT_URL);// 编辑url
			mav.addObject(ConstantUrl.DEL_URL,ConstantUrl.RESOURCE_INFO_DEL_URL);// 删除url
			
			mav.addObject("parentResources",JSON.toJSON(resourceInfoService.getAllParentResources()));
			mav.addObject("menus",JSON.toJSON(menuInfoService.getAll()));
		} catch (Exception e) {
			log.error("资源分页查询出现错误："+e.getMessage());
			throw webExp(e);
		}
	}
	
	/**
	 * 查看详情
	 */
	@RequestMapping("/detail.do")
	public ModelAndView detail(Long id){
		try{
    		ModelAndView mv = new ModelAndView("permission/resourceInfoDetail");
    		ResourceInfoVo resourceInfo = resourceInfoService.detail(id);
    		mv.addObject("resourceInfo", JSON.toJSON(resourceInfo));
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
		mav.setViewName("permission/resourceInfoEdit");
		ResourceInfo resourceInfo = resourceInfoService.get(id);
		mav.addObject("resourceInfo", JSON.toJSON(resourceInfo));
		
		mav.addObject("parentResources",JSON.toJSON(resourceInfoService.getAllParentResources()));
		mav.addObject("menus",JSON.toJSON(menuInfoService.getAll()));
		return mav;
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save.do")
	public AjaxResult save(ResourceInfo resourceInfo){
		try{
			if(StrUtil.isBlank(resourceInfo.getResName())){
				return AjaxResult.fail("请填写资源名称");
			}
			resourceInfoService.saveOrUpdate(resourceInfo);
			return AjaxResult.success("保存成功", JSON.toJSON(resourceInfo));
		}catch(Exception e){
			log.error("保存资源出错：" + e.getMessage());
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
			resourceInfoService.deleteById(id);
			return AjaxResult.success("删除成功");
		} catch (Exception e) {
			log.error("删除资源出错：" + e.getMessage());
			return AjaxResult.fail("删除失败");
		}
	}
	
	/**
	 * 判断资源名称是否不重复（如果是修改，则还需要判断是否真的更改）
	 */
	@ResponseBody
	@RequestMapping("/checkResNameIsNotExist")
	public Object checkResNameIsNotExist(Long resId,String resName){
		return resourceInfoService.checkResNameIsNotExist(resId,resName);
	}
	
	/**
	 * 判断资源Url是否不重复（如果是修改，则还需要判断是否真的更改）
	 */
	@ResponseBody
	@RequestMapping("/checkResUrlIsNotExist")
	public Object checkResUrlIsNotExist(Long resId,String resUrl){
		return resourceInfoService.checkResUrlIsNotExist(resId,resUrl);
	}
}
