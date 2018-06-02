package com.school.domain.vo.good;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.school.constant.StatusManage;
import com.school.domain.entity.good.GoodImage;
import com.school.util.core.utils.excel.annotation.Excel;

public class GoodInfoVo {
	private Long id;

    @Excel(exportName = "商品编号")
    private String goodNo;

    @Excel(exportName = "商品名称")
    private String goodName;

    private Long goodTypeId;
    
    @Excel(exportName = "类型")
    private String goodTypeName;
    
    private Long goodBrandId;
    
    @Excel(exportName = "品牌")
    private String goodBrandName;
    
    @Excel(exportName = "进价")
    private BigDecimal purchasePrice;

    @Excel(exportName = "原价")
    private BigDecimal oldPrice;

    @Excel(exportName = "现价")
    private BigDecimal price;

    @Excel(exportName = "现价对应积分")
    private Long goodPoints;

    private Long shopId;
    
    @Excel(exportName = "店铺名称")
    private String shopName;
    
    @Excel(exportName = "是否邮寄",exportConvertSign = 1)
    private Integer isPost;
    
    /**翻译中文*/
    public String convertGetIsPost(){
    	return StatusManage.isPostMap.get(this.isPost);
    }
    
    @Excel(exportName = "邮寄费用")
    private BigDecimal postMoney;
    
    @Excel(exportName = "总量")
    private Integer totalNum;

    @Excel(exportName = "销售数量")
    private Integer saleNum;

    @Excel(exportName = "是否店铺商品",exportConvertSign = 1)
    private Integer isShopGood;
    
    /**翻译中文*/
    public String convertGetIsShopGood(){
    	return StatusManage.isShopGoodMap.get(this.isShopGood);
    }

    @Excel(exportName = "首页推荐",exportConvertSign = 1)
    private Integer isRecommend;
    
    /**翻译中文*/
    public String convertGetIsRecommend(){
    	return StatusManage.isRecommendMap.get(this.isRecommend);
    }
    
    @Excel(exportName = "状态",exportConvertSign = 1)
    private Integer status;
    
    /**翻译中文*/
    public String convertGetStatus(){
    	return StatusManage.goodInfoStatusMap.get(this.status);
    }

    private String publishTime;

    private String downTime;

    private String noPassReason;

    private String createTime;


    private String qrCodePath;

    private Integer version;

    private String description;
    
    private List<GoodImage> goodImages = new ArrayList<GoodImage>();
    
    private List<String> goodLabels = new ArrayList<String>();
    
    private String smallImgPath;
    
    private String bigImgPath;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGoodNo() {
		return goodNo;
	}

	public void setGoodNo(String goodNo) {
		this.goodNo = goodNo;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(BigDecimal oldPrice) {
		this.oldPrice = oldPrice;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getGoodPoints() {
		return goodPoints;
	}

	public void setGoodPoints(Long goodPoints) {
		this.goodPoints = goodPoints;
	}

	public Long getGoodTypeId() {
		return goodTypeId;
	}

	public void setGoodTypeId(Long goodTypeId) {
		this.goodTypeId = goodTypeId;
	}

	public String getGoodTypeName() {
		return goodTypeName;
	}

	public void setGoodTypeName(String goodTypeName) {
		this.goodTypeName = goodTypeName;
	}

	public Long getGoodBrandId() {
		return goodBrandId;
	}

	public void setGoodBrandId(Long goodBrandId) {
		this.goodBrandId = goodBrandId;
	}

	public String getGoodBrandName() {
		return goodBrandName;
	}

	public void setGoodBrandName(String goodBrandName) {
		this.goodBrandName = goodBrandName;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

	public Integer getIsPost() {
		return isPost;
	}

	public void setIsPost(Integer isPost) {
		this.isPost = isPost;
	}

	public BigDecimal getPostMoney() {
		return postMoney;
	}

	public void setPostMoney(BigDecimal postMoney) {
		this.postMoney = postMoney;
	}

	public Integer getIsShopGood() {
		return isShopGood;
	}

	public void setIsShopGood(Integer isShopGood) {
		this.isShopGood = isShopGood;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getDownTime() {
		return downTime;
	}

	public void setDownTime(String downTime) {
		this.downTime = downTime;
	}

	public String getNoPassReason() {
		return noPassReason;
	}

	public void setNoPassReason(String noPassReason) {
		this.noPassReason = noPassReason;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getQrCodePath() {
		return qrCodePath;
	}

	public void setQrCodePath(String qrCodePath) {
		this.qrCodePath = qrCodePath;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<GoodImage> getGoodImages() {
		return goodImages;
	}

	public void setGoodImages(List<GoodImage> goodImages) {
		this.goodImages = goodImages;
	}

	public List<String> getGoodLabels() {
		return goodLabels;
	}

	public void setGoodLabels(List<String> goodLabels) {
		this.goodLabels = goodLabels;
	}

	public String getSmallImgPath() {
		return smallImgPath;
	}

	public void setSmallImgPath(String smallImgPath) {
		this.smallImgPath = smallImgPath;
	}

	public String getBigImgPath() {
		return bigImgPath;
	}

	public void setBigImgPath(String bigImgPath) {
		this.bigImgPath = bigImgPath;
	}
	
}
