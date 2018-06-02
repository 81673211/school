package com.school.service.good;

import java.util.List;
import java.util.Map;

import com.school.dao.good.GoodInfoMapper;
import com.school.domain.entity.good.GoodInfo;
import com.school.domain.vo.good.GoodInfoVo;
import com.school.service.base.BaseService;

public interface GoodInfoService extends BaseService<GoodInfo, GoodInfoMapper> {

	/**
	 * 商品列表页面
	 */
	List<GoodInfoVo> queryPage(Map<String,Object> paramMap);

	void saveOrUpdate(GoodInfo goodInfo);

	GoodInfoVo detail(Long id);
	
	/**
	 * 筛选能上架/下架的产品:
	 *    上架：产品状态为审核通过或下架
	 *    下架：产品状态为上架
	 */
	List<Long> chooseGoodId(List<String> idList, Integer isUpAll);
	
	/**
	 * 批量上架/下架
	 */
	void upAndDownAll(List<Long> idList,Integer isUpAll);

	/**
	 * 审核页面
	 */
	List<GoodInfoVo> queryAuthPage(Map<String, Object> searchParams);

	/**
	 * 保存商品信息
	 * @throws Exception 
	 */
	void saveGood(GoodInfo goodInfo) throws Exception;

	/** 根据id获取 */
	GoodInfoVo selectById(Long id);

	List<Map<String, Object>> getAllLabels();

}
