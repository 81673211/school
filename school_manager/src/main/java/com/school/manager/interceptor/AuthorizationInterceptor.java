package com.school.manager.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.school.biz.constant.Constants;
import com.school.manager.util.HttpSessionThreadLocal;
import com.school.biz.domain.entity.resource.ResourceInfo;

import lombok.extern.slf4j.Slf4j;



/**    
 *        
 * 类名称：AuthorizationInterceptor    
 * 类描述：拦截器（权限）
 * 创建人：LiBo    
 * 创建时间：2016年6月24日 下午3:51:43    
 * 最后修改人：LiBo    
 * 最后修改时间：2016年6月24日 下午3:51:43    
 * 修改备注：    
 * @version 1.0    
 *     
 */
@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor{

	
	@Override
	public void afterCompletion(HttpServletRequest arg0,HttpServletResponse arg1, Object arg2, Exception arg3)throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,Object arg2, ModelAndView arg3) throws Exception {
		HttpSessionThreadLocal.getInstance().set(null);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object arg2) throws Exception {
		//权限检测
		String target = request.getRequestURI().substring(request.getContextPath().length());
		target = target.replaceAll("/[0-9]+", "");
		log.debug("===========:"+target);
		HttpSession session = request.getSession();
		List<ResourceInfo> resources = (List<ResourceInfo>) session.getAttribute(Constants.USER_SESSION_RESOURCES);
        for(ResourceInfo res:resources){
        	if(target.equals(res.getResUrl())){
        		 return true;
        	}
        }
        log.warn("no permission----------->" + target);
        response.sendRedirect(request.getContextPath()+"/console/index/forbidden");
        return false;
	}

	public static String getRelativePath(HttpServletRequest req) {
	    return req.getRequestURI().substring(req.getContextPath().length());
	}
	
	/**
	 * 无需过滤地址
	 */
	public static boolean notInterceptor(String uri) {
		String[] pathArr = new String[]{	
				"/console/merchantInfo/finishinfo",
				"/console/merchantInfo/result",
				"/console/merchantInfo/reset",
				"/console/merchant/register",
				//商户注册-wh
				"/console/merchantInfo/register",				
				"/console/merchantInfo/edit",				
				"/merchant/upload/uploadGoods",
				"/managers/msg/send",				
				"/console/merchantInfo/judge",
				/*"/merchant/price/add",
				"/merchant/price/addCarDiscountInfo",
				"/merchant/car/updateDelivery",*/
		};
		for (int i = 0; i < pathArr.length; i++) {
			if (uri.indexOf(pathArr[i]) >= 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 清除缓存
	 * @author yao
	 * @since 2016-01-05 16:08
	 * @param session
	 */
	public static void cancelSession(HttpSession session){
		session.removeAttribute(Constants.ATTRIBUTE_ADMIN_USER);
		session.removeAttribute(Constants.ATTRIBUTE_ADMIN_USER_MENU);
//		session.removeAttribute(Constants.ATTRIBUTE_ADMIN_MERCHANT);
//		session.removeAttribute(Constants.ATTRIBUTE_ADMIN_BTN_MENU);
//		session.removeAttribute(Constants.ATTRIBUTE_ADMIN_ALL_MENU);
		session.invalidate();
	}
}
