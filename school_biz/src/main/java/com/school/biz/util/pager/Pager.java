package com.school.biz.util.pager;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Title: Pager <BR>
 * Description: 页面的分页标签 <BR>
 *
 * @version 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
public class Pager extends TagSupport {

	private static final long serialVersionUID = 1L;

	private Integer curPage;
	private Integer totalPage;
	private Integer pageSize = 10;
	private Integer totalCount = 0;
	private String formId;
	private String searchParams; 

	public int doStartTag() throws JspException {
		
		JspWriter out = pageContext.getOut();
		int pageNumber = 0;
		if (totalPage%pageSize==0) {
			pageNumber = totalPage/pageSize;
		} else {
			pageNumber = (totalPage/pageSize)+1;
		}
		if (curPage < 1) {
			curPage = 1;
		}

		try {
			if (pageNumber > 0) {
				out.print("<script type='text/javascript'>" + 
						  		"function go(pageNum){" + 
						  			"var f = document.getElementById('" + formId + "');"+
						  			"f.action = f.action + '?pageNo=' + pageNum + '&pageSize="+pageSize+ "&" + searchParams+ "';"+
						  			"f.submit();"+
		  						"}" + 
						  "</script>");
				
				out.print("<div class='yhl-pags' id='pag_wrap'><div class='yhl-pags-box pull-right'>");
				int start = 1;
				int end = totalPage;
				for(int i=4;i>=1;i--){
					if((curPage-i)>=1){
						start = curPage-i;
						break;
					}
				}
				for(int i=4;i>=1;i--){
					if((curPage+i)<=totalPage){
						end = curPage+i;
						break;
					}
				}
				//如果小于9则右侧补齐
				if(end-start+1<=9){
					Integer padLen = 9-(end-start+1);
					for(int i=padLen;i>=1;i--){
						if((end+i)<=totalPage){
							end = end+i;
							break;
						}
					}
				}
				
				//如果还小于9左侧补齐
				if(end-start+1<=9){
					Integer padLen = 9-(end-start+1);
					for(int i=padLen;i>=1;i--){
						if((start-i)>=1){
							start = start-i;
							break;
						}
					}
				}
				
				if(curPage>=1){
					if(start>1){
						
						out.print("<a class='pag-home' href='javascript:go(1)'>首页</a>");
					}			
					out.print("<a class='pag-left' href='javascript:go("+(curPage-1)+")'>上一页</a>");
				}
				
				for(int i=start;i<=end;i++){
					if(i==curPage){
						out.print("<a class='pag-number on' href='javascript:void(0);'>" + i + "</a>");
					}else{
						out.print("<a class='pag-number' href='javascript:go("+i+")'>" + i + "</a>");
					}
				}
				if(curPage<totalPage){
					out.print("<a class='pag-right act' href='javascript:go("+(curPage+1)+")'>下一页</a>");
					if(end<totalPage){
						out.print("<a class='pag-last' href='javascript:go("+totalPage+")'>尾页</a>");
					}
				}
				out.print("<span class='pag-sum'>"+"共" + this.totalCount + "条/" + totalPage + "页" +"</span>");
				out.print("</div>");
				out.print("</div>");
			}

		} catch (IOException e) {
			log.error("doStartTag error",e);
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