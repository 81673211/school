package com.school.domain.entity.good;

import java.math.BigDecimal;
import java.util.List;

public class GoodInfo {
    private Long id;

    private String goodNo;

    private String goodName;

    private BigDecimal purchasePrice;

    private BigDecimal oldPrice;

    private BigDecimal price;

    private Long goodPoints;

    private Long goodTypeId;

    private Long goodBrandId;

    private Integer totalNum;

    private Integer saleNum;

    private Integer isPost;

    private BigDecimal postMoney;

    private Integer isShopGood;

    private Long shopId;

    private Integer status;

    private String publishTime;

    private String downTime;

    private String noPassReason;

    private String createTime;

    private Integer isRecommend;

    private String qrCodePath;

    private Integer version;

    private String description;
    
    private List<GoodImage> goodImages;
    
    private List<String> labels;
    
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
        this.goodNo = goodNo == null ? null : goodNo.trim();
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName == null ? null : goodName.trim();
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

    public Long getGoodBrandId() {
        return goodBrandId;
    }

    public void setGoodBrandId(Long goodBrandId) {
        this.goodBrandId = goodBrandId;
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
        this.publishTime = publishTime == null ? null : publishTime.trim();
    }

    public String getDownTime() {
        return downTime;
    }

    public void setDownTime(String downTime) {
        this.downTime = downTime == null ? null : downTime.trim();
    }

    public String getNoPassReason() {
        return noPassReason;
    }

    public void setNoPassReason(String noPassReason) {
        this.noPassReason = noPassReason == null ? null : noPassReason.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
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
        this.qrCodePath = qrCodePath == null ? null : qrCodePath.trim();
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
        this.description = description == null ? null : description.trim();
    }

	public List<GoodImage> getGoodImages() {
		return goodImages;
	}

	public void setGoodImages(List<GoodImage> goodImages) {
		this.goodImages = goodImages;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

}