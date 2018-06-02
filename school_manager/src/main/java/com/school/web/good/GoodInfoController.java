package com.school.web.good;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.school.common.model.AjaxResult;
import com.school.constant.ConstantMap;
import com.school.constant.ConstantUrl;
import com.school.constant.Constants;
import com.school.constant.StatusManage;
import com.school.domain.entity.good.GoodImage;
import com.school.domain.entity.good.GoodInfo;
import com.school.domain.vo.good.GoodInfoVo;
import com.school.service.good.GoodBrandService;
import com.school.service.good.GoodInfoService;
import com.school.service.good.GoodTypeService;
import com.school.util.core.exception.FuBusinessException;
import com.school.util.core.pager.PageInfo;
import com.school.util.core.utils.BeanUtils;
import com.school.util.core.utils.DateUtil;
import com.school.util.core.utils.FileUtil;
import com.school.util.core.utils.StrUtil;
import com.school.web.base.BaseEasyWebController;

@Slf4j
@Controller
@RequestMapping(value = "/good/goodInfo")
public class GoodInfoController extends BaseEasyWebController {

	@Autowired
	private GoodInfoService goodInfoService;
	
	@Autowired
	private GoodTypeService goodTypeService;
	
	@Autowired
	private GoodBrandService goodBrandService;
	
	{
		listView = "good/goodInfo";
	}
	
