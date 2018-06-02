package com.school.service.good.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.constant.ConstantMap;
import com.school.constant.Constants;
import com.school.constant.StatusManage;
import com.school.dao.good.GoodInfoMapper;
import com.school.domain.entity.good.GoodInfo;
import com.school.domain.vo.good.GoodInfoVo;
import com.school.service.base.impl.BaseServiceImpl;
import com.school.service.good.GoodInfoService;
import com.school.util.core.utils.DateUtil;
import com.school.util.core.utils.FileUtil;

@Service
public class GoodInfoServiceImpl extends BaseServiceImpl<GoodInfo, GoodInfoMapper> implements GoodInfoService {

	@Autowired
	private GoodInfoMapper goodInfoMapper;
	
	@Override
	public List<GoodInfoVo> queryPage(Map<String, Object> paramMap) {
		return goodInfoMapper.queryPage(paramMap);
	}
	
	@Override
	public void saveOrUpdate(GoodInfo goodInfo) {
		if(goodInfo.getId() == null){
			this.save(goodInfo);
		}else{
			this.update(goodInfo);
		}
	}

	@Override
	public GoodInfoVo detail(Long id) {
		return goodInfoMapper.detail(id);
	}

	@Override
	public List<Long> chooseGoodId(List<String> idList, Integer isUpAll) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idList", idList);
		map.put("goodInfoStatusAuthSuccess", StatusManage.GOOD_INFO_STATUS_AUTH_SUCCESS);
		map.put("goodInfoStatusUp", StatusManage.GOOD_INFO_STATUS_UP);
		map.put("goodInfoStatusDown", StatusManage.GOOD_INFO_STATUS_DOWN);
		map.put("isUpAll", isUpAll);
		return goodInfoMapper.chooseGoodId(map);
	}

	@Override
	public void upAndDownAll(List<Long> idList, Integer isUpAll) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idList", idList);
		map.put("now", DateUtil.now());
		map.put("goodInfoStatusUp", StatusManage.GOOD_INFO_STATUS_UP);
		map.put("goodInfoStatusDown", StatusManage.GOOD_INFO_STATUS_DOWN);
		map.put("isUpAll", isUpAll);
		goodInfoMapper.upAndDownAll(map);
	}

	@Override
	public List<GoodInfoVo> queryAuthPage(Map<String, Object> searchParams) {
		return goodInfoMapper.queryAuthPage(searchParams);
	}

	@Override
	public void saveGood(GoodInfo goodInfo) throws Exception {
		
		String originalGoodNo = "";
		String nowGoodNo = "";
		
		// 保存产品信息
		if(goodInfo.getId() == null){
			goodInfoMapper.insertSelective(goodInfo);
		}else{
			GoodInfo originalGoodInfo = goodInfoMapper.selectByPrimaryKey(goodInfo.getId());
			originalGoodNo = originalGoodInfo.getGoodNo();
			nowGoodNo = goodInfo.getGoodNo();
			goodInfoMapper.updateByPrimaryKeySelective(goodInfo);
			// 删除之前的商品图片
			goodInfoMapper.delGoodImagesById(goodInfo.getId());
			// 删除之前的标签
			goodInfoMapper.delGoodLabelById(goodInfo.getId());
		}
		// 批量插入商品图片
		if(goodInfo.getGoodImages() != null && goodInfo.getGoodImages().size() > 0){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("goodId", goodInfo.getId());
			map.put("goodImages", goodInfo.getGoodImages());
			goodInfoMapper.insertImagesOnce(map);
		}
		
		// 批量插入标签
		if(goodInfo.getLabels() != null && goodInfo.getLabels().size() > 0){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("goodId", goodInfo.getId());
			map.put("goodLabels", goodInfo.getLabels());
			goodInfoMapper.insertLabelOnce(map);
		}
		
		// 检查goodNo是否更改，如果有更改则将原图片文件夹移动到新的goodNo下，并删除之前的。
		if(goodInfo.getId() != null && !originalGoodNo.equals(nowGoodNo)){
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("goodId", goodInfo.getId());
			map.put("originalGoodNo", originalGoodNo);
			map.put("nowGoodNo", nowGoodNo);
			
			// 替换图片路径
			goodInfoMapper.replaceGoodNoFromImgPath(map);
			
			File originalGoodImgFile = new File(Constants.UPLOAD_FILE_PATH + ConstantMap.GOOD_INFO_IMG_PATH + originalGoodNo + File.separator);
			File nowGoodImgFile = new File(Constants.UPLOAD_FILE_PATH + ConstantMap.GOOD_INFO_IMG_PATH + nowGoodNo + File.separator);
			FileUtil.move(originalGoodImgFile, nowGoodImgFile, true);
			
		}
	}

	@Override
	public GoodInfoVo selectById(Long id) {
		return goodInfoMapper.selectById(id);
	}

	@Override
	public List<Map<String, Object>> getAllLabels() {
		return goodInfoMapper.getAllLabels();
	}

}
