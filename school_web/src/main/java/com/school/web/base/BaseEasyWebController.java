package com.school.web.base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.google.common.collect.Maps;
import com.school.util.core.ConstantCore;
import com.school.util.core.entity.ExcelTitle;
import com.school.util.core.exception.FuBusinessException;
import com.school.util.core.pager.PageInfo;
import com.school.util.core.utils.CamelAndUnderlineUtil;
import com.school.util.core.utils.DateUtil;
import com.school.util.core.utils.JsonUtils;
import com.school.util.core.utils.excel.util.ExcelExportUtil;


/**
 * @Description: web端基础类---专注用于MyBatis体系
 * @author fujie
 */

@Controller
@Slf4j
@SuppressWarnings("all")
public abstract class BaseEasyWebController {
	
	
	private static final String PAGE_SIZE = "10";
	
	/**
	 * 当前页数
	 */
	public static final String PAGE_PARAM_PAGENO = "pageNo";
	
	/**
	 * 总页数
	 */
	public static final String PAGE_PARAM_PAGECOUNT = "pageCount";
	
	/**
	 * 每页显示记录数
	 */
	public static final String PAGE_PARAM_PAGESIZE = "pageSize";
	
	/**
	 * 总记录数
	 */
	public static final String PAGE_PARAM_TOTALCOUNT = "totalCount";
	
	/**
	 * 查询条件参数
	 */
	public static final String PAGE_PARAM_SEARCHPARAMS = "searchParams";
	
	/**
	 * 以下的View变量即可以在配置文件中定义，也可以在子类静态初始化
	 * 
	 * <pre>
	 * {
	 * 	listView = 展示 列表视图
	 * }
	 * </pre>
	 */
	protected String listView = null;
	/**
	 * <pre>
	 * {
	 * 	editView = 展示和编辑 有数据的详情视图
	 * }
	 * </pre>
	 */
	protected String editView = null;
	/**
	 * <pre>
	 * {
	 *  showView = 新增 空白的视图
	 * }
	 * </pre>
	 */
	protected String createView = null;
	/**
	 * <pre>
	 * {
	 * 	successView = 操作成功跳转路径 如:"redirect:/list.do";
	 * }
	 * </pre>
	 */
	protected String successView = null;
	
	/**
	 * <pre>
	 * {
	 * 	exportClass 导出数据映射类
	 * }
	 * </pre>
	 */
	public Class exportClass = null; 
	
	/** json日期格式处理 */
    private static SerializeConfig mapping = new SerializeConfig();

    private static String dateFormat;
    static {
        dateFormat = "yyyy-MM-dd HH:mm:ss";
        mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
    }

	/**
	 * 设置默认函数为list()
	 */
//	@RequestMapping()
//	public ModelAndView index(@RequestParam(value = "pageNo", defaultValue = "1") int pageNumber,
//			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,HttpServletRequest request,
//			HttpServletResponse response) throws FuBusinessException {
//
//		return list(pageNumber,pageSize,request);
//	}

	/**
	 * 显示对象列表页面的模板方法. 调用子类的
	 */
	@RequestMapping(value = "list.do")
	public ModelAndView list(@RequestParam(value = "pageNo", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,HttpServletRequest request) throws FuBusinessException {
		
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "search_");
		Map<String, Object> orderParams = WebUtils.getParametersStartingWith(request, "order_");
		
		PageInfo pageInfo = new PageInfo(pageNumber,pageSize);
		
		//排序模板
		//order_productType = DESC
		/*		
		orderParams.put("productType", "DESC");
		orderParams.put("productNo", "ASC");*/
		
		//构建排序
		if(!orderParams.isEmpty()) {
			
			//Map<String,FuSort> mapSort = Maps.newTreeMap();
			StringBuffer strSb = new StringBuffer(); 
			for(Map.Entry<String,Object> entry : orderParams.entrySet()) {
				//mapSort.put(entry.getKey(), FuSort.valueOf((String)entry.getValue()));
				strSb.append(CamelAndUnderlineUtil.camelToUnderline(entry.getKey())).
				append(" ").append(entry.getValue()).append(",");
			}
			
			//pageInfo.setSortMap(mapSort);
			String strSortMsg = strSb.toString();
			pageInfo.setSort(strSortMsg.substring(0,strSortMsg.length()-1));
		}
		
		//设置分页对象
		searchParams.put(ConstantCore.EASY_PAGE, pageInfo);
		
		ModelAndView mav = new ModelAndView(listView);
		mav.addAllObjects(referenceData(request));
		
		//查询条件，传给页面
		mav.addObject(PAGE_PARAM_SEARCHPARAMS, searchParams);
		mav.addObject(PAGE_PARAM_PAGENO, pageNumber);
		mav.addObject(PAGE_PARAM_PAGESIZE, pageSize);
		
		onList(pageInfo,searchParams,request,mav);
		
		return mav;
	}
	
	@RequestMapping(value = "edit.htm")
	public ModelAndView edit(@RequestParam("id") Long id, HttpServletRequest request,Model model) throws FuBusinessException {
		
		ModelAndView mav = new ModelAndView(editView);
		mav.addAllObjects(referenceData(request));
		onEdit(id,request,model);
		
		return mav;
	}
	
	@RequestMapping(value = "show.htm")
	public ModelAndView show(@RequestParam("id") Long id, HttpServletRequest request,Model model) throws FuBusinessException {
		
		ModelAndView mav = new ModelAndView(editView);
		mav.addAllObjects(referenceData(request));
		onEdit(id,request,model);
		
		return mav;
	}
	
	
	@RequestMapping(value = "create.htm")
	public ModelAndView create(HttpServletRequest request,Model model) throws FuBusinessException {
		
		ModelAndView mav = new ModelAndView(createView);
		mav.addAllObjects(referenceData(request));
		onCreate(request,model);
		
		return mav;
	}
	
