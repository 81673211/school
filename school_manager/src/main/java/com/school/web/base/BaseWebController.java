package com.school.web.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.school.util.core.exception.BusinessException;
import com.school.util.core.utils.StrToMapUtil;
import com.school.util.core.utils.StrUtil;


@Controller
public abstract class BaseWebController {
	
	
	public static final String PAGE_SIZE = "10";
	
	/**
	 * 当前页
	 */
	public static final String PAGE_PARAM_PAGENO = "pageNo";
	
	/**
	 * 总页数
	 */
	public static final String PAGE_PARAM_PAGECOUNT = "pageCount";
	
	/**
	 * 每页记录数
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
	protected String showView = null;
	/**
	 * <pre>
	 * {
	 * 	successView = 操作成功跳转路径 如:"redirect:/list.do";
	 * }
	 * </pre>
	 */
	protected String successView = null;
	
	/** 
	 * @Description: 默认成功
	 * @author fujie
	 */
	protected static final Map<String,Object> SUCCESS;
	
	/** 
	 * @Description: 默认失败
	 * @author fujie
	 */
	protected static final Map<String,Object> ERROR;
	
	static {
		SUCCESS = success() ;
		ERROR = error();
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
		this.showView = showView;
	}

	/**
	 * 设置默认函数为list()
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(@RequestParam(value = "pagenum", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pagesize", defaultValue = PAGE_SIZE) int pageSize,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return list(pageNumber,pageSize,request);
	}

	/**
	 * 显示对象列表页面的模板方法. 调用子类的
	 * {@link  #onList(HttpServletRequest,HttpServletResponse,ModelAndView)}
	 */
	@RequestMapping(value = "list")
	public ModelAndView list(@RequestParam(value = "pageNo", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,HttpServletRequest request) throws Exception {
		
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "search_");
		Map<String, Object> orderParams = WebUtils.getParametersStartingWith(request, "order_");
		Sort sort = null;
		//构建个性化分页
		for(Map.Entry<String,Object> entry : orderParams.entrySet()){
			
			String value = (String)entry.getValue();
			sort = new Sort(Direction.fromString(value),entry.getKey());
		}
		//分页传输对象
		PageRequest pageRequest = new PageRequest(pageNumber,pageSize,sort);
		
		ModelAndView mav = new ModelAndView(listView);
		mav.addAllObjects(referenceData(request));
		
		//查询条件，传给页面
		mav.addObject(PAGE_PARAM_SEARCHPARAMS, searchParams);
		mav.addObject(PAGE_PARAM_PAGENO, pageNumber);
		mav.addObject(PAGE_PARAM_PAGESIZE, pageSize);
		
		onList(pageRequest,searchParams,request, mav);		
		return mav;
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("id") Long id, HttpServletRequest request,Model model) throws Exception {
		
		ModelAndView mav = new ModelAndView(editView);
		mav.addAllObjects(referenceData(request));
		onEdit(id,request,model);
		
		return mav;
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public ModelAndView create(HttpServletRequest request,Model model) throws Exception {
		
		ModelAndView mav = new ModelAndView(showView);
		mav.addAllObjects(referenceData(request));
		onCreate(request,model);
		
		return mav;
	}
	
	@RequestMapping(value = "show", method = RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request,Model model) throws Exception {
		
		ModelAndView mav = new ModelAndView(editView);
		mav.addAllObjects(referenceData(request));
		onShow(request,model);
		
		return mav;
	}
	
	
	/* 以下为回调函数列表,意义见前,在各子类中实现 */
	protected void onList(PageRequest pageRequest,Map<String, Object> searchParams,HttpServletRequest request, ModelAndView mav) throws Exception {
		
	}
	
	protected void onEdit(Long id,HttpServletRequest request,Model model) throws Exception {
		
	}
	
	protected void onCreate(HttpServletRequest request,Model model) throws Exception {
		
	}
	
	protected void onShow(HttpServletRequest request,Model model) throws Exception {
		
	}


	/**
	 * 将辅助显示的对象,如类别列表,状态列表 放入request的Attribute中. 实际调用{@link #referenceData(HttpServletRequest, Map)},
	 * 多此一层封装仅仅为了初始化module.
	 */
	protected Map referenceData(HttpServletRequest request) {
		Map model = new HashMap();
		referenceData(request, model);
		return model;
	}

	/**
	 * 将辅助显示的对象,如类别列表,状态列表 放入request的Attribute中. 在各子类实现.
	 */
	protected void referenceData(HttpServletRequest request, Map model) {
	}

	/**
	 * 获取客户端真实的ip地址
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isBlank(ip)
			|| StringUtils.equalsIgnoreCase(ip, "unknown")) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip)
				|| StringUtils.equalsIgnoreCase(ip, "unknown")) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip)
				|| StringUtils.equalsIgnoreCase(ip, "unknown")) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isBlank(ip)
				|| StringUtils.equalsIgnoreCase(ip, "unknown")) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isBlank(ip)
				|| StringUtils.equalsIgnoreCase(ip, "unknown")) {
			ip = request.getRemoteAddr();
		}
		if (StringUtils.isNotBlank(ip) && StringUtils.indexOf(ip, ",") > 0) {
			String[] ipArray = StringUtils.split(ip, ",");
			ip = ipArray[0];
		}
		return ip;
	}
	
	/**
	 * 返回错误结果
	 * @param e
	 * @return
	 */
	protected static Map<String,Object> error(BusinessException e) {
		
		Map<String,Object> errorMap = new HashMap<String,Object>();
		
		errorMap.put("code", e.getCode());
		errorMap.put("msg", e.getMsg());
		
		
		return errorMap;
	}
	/** 
	 * @Description: 返回默认失败 结果数据
	 * @author yao
	 */
	protected static Map<String,Object> error() {
		
		return error(BusinessException.createErr(String.valueOf("5000")));
	}
	
	/** 
	 * @Description: 返回成功结果数据
	 * @param  obj 返回数据对象
	 * @author fujie
	 */
	protected static Map<String,Object> success(Object obj) {
		
		Map<String,Object> successMap = new HashMap<String,Object>();
		
		successMap.put("code", "0");
		successMap.put("msg", "成功");
		successMap.put("data", obj);
		
		return successMap;
	}
	/** 
	 * @Description: 数据操作成功
	 * @author yao
	 */
	private static Map<String,Object> success() {
		
		Map<String,Object> successMap = new HashMap<String,Object>();
		
		successMap.put("code", "0");
		successMap.put("msg", "成功");
		
		return successMap;
	}
	
	
	/**
	 * list 页面查询条件传递到详情或编辑或新增页面
	 * @author yao
	 * @since 2016-03-09 16:03
	 * @param request
	 * @return
	 */
	public String searchParams(HttpServletRequest request) {
		String searchParams = request.getParameter("searchParams");
		String pageNoStr = request.getParameter("listPageNo");
		StringBuilder searchParamsStr = new StringBuilder();
		if (StringUtils.isNotBlank(searchParams)) {
			Map<String, String> map = StrToMapUtil.transStringToMap(searchParams);
			if (map != null) {
				for (String key : map.keySet()) {
					searchParamsStr.append("search_")
					.append(key)
					.append("=")
					.append(map.get(key))
					.append("&");
				}
			}
		}
		if (StringUtils.isNotBlank(pageNoStr)) {
            searchParamsStr.append("pageNo=").append(pageNoStr);
        }
		return searchParamsStr.toString();
	}
}
