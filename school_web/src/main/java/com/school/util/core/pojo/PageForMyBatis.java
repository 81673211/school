package com.school.util.core.pojo;

import org.springframework.data.domain.Sort.Direction;

import java.util.List;
import java.util.Map;

public class PageForMyBatis<T> {
   
    //当前页
   private int nowPage;
   
    //总记录数
   private int totalRecord;
   
    //总页数
   private int totalPage;
   
    // 当前页记录开始的位置
   private int pageIndex;
   
   //每页显示的记录数
   private int pageSize;
    // 默认每页显示的记录数
   public static final int DEFAULT_PAGESIZE = 5;
   
      
    // 开始的索引值
   private int startIndex;
   
    // 结束的索引值
   private int endIndex;
   
   // 分页信息
   private Map<String,Direction> sortMap;
   
    //当前页信息
   private List<T> content;

    //构造器
   public PageForMyBatis() {
   }
   
   public PageForMyBatis(int nowPage,int pageSize) {
	   
	   if(pageSize <= 0){
		   pageSize = DEFAULT_PAGESIZE;
	   }
	  this.pageSize = pageSize;    
	  this.nowPage = nowPage;
     /* this.totalRecord = totalRecord;
      //计算总页数
      if (this.totalRecord % pageSize == 0) {
       this.totalPage = this.totalRecord / pageSize;
      } else {
       this.totalPage = this.totalRecord / pageSize + 1;
      }
      
      if(nowPage > this.totalPage){
         // 计算当前页
    	  nowPage = this.totalPage;
      }
      
      if(nowPage <= 0){
		   nowPage = 1;
	  }
	   
      this.nowPage = nowPage;
      
      // 计算出当前页开始的位置
      this.pageIndex = (this.nowPage - 1) * pageSize;
      
      //this.startIndex = this.pageIndex + 1;
      //mysql 
      this.startIndex = this.pageIndex;
      this.endIndex = this.pageIndex + pageSize;
      
      if(this.endIndex > totalRecord){
    	 this.endIndex = totalRecord;
      }*/
    }

	public int getNowPage() {
		return nowPage;
	}
	
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	
	public int getTotalRecord() {
		return totalRecord;
	}
	
	public void setTotalRecord(int totalRecord) {
		//计算总页数
	     if (totalRecord % this.pageSize == 0) {
	       this.totalPage = totalRecord / this.pageSize;
	     } else {
	       this.totalPage = totalRecord / this.pageSize + 1;
	     }
		this.totalRecord = totalRecord;
	}
	
	public int getTotalPage() {
		return totalPage;
	}
	
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public int getPageIndex() {
		return pageIndex;
	}
	
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	public int getStartIndex() {
		return startIndex;
	}
	
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	
	public int getEndIndex() {
		return endIndex;
	}
	
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	
	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public Map<String, Direction> getSortMap() {
		return sortMap;
	}

	public void setSortMap(Map<String, Direction> sortMap) {
		this.sortMap = sortMap;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