	/**
	 * excel 导出 
	 * 
	 * 1.只能采用POST请求
	 * 2.excel导出无分页
	 */
	@RequestMapping(value = "excel.htm",method = RequestMethod.POST)
	public void exportExcel(HttpServletRequest request,HttpServletResponse reponse) throws FuBusinessException {
		
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "search_");
		
		List dataset = onExportXls(searchParams,request);
		
		downLoadExcle(request, reponse, (String)searchParams.get("title"), this.exportClass,dataset);
	}
	
	
	/* 以下为回调函数列表,意义见前,在各子类中实现 */
	protected void onList(PageInfo pageInfo,Map<String, Object> searchParams,HttpServletRequest request, ModelAndView mav) throws FuBusinessException {}
	
	protected void onEdit(Long id,HttpServletRequest request,Model model) throws FuBusinessException {}
	
	protected void onCreate(HttpServletRequest request,Model model) throws FuBusinessException {}
	
	protected void onShow(HttpServletRequest request,Model model) throws FuBusinessException {}

	/**
	 * 将辅助显示的对象,如类别列表,状态列表 放入request的Attribute中. 实际调用{@link #referenceData(HttpServletRequest, Map)},
	 * 多此一层封装仅仅为了初始化module.
	 */
	protected Map referenceData(HttpServletRequest request) {
		Map model = Maps.newHashMap();
		referenceData(request, model);
		return model;
	}

	/**
	 * 将辅助显示的对象,如类别列表,状态列表 放入request的Attribute中. 在各子类实现.
	 */
	protected void referenceData(HttpServletRequest request, Map model) {};
	
	/**
	 * @Description: web Controller 统一异常
	 * @author fujie
	 */
	protected FuBusinessException webExp(Exception e) {
		
		 log.error(this.getClass().getName(),e);
	
		 return FuBusinessException.createErr(5100);
	}
	
	 /**
	 * 导出报表方法,需子类重写
	 * 
	 * @param request
	 * @param response
	 * @return List 需导出的数据列表
	 * 
	 * @throws FuBusinessException
	 */
	protected List onExportXls(Map<String, Object> searchParams, HttpServletRequest request) throws FuBusinessException { return null;};
	
	/**
	 * 导出
	 * @param request
	 * @param response
	 * @param Title 导出的文件名
	 * @param pojoClass 导出数据的对象类型
	 * @param dataset 需要导出的数据集合
	 */
	protected void downLoadExcle(HttpServletRequest request,HttpServletResponse response,String Title, Class<?> pojoClass, Collection<?> dataset) {
	    
		 
		 response.setContentType("application/vnd.ms-excel");
		 OutputStream out = null;  
		 response.setHeader("Content-disposition", "attachment; filename=fine.xls");// 设定输出文
		 String newtitle = null;
		 try{
			 final String userAgent = request.getHeader("USER-AGENT");
			 if (StringUtils.contains(userAgent, "MSIE")||StringUtils.contains(userAgent, "like Gecko")){// IE9,IE11
				 newtitle = URLEncoder.encode(Title, "UTF8");
				
			 }else {
				 newtitle = new String(Title.getBytes(), "ISO8859-1");
	            }
			 response.setHeader("content-disposition","attachment;filename="+ newtitle +DateUtil.formatDate(new Date())+ ".xls");
		     
		     out= response.getOutputStream();
		      
		     HSSFWorkbook workbook = null;
			 workbook = ExcelExportUtil.exportExcel(new ExcelTitle(Title), pojoClass, dataset);
			 workbook.write(out);
		        
	        } catch (FileNotFoundException e) {
	        	log.error("downLoadExcle error1",e);
	        } catch (IOException e) {
	        	log.error("downLoadExcle error2",e);
	        }
	        
		 finally{
			 try {
				out.flush();
				out.close();
			} catch (IOException e) {
				log.error("downLoadExcle error3",e);
			}
		 }
	}
	
	/**
     * TODO用于想前台BSGrid返回JSon格式数据
     * 
     * @param page
     * @return
     */
    public String getBSGridJsonString(PageInfo page) {
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("success", true);
        if (page.getTotalRecord() < 1) {
            map2.put("totalRows", 0);
        } else {
            map2.put("totalRows", page.getTotalRecord());
        }
        map2.put("curPage", page.getNowPage());
        map2.put("data", page.getContent());
        return JSON.toJSONString(map2, mapping, SerializerFeature.DisableCircularReferenceDetect);
    }
    
    /**
     * 直接向客户端返回Content字符串，不用通过View页面渲染.
     */
    protected void rendText(HttpServletResponse response, Object content) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        if (null == content) {
            content = "";
        }
        PrintWriter out = response.getWriter();
        out.write(content.toString());
        out.flush();
        out.close();
    }
    
    /**
     * 用于Ajax数据存储提示,
     * 
     * @param status true 或 false
     * @param msg 对应的消息
     * @return
     */
    public String getAjaxCallBackMessage(boolean status, String msg) {
        Map<String, Object> msgMap = new HashMap<String, Object>();
        msgMap.put("status", status);
        msgMap.put("msg", msg);
        return JsonUtils.toJsonStringFastJson(msgMap);
    }
	
	public void setListView(String listView) {
		this.listView = listView;
	}

	public void setEditView(String editView) {
		this.editView = editView;
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	public void setShowView(String showView) {
		this.createView = showView;
	}
	
}
