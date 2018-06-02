package com.school.web.good;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.school.common.model.AjaxResult;
import com.school.constant.ConstantUrl;
import com.school.constant.StatusManage;
import com.school.domain.entity.good.GoodInfo;
import com.school.domain.vo.good.GoodInfoVo;
import com.school.service.good.GoodBrandService;
import com.school.service.good.GoodInfoService;
import com.school.service.good.GoodTypeService;
import com.school.util.core.exception.FuBusinessException;
import com.school.util.core.pager.PageInfo;
import com.school.web.base.BaseEasyWebController;

@Slf4j
@Controller
@RequestMapping(value = "/good/goodAuth")
public class GoodAuthController extends BaseEasyWebController {

	@Autowired
	private GoodInfoService goodInfoService;
	
	@Autowired
	private GoodTypeService goodTypeService;
	
	@Autowired
	private GoodBrandService goodBrandService;
	
	{
		listView = "good/goodAuth";
	}
	
	/**
	 * 分页查询
	 */
	@Override
	protected void onList(PageInfo pageInfo, Map<String, Object> searchParams, HttpServletRequest request,
			ModelAndView mav) throws FuBusinessException {
		try {
			if(searchParams.get("status") == null){
				searchParams.put("status", StatusManage.GOOD_INFO_STATUS_AUTH_WAIT);
			}
			mav.addObject("listData",JSON.toJSON(goodInfoService.queryAuthPage(searchParams)));
			mav.addObject(PAGE_PARAM_PAGECOUNT, pageInfo.getTotalPage());
			mav.addObject(PAGE_PARAM_TOTALCOUNT, pageInfo.getTotalRecord());
			
			mav.addObject(ConstantUrl.DETAIL_URL, ConstantUrl.GOOD_INFO_AUTH_DETAIL_URL);//详情页面
			mav.addObject("goodAuthUrl", ConstantUrl.GOOD_INFO_AUTH_URL);// 审核url
			
			mav.addObject("isPostMap",JSON.toJSON(StatusManage.isPostMap));
			mav.addObject("isShopGoodMap",JSON.toJSON(StatusManage.isShopGoodMap));
			mav.addObject("isRecommendMap",JSON.toJSON(StatusManage.isRecommendMap));
			mav.addObject("goodInfoAuthStatusMap",JSON.toJSON(StatusManage.goodInfoAuthStatusMap));
			
			mav.addObject("goodTypes",JSON.toJSON(goodTypeService.getEffectAll()));//商品类型
			mav.addObject("goodBrands",JSON.toJSON(goodBrandService.getAll()));//商品品牌
		} catch (Exception e) {
			log.error("商品审核查询出现错误："+e.getMessage());
			throw webExp(e);
		}
	}
	
	/**
	 * 查看详情
	 */
	@RequestMapping("/detail.do")
	public ModelAndView detail(Long id){
		try{
    		ModelAndView mv = new ModelAndView("good/goodInfoDetail");
    		GoodInfoVo goodInfo = goodInfoService.detail(id);
    		mv.addObject("goodInfo", JSON.toJSON(goodInfo));
    		
    		mv.addObject("isPostMap",JSON.toJSON(StatusManage.isPostMap));
			mv.addObject("isShopGoodMap",JSON.toJSON(StatusManage.isShopGoodMap));
			mv.addObject("isRecommendMap",JSON.toJSON(StatusManage.isRecommendMap));
			mv.addObject("goodInfoStatusMap",JSON.toJSON(StatusManage.goodInfoStatusMap));
			mv.addObject("goodLabels",JSON.toJSON(goodInfoService.getAllLabels()));//标签
    		return mv;
    	}catch(Exception e){
    		throw webExp(e);
    	}
	}
	
	/**
	 * 审核页面
	 */
	@RequestMapping(value = "/auth.do",method=RequestMethod.GET)
	public ModelAndView toAuth(Long id, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("good/goodAuthEdit");
		GoodInfoVo goodInfo = goodInfoService.detail(id);
		mv.addObject("goodAuth", JSON.toJSON(goodInfo));
		
		mv.addObject("isPostMap",JSON.toJSON(StatusManage.isPostMap));
		mv.addObject("isShopGoodMap",JSON.toJSON(StatusManage.isShopGoodMap));
		mv.addObject("isRecommendMap",JSON.toJSON(StatusManage.isRecommendMap));
		mv.addObject("goodInfoStatusMap",JSON.toJSON(StatusManage.goodInfoStatusMap));
		mv.addObject("goodLabels",JSON.toJSON(goodInfoService.getAllLabels()));//标签
		return mv;
	}
	
	/**
	 * 审核
	 */
	@ResponseBody
	@RequestMapping(value = "/auth.do", method = RequestMethod.POST)
	public AjaxResult auth(GoodInfo goodInfo){
		try{
			GoodInfo originalGoodInfo = goodInfoService.get(goodInfo.getId());
			if(!StatusManage.GOOD_INFO_STATUS_AUTH_WAIT.equals(originalGoodInfo.getStatus())){
				throw new Exception("商品状态不是待审核状态，无法进行审核");
			}
			goodInfoService.saveOrUpdate(goodInfo);
			return AjaxResult.success("审核成功", JSON.toJSON(goodInfo));
		}catch(Exception e){
			log.error("审核商品出错：" + e.getMessage());
			return AjaxResult.fail("审核失败");
		}
	}
	
}
