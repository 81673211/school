package com.school.dao.good;

import java.util.List;
import java.util.Map;

import com.school.dao.base.BaseDao;
import com.school.domain.entity.good.GoodInfo;
import com.school.domain.vo.good.GoodInfoVo;

public interface GoodInfoMapper extends BaseDao {
    int deleteByPrimaryKey(Long id);

    int insert(GoodInfo record);

    int insertSelective(GoodInfo record);

    GoodInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodInfo record);

    int updateByPrimaryKeyWithBLOBs(GoodInfo record);

    int updateByPrimaryKey(GoodInfo record);
    
    /**
     * 商品页面
     */
    List<GoodInfoVo> queryPage(Map<String, Object> paramMap);

	GoodInfoVo detail(Long id);

	/**
	 * 筛选能上架/下架的产品:
	 *    上架：产品状态为审核通过或下架
	 *    下架：产品状态为上架
	 */
	List<Long> chooseGoodId(Map map);
	
	/**
	 * 批量上架/下架
	 */
	int upAndDownAll(Map map);

	/**
	 * 审核页面
	 */
	List<GoodInfoVo> queryAuthPage(Map<String, Object> searchParams);

	/**
	 * 根据id删除该商品所有的图片
	 */
	void delGoodImagesById(Long id);

	/**
	 * 批量插入商品图片
	 */
	void insertImagesOnce(Map map);

	/**
	 * 根据id查询
	 */
	GoodInfoVo selectById(Long id);

	/**
	 * 删除标签
	 */
	void delGoodLabelById(Long id);

	/**
	 * 批量插入标签
	 */
	void insertLabelOnce(Map<String, Object> map);

	/**
	 * 获取所有标签
	 */
	List<Map<String, Object>> getAllLabels();

	/**
	 * 替换图片路径：如果修改了商品编号，则需要替换图片路径
	 */
	void replaceGoodNoFromImgPath(Map<String, Object> map);

}