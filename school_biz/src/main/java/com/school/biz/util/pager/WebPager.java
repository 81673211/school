package com.school.biz.util.pager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class WebPager extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer curPage;
	private Integer totalPage;
	private Integer pageSize = 16;
	private Integer totalCount = 0;
	private String formId;
	private String searchParams;
	public Integer getCurPage() {
		return curPage;
	}
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getSearchParams() {
		return searchParams;
	}
	public void setSearchParams(String searchParams) {
		this.searchParams = searchParams;
	}
	
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		
		int pageNumber = 0;
		if (totalCount%pageSize == 0) {
			pageNumber = totalCount/pageSize;
		}else{
			pageNumber = totalCount/pageSize+1;
		}
		if (curPage < 1) {
			curPage = 1;
		}
		try {
			if (pageNumber > 1) {
				out.print("<script type='text/javascript'>" + 
				  		"function go(pageNum){" + 
				  			"var f = document.getElementById('" + formId + "');"+
				  			"f.action = f.action + '?pageNo=' + pageNum + '&pageSize="+
				  						pageSize+ "';"+
				  			"f.submit();"+
  						"}" + 
				  "</script>");
				out.print("<div class='fr clearfix'>");
				if (curPage-1 <=0 ) {
					out.print("<a href='javascript:go("+(curPage)+")'>上一页</a>");
				}else{
					out.print("<a href='javascript:go("+(curPage-1)+")'>上一页</a>");
				}
				
				for(int i=1;i<=totalPage;i++){
					if(i==curPage){
						out.print("<a class='act' href='#'>" + i + "</a>");
					}else{
						out.print("<a href='javascript:go("+i+")'>" + i + "</a>");
					}
				}
				if (curPage+1 > pageNumber) {
					out.print("<a href='javascript:go("+(curPage)+")'>下一页</a>");
				}else {
					out.print("<a href='javascript:go("+(curPage+1)+")'>下一页</a>");
				}
				
				out.print("</div>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}
	
	public static Integer getStartIndex(Integer pageNum, Integer pageSize){
		Integer res = 0;
		if(pageNum>0){
			res = (pageNum-1)*pageSize;
		}
		return res;
	}
}
