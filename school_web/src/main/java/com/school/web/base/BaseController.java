/**
 * Title: MOBAO <BR>
 * Description: todo Copyright: Copyright (c) 2014-2015<BR>
 * Company: 成都摩宝网络科技有限公司(www.mobo360.com/)<BR>
 * 
 * @author linqingsong
 * @version 1.0
 */
package com.school.web.base;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import com.school.util.core.exception.BusinessException;
import com.school.util.core.pojo.ReqHeader;
/**
 * ClassName: BaseController 
 * @Description: TODO
 * @author linqingsong
 * @date Feb 16, 2015
 */
@Slf4j
@Controller
public class BaseController {
	
//	@Autowired
//	private Validator validator;
	
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
		SUCCESS = success();
		ERROR = error();
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
	 * @Description: 返回默认失败 结果数据
	 * @author fujie
	 */
	protected static Map<String,Object> error() {
		
		return error(BusinessException.createErr(String.valueOf("5000")));
	}
	
	/** 
	 * @Description: 返回成功结果数据和分页信息
	 * @param obj 返回数据对象
	 * @param totalPage 总共有多少页
	 * @param totalRecordNum  总共有多少条记录数
	 * @author fujie
	 */
	protected static Map<String,Object> success(Object obj,long totalPage,long totalRecordNum) {
		
		Map<String,Object> successMap = new HashMap<String,Object>();
		
		successMap.put("code", "0");
		successMap.put("msg", "成功");
		successMap.put("data", obj);
		
		Map<String,Long> pageMap = new HashMap<String,Long>();
		pageMap.put("totalPage", totalPage);
		pageMap.put("totalRecordNum", totalRecordNum);
		
		successMap.put("page", pageMap);
		
		return successMap;
	}
	
	/** 
	 * @Description: 数据操作成功
	 * @author fujie
	 */
	private static Map<String,Object> success() {
		
		Map<String,Object> successMap = new HashMap<String,Object>();
		
		successMap.put("code", "0");
		successMap.put("msg", "成功");
		
		return successMap;
	}
	
	protected static Map<String,Object> error(BusinessException e) {
		
		Map<String,Object> errorMap = new HashMap<String,Object>();
		
		errorMap.put("code", e.getCode());
		errorMap.put("msg", e.getMsg());
		
		log.info("接口错误信息,错误码:"+e.getCode()+",错误信息:"+e.getMsg());
		
		return errorMap;
	}
	
	protected static Map<String,Object> error(int errCode) {
		
		return error(BusinessException.createErr(String.valueOf(errCode)));
	}
	
	/**
	 * @Description: JSR 303 验证失败错误封装信息
	 * @author fujie
	 */
	protected static Map<String,Object> error(Map<String,String> errorMap) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		String errorMsg  = "";
		for(String key : errorMap.keySet()){
			errorMsg += errorMap.get(key)+ ";";
		}
		
		map.put("code", "4999");
		map.put("msg", errorMsg.substring(0,errorMsg.lastIndexOf(";")));
		
		return map;
	}
	
	protected static PageRequest createPageRequest(ReqHeader header){
		
		return new PageRequest(header.getPagenum(),header.getPagesize());
	}
	
	/**
	 * @Description: JSR 303 检验
	 * @author fujie
	 */
//	protected Map<String,String> jsrValidate(Object entity){
//		
//		Map<String, String> errorsMap = new HashMap<String, String>();
//		
//		try{
//			// 调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
//			BeanValidators.validateWithException(validator, entity);
//			
//		}catch(ConstraintViolationException ex){
//			
//			ex.printStackTrace();
//			
//			errorsMap = BeanValidators.extractPropertyAndMessage(ex.getConstraintViolations());
//		}
//		
//		return errorsMap;
//	}

	/**
	 * @Title: getJSConfig 
	 * @Description: jsonconfig配置
	 * @param @return
	 * @return JsonConfig  
	 * @throws
	 */
	protected JsonConfig getJSConfig(){
		JsonConfig jsonConfig = new JsonConfig();  
		jsonConfig.registerDefaultValueProcessor(Integer.class,  
		        new DefaultValueProcessor() {  
		            public Object getDefaultValue(Class type) {  
		                return null;  
		            }  
		        }); 
		jsonConfig.registerDefaultValueProcessor(Long.class,  
		        new DefaultValueProcessor() {  
		            public Object getDefaultValue(Class type) {  
		                return null;  
		            }  
		        }); 
		
		return jsonConfig;
	}
	
	
//	protected void success(JSONObject json) {
//		json.put("code", "0");
//		json.put("msg", "成功");
//	}
//	
//	protected void error(JSONObject json, BusinessException e) {
//		json.put("code", e.getCode());
//		json.put("msg", e.getMsg());
//	}

}
 