package com.school.biz.util.pager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class PageInfo implements Serializable {
	public int getNowPage() {
		return this.nowPage;
	}

	public int getTotalRecord() {
		return this.totalRecord;
	}

	public int getTotalPage() {
		return this.totalPage;
	}

	public int getPageIndex() {
		return this.pageIndex;
	}

	public int getStartIndex() {
		return this.startIndex;
	}

	public int getEndIndex() {
		return this.endIndex;
	}

	public String getSort() {
		return this.sort;
	}

	public Object getContent() {
		return this.content;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof PageInfo)) {
			return false;
		}
		PageInfo other = (PageInfo) o;
		if (!other.canEqual(this)) {
			return false;
		}
		if (getNowPage() != other.getNowPage()) {
			return false;
		}
		if (getTotalRecord() != other.getTotalRecord()) {
			return false;
		}
		if (getTotalPage() != other.getTotalPage()) {
			return false;
		}
		if (getPageIndex() != other.getPageIndex()) {
			return false;
		}
		if (getStartIndex() != other.getStartIndex()) {
			return false;
		}
		if (getEndIndex() != other.getEndIndex()) {
			return false;
		}
		Object this$sort = getSort();
		Object other$sort = other.getSort();
		if (this$sort == null ? other$sort != null : !this$sort.equals(other$sort)) {
			return false;
		}
		Object this$content = getContent();
		Object other$content = other.getContent();
		if (this$content == null ? other$content != null : !this$content.equals(other$content)) {
			return false;
		}
		return getPageSize() == other.getPageSize();
	}

	protected boolean canEqual(Object other) {
		return other instanceof PageInfo;
	}

	public int hashCode() {
		int PRIME = 59;
		int result = 1;
		result = result * 59 + getNowPage();
		result = result * 59 + getTotalRecord();
		result = result * 59 + getTotalPage();
		result = result * 59 + getPageIndex();
		result = result * 59 + getStartIndex();
		result = result * 59 + getEndIndex();
		Object $sort = getSort();
		result = result * 59 + ($sort == null ? 43 : $sort.hashCode());
		Object $content = getContent();
		result = result * 59 + ($content == null ? 43 : $content.hashCode());
		result = result * 59 + getPageSize();
		return result;
	}

	public String toString() {
		return "PageInfo(nowPage=" + getNowPage() + ", totalRecord=" + getTotalRecord() + ", totalPage="
				+ getTotalPage() + ", pageIndex=" + getPageIndex() + ", startIndex=" + getStartIndex() + ", endIndex="
				+ getEndIndex() + ", sort=" + getSort() + ", content=" + getContent() + ", pageSize=" + getPageSize()
				+ ")";
	}

	private static final Logger log = LoggerFactory.getLogger(PageInfo.class);
	private static final long serialVersionUID = 1L;
	private int nowPage;
	private int totalRecord;
	private int totalPage;
	private int pageIndex;
	public static final int DEFAULT_PAGESIZE = 5;
	private int startIndex;
	private int endIndex;
	private String sort;
	private Object content;
	private int pageSize;

	public PageInfo(int nowPage, int pageSize) {
		this.nowPage = nowPage;
		this.pageSize = pageSize;

		this.pageIndex = ((this.nowPage - 1) * pageSize);

		this.startIndex = this.pageIndex;
		this.endIndex = (this.pageIndex + pageSize);
	}

	public PageInfo(int totalRecord, int nowPage, int pageSize) {
		if (pageSize <= 0) {
			pageSize = totalRecord;
		}
		this.totalRecord = totalRecord;
		if (this.totalRecord % pageSize == 0) {
			this.totalPage = (this.totalRecord / pageSize);
		} else {
			this.totalPage = (this.totalRecord / pageSize + 1);
		}
		if (nowPage > this.totalPage) {
			nowPage = this.totalPage;
		}
		if (nowPage <= 0) {
			nowPage = 1;
		}
		this.nowPage = nowPage;

		this.pageIndex = ((this.nowPage - 1) * pageSize);

		this.startIndex = this.pageIndex;
		this.endIndex = (this.pageIndex + pageSize);
		if (this.endIndex > totalRecord) {
			this.endIndex = totalRecord;
		}
	}

	public void computePage(int totalRecord) {
		if (this.pageSize <= 0) {
			this.pageSize = totalRecord;
		}
		this.totalRecord = totalRecord;
		if (this.totalRecord % this.pageSize == 0) {
			this.totalPage = (this.totalRecord / this.pageSize);
		} else {
			this.totalPage = (this.totalRecord / this.pageSize + 1);
		}
		if (this.nowPage > this.totalPage) {
			this.nowPage = this.totalPage;
		}
		if (this.nowPage <= 0) {
			this.nowPage = 1;
		}
		this.pageIndex = ((this.nowPage - 1) * this.pageSize);

		this.startIndex = this.pageIndex;
		this.endIndex = (this.pageIndex + this.pageSize);
		if (this.endIndex > this.totalRecord) {
			this.endIndex = this.totalRecord;
		}
	}

	public PageInfo() {
	}
}
