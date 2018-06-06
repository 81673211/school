package com.school.common.springmvc;

import com.alibaba.fastjson.JSON;
import com.school.util.core.pager.PageInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @ClassName: SpringMvcUtil 
 * @Description: springMvc的工具类
 * @author Ycl
 *
 */
public class SpringMvcUtil {
	
    /** 
     * @Fields RESULT_CODE : 返回的状态码 code
     */ 
    private static final String RESULT_CODE = "code";
    
    /** 
     * @Fields RESULT_CODE_SUCCESS : 返回成功的状态码 0)
     */ 
    private static final String RESULT_CODE_SUCCESS = "0";
    
    /** 
     * @Fields RESULT_CODE_ERROR : 返回失败的状态码 -1
     */ 
    private static final String RESULT_CODE_ERROR = "-1";
    
    /** 
     * @Fields RESULT_DATA : 返回的数据 data
     */ 
    private static final String RESULT_DATA = "data";
    
    /** 
     * @Fields RESULT_MSG : 返回失败的信息 msg
     */ 
    private static final String RESULT_MSG = "msg";
    /**
     * @Fields RESULT_CURPAGE : 当前页
     */
    private static final String RESULT_CURPAGE="curPage";
    /**
     * @Fields RESULT_TOTALROWS :总条数
     */
    private static final String RESULT_TOTALROWS="totalRows";
	
	/**
	  * writeBackParam()
	  * @Title: writeBackParam
	  * @Description: 将request中的参数回写,并返回参数
	  * @param request
	  * @param @return
	  * @return Map<String,Object>  
	 */
	public static Map<String, Object> writeBackParam(HttpServletRequest request) {
		return writeBackParam(request,null,null);
	}
	
	/**
	  * writeBackParam()
	  * @Title: writeBackParam
	  * @Description: 将request中的参数回写,并返回参数
	  * @param request
	  * @param pageInfo PageInfo
	  * @param @return
	  * @return Map<String,Object>  
	 */
	public static Map<String, Object> writeBackParamByPage(HttpServletRequest request) {
		PageInfo pageInfo=new PageInfo();
		return writeBackParam(request,null,pageInfo);
		
	}
	
	/**
	  * writeBackParam()
	  * @Title: writeBackParam
	  * @Description: 将request中的参数回写,并返回参数
	  * @param request
	  * @param mav ModelAndView
	  * @param @return
	  * @return Map<String,Object>  
	 */
	public static Map<String, Object> writeBackParam(HttpServletRequest request,ModelAndView mav) {
		return writeBackParam(request,mav,null);
		
	}
	