	/**
	 * 分页查询
	 */
	@Override
	protected void onList(PageInfo pageInfo, Map<String, Object> searchParams, HttpServletRequest request,
			ModelAndView mav) throws FuBusinessException {
		try {
			mav.addObject("listData",JSON.toJSON(goodInfoService.queryPage(searchParams)));
			mav.addObject(PAGE_PARAM_PAGECOUNT, pageInfo.getTotalPage());
			mav.addObject(PAGE_PARAM_TOTALCOUNT, pageInfo.getTotalRecord());
			
			mav.addObject(ConstantUrl.DETAIL_URL, ConstantUrl.GOOD_INFO_DETAIL_URL);// 详情url
			mav.addObject(ConstantUrl.EDIT_URL, ConstantUrl.GOOD_INFO_EDIT_URL);// 编辑url
			mav.addObject(ConstantUrl.DEL_URL,ConstantUrl.GOOD_INFO_DEL_URL);// 删除url
			mav.addObject("upAndDownUrl",ConstantUrl.GOOD_INFO_UP_AND_DOWN_URL);// 上下架url
			mav.addObject("upAndDownAllUrl",ConstantUrl.GOOD_INFO_UP_AND_DOWN_ALL_URL);// 批量上架/下架url
			mav.addObject(ConstantUrl.EXPORT_DATA_URL,ConstantUrl.GOOD_INFO_EXPORT_DATA_URL);//导出数据
			
			mav.addObject("isPostMap",JSON.toJSON(StatusManage.isPostMap));
			mav.addObject("isShopGoodMap",JSON.toJSON(StatusManage.isShopGoodMap));
			mav.addObject("isRecommendMap",JSON.toJSON(StatusManage.isRecommendMap));
			mav.addObject("goodInfoStatusMap",JSON.toJSON(StatusManage.goodInfoStatusMap));
			
			mav.addObject("goodTypes",JSON.toJSON(goodTypeService.getEffectAll()));//商品类型
			mav.addObject("goodBrands",JSON.toJSON(goodBrandService.getAll()));//商品品牌
		} catch (Exception e) {
			log.error("商品分页查询出现错误："+e.getMessage());
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
	 * 编辑页面
	 */
	@RequestMapping(value = "/edit.do")
	public ModelAndView edit(Long id, HttpServletRequest request){
		ModelAndView mav= new ModelAndView();
		mav.setViewName("good/goodInfoEdit");
		GoodInfoVo goodInfo = goodInfoService.selectById(id);
		if(goodInfo == null){
			goodInfo = new GoodInfoVo();
		}
		mav.addObject("goodInfo", JSON.toJSON(goodInfo));
		mav.addObject("isPostMap",JSON.toJSON(StatusManage.isPostMap));
		mav.addObject("isShopGoodMap",JSON.toJSON(StatusManage.isShopGoodMap));
		mav.addObject("isRecommendMap",JSON.toJSON(StatusManage.isRecommendMap));
		mav.addObject("goodInfoStatusMap",JSON.toJSON(StatusManage.goodInfoStatusMap));
		
		mav.addObject("goodTypes",JSON.toJSON(goodTypeService.getEffectAll()));//商品类型
		mav.addObject("goodBrands",JSON.toJSON(goodBrandService.getAll()));//商品品牌
		mav.addObject("goodLabels",JSON.toJSON(goodInfoService.getAllLabels()));//标签
		return mav;
	}
	
	/**
	 * 保存前校验是否需要重审，如果需要则提示前台
	 */
	@ResponseBody
	@RequestMapping("/checkNeedAuthBefore")
	public boolean checkNeedAuthBefore(GoodInfo goodInfo){
		if(goodInfo.getId() == null){
			// 如果是新增商品，需要审核
			return true;
		}
		GoodInfo originalGoodInfo = goodInfoService.get(goodInfo.getId());
		boolean result = this.checkNeedAuth(originalGoodInfo, goodInfo);
		return result;
			
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save.do")
	public AjaxResult save(@RequestBody GoodInfo goodInfo,HttpServletRequest request){
		try{
			if(StrUtil.isBlank(goodInfo.getGoodName())){
				return AjaxResult.fail("请填写商品名称");
			}
			//修改
			if(goodInfo.getId() != null){
				GoodInfo originalGoodInfo = goodInfoService.get(goodInfo.getId());
				if(originalGoodInfo==null || StatusManage.GOOD_INFO_STATUS_UP.equals(originalGoodInfo.getStatus())){
					return AjaxResult.fail("当前商品处于上架状态，不能修改");
				}
			}
			if(goodInfo.getId() == null){
				// 新增时，默认商品状态为待审核状态
				goodInfo.setStatus(StatusManage.GOOD_INFO_STATUS_AUTH_WAIT);
			}else{
				// 修改时，需判断是否需要重审
				GoodInfo originalGoodInfo = goodInfoService.get(goodInfo.getId());
				boolean needAuth = this.checkNeedAuth(goodInfo,originalGoodInfo);
				if(needAuth){
					goodInfo.setStatus(StatusManage.GOOD_INFO_STATUS_AUTH_WAIT);
				}
			}
			
			goodInfoService.saveGood(goodInfo);
			
			return AjaxResult.success("保存成功", JSON.toJSON(goodInfo));
		}catch(Exception e){
			log.error("保存商品出错：" + e.getMessage());
			return AjaxResult.fail("保存失败");
		}
	}
	
	/**
	 * 修改时检查是否需要重审
	 */
	private boolean checkNeedAuth(GoodInfo originalGoodInfo, GoodInfo goodInfo){
		try {
			// 需要重审的检查项：商品编号、商品名称、进货价格、原价、现价、现价对应积分
			String[] checkItems = {"goodNo","goodName","purchasePrice","oldPrice","price","goodPoints"};
			
			List<String> itemList = Arrays.asList(checkItems);
			for (String item : itemList) {
				Object originValue = BeanUtils.forceGetProperty(originalGoodInfo, item);
				Object value = BeanUtils.forceGetProperty(goodInfo, item);
				if(originValue != null && value != null && !originValue.toString().equals(value.toString())){
					log.info("商品id:" + originalGoodInfo.getId() + ",修改后需要发起重审。" + "检查项：" + item + " ,原值为：" + originValue.toString() + " ,新值为：" + value.toString());
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			// 只有BeanUtils.forceGetProperty会出异常，而且最可能的就是找不到检查项对应的属性名
			log.error("保存商品时，检查是否需要重审时出现异常：" + e.getMessage());
			return true;
		}
	}
	
	/**
	 * webuploader上传图片
	 */
	@ResponseBody
	@RequestMapping("/uploadGoodImage")
	public AjaxResult uploadGoodImage(HttpServletRequest request){
		try {
			String goodNo = request.getParameter("goodNo");
			if(StringUtils.isBlank(goodNo)){
				throw new Exception("商品编号不能为空");
			}
			
			String bigImgPath = "";
			String smallImgPath = "";
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
				String key = (String) it.next();
				// 取出文件
				MultipartFile mulfile = multipartRequest.getFile(key);
				long size=mulfile.getSize();
				if (size/1024>5*1024) {
					throw new Exception("图片不能大于5M");
				}
				if(mulfile!=null){
					String matchPath = Constants.UPLOAD_FILE_PATH + ConstantMap.GOOD_INFO_IMG_PATH + goodNo + File.separator;
					String [] types={".jpg",".png"};
					String bigImgName = DateUtil.formatFileDatetime(new Date())+StrUtil.randNumber(3);
					String smallImgName = bigImgName + "_small";
					File file=FileUtil.uploadfile(request, matchPath, types, key, bigImgName);
					bigImgPath = "/upload"+ConstantMap.GOOD_INFO_IMG_PATH + goodNo + File.separator + file.getName();
					// 利用Thumbnails来缩放图片
					
					String fullSmallImgName = smallImgName+"."+FileUtil.getExtension(file.getName());
					Thumbnails.of(file.getPath()).scale(0.25f).toFile(matchPath+fullSmallImgName);
					smallImgPath = "/upload"+ConstantMap.GOOD_INFO_IMG_PATH + goodNo + File.separator + fullSmallImgName;
				}
			}
			GoodImage goodImage = new GoodImage(bigImgPath, smallImgPath);
			return AjaxResult.success("上传成功",goodImage);
		} catch (Exception e) {
			log.error(e.getMessage());
            return AjaxResult.fail("上传失败");
		}
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/del.do")
	public AjaxResult del(Long id){
		try {
			goodInfoService.deleteById(id);
			return AjaxResult.success("删除成功");
		} catch (Exception e) {
			log.error("删除商品出错：" + e.getMessage());
			return AjaxResult.fail("删除失败");
		}
	}
	
	/**
	 * 上架/下架
	 */
	@ResponseBody
	@RequestMapping("/upAndDown.do")
	public AjaxResult upAndDown(Long id){
		String opt = "";
		try {
			GoodInfo goodInfo = goodInfoService.get(id);
			if(goodInfo == null){
				return AjaxResult.fail("没有查到该产品");
			}
			
			// 如果原商品状态为上架，则为下架操作
			if(StatusManage.GOOD_INFO_STATUS_UP.equals(goodInfo.getStatus())){
				opt = "下架";
				goodInfo.setDownTime(DateUtil.now());
				goodInfo.setStatus(StatusManage.GOOD_INFO_STATUS_DOWN);
			}else if(StatusManage.GOOD_INFO_STATUS_DOWN.equals(goodInfo.getStatus()) || StatusManage.GOOD_INFO_STATUS_AUTH_SUCCESS.equals(goodInfo.getStatus())){
				// 如果原商品状态为下架或审核通过，则为上架操作
				opt = "上架";
				goodInfo.setPublishTime(DateUtil.now());
				goodInfo.setStatus(StatusManage.GOOD_INFO_STATUS_UP);
			}else{
				log.error("商品上架/下架操作异常，商品信息:"+JSON.toJSON(goodInfo));
				return AjaxResult.fail("系统异常");
			}
			goodInfoService.update(goodInfo);
			return AjaxResult.success(opt + "成功");
		} catch (Exception e) {
			log.error(opt + "出错：" + e.getMessage());
			return AjaxResult.fail(opt + "失败");
		}
	}
	
	/**
	 * 批量上架/下架
	 */
	@ResponseBody
	@RequestMapping("/upAndDownAll.do")
	public AjaxResult upAll(String ids,Integer isUpAll){
		String opt = "";
		try {
			if(isUpAll == StatusManage.IS_UP_ALL_YES){
				opt = "批量上架";
			}else{
				opt = "批量下架";
			}
			
			if(StringUtils.isBlank(ids)){
				throw new Exception("请选择需要"+opt+"的产品");
			}
			// 将ids解析成集合
			String[] idsArr = ids.split(",");
			List<String> idList = Arrays.asList(idsArr);
			
			// 筛选出可以上架/下架的产品
			List<Long> realIdList = goodInfoService.chooseGoodId(idList,isUpAll);
			
			// 批量上架/下架
			if(realIdList != null && realIdList.size() > 0){
				goodInfoService.upAndDownAll(realIdList,isUpAll);
			}
			return AjaxResult.success(opt + "成功");
		} catch (Exception e) {
			log.error(opt + "出错：" + e.getMessage());
			return AjaxResult.fail(opt + "失败");
		}
	}
	
	@RequestMapping(value = "/exportData.do")
	public void export(HttpServletRequest request, HttpServletResponse response) throws FuBusinessException {
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "search_");
		
		List<GoodInfoVo> list = goodInfoService.queryPage(searchParams);
		
		if (list.size() > 0) {
			downLoadExcle(request, response, "商品信息", GoodInfoVo.class, list);
		}

	}
	
}
