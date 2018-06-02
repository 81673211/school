package com.school.domain.entity.good;

public class GoodImage {

	private Long id;
	private Long goodId;
	private String bigImgPath;
	private String smallImgPath;
	private Integer sort;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGoodId() {
		return goodId;
	}
	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}
	public String getBigImgPath() {
		return bigImgPath;
	}
	public void setBigImgPath(String bigImgPath) {
		this.bigImgPath = bigImgPath;
	}
	public String getSmallImgPath() {
		return smallImgPath;
	}
	public void setSmallImgPath(String smallImgPath) {
		this.smallImgPath = smallImgPath;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public GoodImage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GoodImage(String bigImgPath, String smallImgPath) {
		super();
		this.bigImgPath = bigImgPath;
		this.smallImgPath = smallImgPath;
	}
	
}