	/**
	 * 
	 * writeBackParam()
	 * @Title: writeBackParam
	 * @Description: 将request中的参数回写,并返回参数
	 * @param request
	 * 			HttpServletRequest
	 * @param mav
	 * 			ModelAndView
	 * @param pageInfo
	 * 			PageInfo
	 * @return
	 * @return Map<String,Object>
	 */
	public static Map<String, Object> writeBackParam(HttpServletRequest request,ModelAndView mav,PageInfo pageInfo) {
		Enumeration<String> names = request.getParameterNames();
		Map<String, Object> map = new HashMap<String, Object>();
		if(pageInfo!=null){
			map.put("page", pageInfo);
		}
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String value = request.getParameter(name);
			request.setAttribute(name, value);
			if("pageSize".equals(name)&&pageInfo!=null){
				pageInfo.setPageSize(Integer.parseInt(value));
			}else if("curPage".equals(name)&&pageInfo!=null){
				pageInfo.setNowPage(Integer.parseInt(value));
			}else{
				map.put(name, value);
			}
			if(mav!=null){
				mav.addObject(name,value);
			}
		}
		return map;
	}
	
	
	/**
	 * writeSuccess()
	 * @Title: writeSuccess
	 * @Description: 回写成功的结果
	 * @param @param result
	 * @param @param request
	 * @param @param response
	 * @return void
	 */
	public static void writeSuccess(Object result, HttpServletRequest request,
			HttpServletResponse response) {
		writeAjaxResult(true, result, request, response,null);
	}
	
	/**
	 * writeSuccess()
	 * @Title: writeSuccess
	 * @Description: 回写成功的结果
	 * @param @param result
	 * @param @param request
	 * @param @param response
	 * @param @param pageInfo
	 * @return void
	 */
	public static void writeSuccess(Object result, HttpServletRequest request,
			HttpServletResponse response,PageInfo pageInfo) {
		writeAjaxResult(true, result, request, response,pageInfo);
	}
	
	/**
	 * writeFailed()
	 * @Title: writeFailed
	 * @Description: 回写失败的结果
	 * @param result
	 * @param request
	 * @param response
	 * @return void
	 */
	public static void writeFailed(Object result, HttpServletRequest request,
			HttpServletResponse response) {
		writeAjaxResult(false, result, request, response,null);
	}
	
	/**
	 * writeAjaxResult()
	 * @Title: writeAjaxResult
	 * @Description: 回写操作结果
	 * @param flag 
	 * 			标识操作成功(true) or 失败(false)
	 * @param result
	 * 			需要返回的数据
	 * @param request
	 * @param response
	 * @return void
	 */
	public static void writeAjaxResult(boolean flag, Object result,
			HttpServletRequest request, HttpServletResponse response,PageInfo pageInfo) {
		write(JSON.toJSONString(buildResult(result, flag,pageInfo)), request, response);
	}
	

	/**
	 * write()
	 * @Title: write
	 * @Description: 执行结果写回操作
	 * @param str
	 * 			需要写回的json串
	 * @param request
	 * @param response
	 * @return void
	 */
	public static void write(String str, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	
	/**
	 * buildResult()
	 * @Title: buildResult
	 * @Description: 构建返回数据
	 * @param data
	 * 			需要回写的数据
	 * @param flag
	 * 			标识操作成功(true) or 失败(false)
	 * @return 返回构建完成的返回数据
	 * @return Map<String,Object>
	 */
	public static Map<String, Object> buildResult(Object data, boolean flag,PageInfo pageInfo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(RESULT_CODE, flag ? RESULT_CODE_SUCCESS :RESULT_CODE_ERROR);
		if (flag) {
			resultMap.put(RESULT_DATA, data);
		} else {
			resultMap.put(RESULT_MSG, data);
		}
		if(pageInfo!=null){
			resultMap.put(RESULT_CURPAGE, pageInfo.getNowPage());
			resultMap.put(RESULT_TOTALROWS, pageInfo.getTotalRecord());
		}
		return resultMap;
	}
	/**
	 * @Title: toMap   
	 * @Description: 解析参数  
	 * @param: @param paramMap
	 * @param: @return      
	 * @return: Map<String,Object>      
	 * @throws
	 */
	public static Map<String, Object> toMap(final Map<String, String[]> map) {
		Map<String, Object> nMap = new HashMap<String, Object>();
		for (String key : map.keySet()) {
			final Object value = map.get(key);
			if (value instanceof Object[]) {
				final Object[] obj = (Object[]) value;
				if (obj.length == 1) {
					nMap.put(key, obj[0]);
				} else {
					nMap.put(key, obj);
				}
			}
		}
		return nMap;

	}
	
	/**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
    
    /**  
     * 将一个 Map 对象转化为一个 JavaBean  
     * @param type 要转化的类型  
     * @param map 包含属性值的 map  
     * @return 转化出来的 JavaBean 对象  
     * @throws IntrospectionException 如果分析类属性失败  
     * @throws IllegalAccessException 如果实例化 JavaBean 失败  
     * @throws InstantiationException 如果实例化 JavaBean 失败  
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败  
     */    
    @SuppressWarnings("rawtypes")    
    public static Object convertMap(Class type, Map map)    
            throws IntrospectionException, IllegalAccessException,    
            InstantiationException, InvocationTargetException {    
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性    
        Object obj = type.newInstance(); // 创建 JavaBean 对象    
    
        // 给 JavaBean 对象的属性赋值    
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();    
        for (int i = 0; i< propertyDescriptors.length; i++) {    
            PropertyDescriptor descriptor = propertyDescriptors[i];    
            
            String propertyName = descriptor.getName();  
            
            Class clazz=descriptor.getPropertyType();
           
    
            if (map.containsKey(propertyName)) {    
                Object value = map.get(propertyName);    
                Object[] args = new Object[1];    
                args[0] =  clazz.cast(value);    
                
                descriptor.getWriteMethod().invoke(obj, args);    
            }    
        }    
        return obj;    
    }
    // Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean  
    public static void transMap2Bean2(Map<String, Object> map, Object obj) {  
        if (map == null || obj == null) {  
            return;  
        }  
        try {  
            BeanUtils.populate(obj, map);  
        } catch (Exception e) {  
            System.out.println("transMap2Bean2 Error " + e);  
        }  
    }  
}
