/**
 * Title: MOBAO <BR>
 * Description: todo 
 * Copyright: Copyright (c) 2014-2015<BR>
 * Company: 成都摩宝网络科技有限公司(www.mobo360.com/)<BR>
 * 
 * @author linqingsong
 * @version 1.0
 */
package com.school.manager.controller.usermanager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.school.biz.constant.Constants;
import com.school.biz.util.MD5Util;
import com.school.manager.filter.UserSessionManager;
import com.school.manager.vo.AjaxResult;
import com.school.biz.domain.entity.resource.ResourceInfo;
import com.school.biz.domain.entity.user.AdminUser;
import com.school.biz.domain.entity.security.LeftMenu;
import com.school.biz.service.resource.ResourceInfoService;
import com.school.biz.service.security.MenuInfoService;
import com.school.biz.service.user.AdminUserService;
import com.school.manager.controller.base.BaseWebController;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: UserManagerController
 * @Description:  用户管理
 * @author linqingsong
 * @date Oct 15, 2015 2:25:32 PM
 */
@Slf4j
@Controller
@RequestMapping(value = "/managers/user")
public class UserManagerController extends BaseWebController{
	
//	@Autowired
//	private ManagerInfoService managerInfoService;
	
//	@Autowired
//	private RoleInfoService roleInfoService;
	
	@Autowired
	private MenuInfoService menuInfoService;
	
	@Autowired
	private ResourceInfoService resourceInfoService;
	
	@Autowired
	private AdminUserService adminUserService;
	
	  {
	  	listView = "platform/user/list";
	  	editView = "platform/user/addUser";
	  }
	  
	/**
	 * @Title: toLogin 
	 * @Description: 跳转到登录页面
	 * @param @param model
	 * @param @param request
	 * @param @return
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value = "toLogin")
	public String toLogin(Model model,HttpServletRequest request,String order){

	    HttpSession session = request.getSession();
		model.addAttribute("error", request.getParameter("error"));
		model.addAttribute("userName",request.getParameter("userName"));
		return "login";
	}
	
	
	/**
	 * @Title: login 
	 * @Description: 登录
	 * @param @param userName
	 * @param @param password
	 * @param @param model
	 * @param @param request
	 * @param @return
	 * @param @throws Exception
	 * @return String  
	 * @throws Exception 
	 * @throws
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(String userName,String password,ModelMap model,ModelAndView modelAndView,
			HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		String succNextUrl = new String(); //登录成功所转地址
		//1 校验验证码
		//2.1 商户信息	
		AdminUser adminUser = adminUserService.getLoginUserByLoginNameAndLoginPassword(userName,password);
        if (adminUser == null) {
        	model.addAttribute("error","用户名或密码错误");
        	model.addAttribute("userName",userName);
        	return "redirect:/managers/user/toLogin";
        }
        succNextUrl =  "redirect:/console/index/main";
        
        // 查询菜单
        Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", adminUser.getId().toString());
        List<LeftMenu> leftMenus = menuInfoService.getMyLeftMenus(map);
		request.getSession().setAttribute(Constants.USER_SESSION_MENUS, JSON.toJSON(leftMenus));
		
		// 查询资源
		List<ResourceInfo> resourceInfos = resourceInfoService.getOwnResources(adminUser.getId());
		request.getSession().setAttribute(Constants.USER_SESSION_RESOURCES, resourceInfos);
		
		UserSessionManager.addSession(adminUser.getId().toString(), adminUser);
        adminUser.setSessionId(session.getId());
        adminUser.setLastRequestTime(System.currentTimeMillis());
        session.setAttribute(Constants.ATTRIBUTE_ADMIN_USER, adminUser);
		return succNextUrl;
	}
	
	/**
	 * 添加用户角色
	 * @author yao
	 * @since 2015-12-15 11:51
	 */ 
//	@RequestMapping(value = "add", method = RequestMethod.POST)	
//	public String add(@Valid @ModelAttribute("user") ManagerInfo user,Long userId,Model model,HttpServletRequest request) {
//		String userName = user.getUsername();
//		String password = user.getPassword();
//		//校验
//		if(StringHelper.isEmpty(userName)){
//			model.addAttribute("errorName","请输入用户账号");
//			return editView;
//		}
//		
//		try {
//			//修改
//			if(userId!=null && userId>0){
//				ManagerInfo usermanager=managerInfoService.get(userId);
//				usermanager.setRoleId(user.getRoleId());
//				usermanager.setUsername(user.getUsername());
//				managerInfoService.saveOrUpdate(usermanager);
//			}else{//添加
//				ManagerInfo existUser =  managerInfoService.getUserByName(userName);
//				if(null!=existUser){
//					model.addAttribute("errorName","用户账号已存在");
//					return editView;
//				}
//				if(StringHelper.isEmpty(password)){
//					model.addAttribute("errorPwd","请输入用户密码");
//					return editView;
//				}
//				user.setStatus(1);
//				user.setCrTime(DateUtil.now());
//				managerInfoService.addUser(user);
//			}
//			
//			List<RoleInfo> roles;
//			try {
//				roles = roleInfoService.getAll();
//			} catch (Exception e1) {
//				log.error("add user error",e1);
//				return "error";
//			}
//			model.addAttribute("roles", roles);
//			
//			return "redirect:/managers/user/list.do";
//		} catch (Exception e) {
//			log.error("add user error",e);
//			return "error";
//		}
//	}
	/**
	 * 编辑用户
	 * @author yao
	 * @since 2015-12-15 11:51
	 */
//	@RequestMapping(value = "edit", method = RequestMethod.POST)	
//	public String edit(@Valid @ModelAttribute("user") ManagerInfo user,Long userId,Model model,HttpServletRequest request) {
//		String userName = user.getUsername();
//		String password = user.getPassword();
//		//校验
//		if(StringHelper.isEmpty(userName)){
//			model.addAttribute("errorName","请输入用户账号");
//			return editView;
//		}
//		
//		try {
//			//修改
//			if(userId!=null && userId>0){
//				ManagerInfo usermanager=managerInfoService.get(userId);
//				usermanager.setRoleId(user.getRoleId());
//				usermanager.setUsername(user.getUsername());
//				managerInfoService.saveOrUpdate(usermanager);
//			}else{
//				ManagerInfo existUser =  managerInfoService.getUserByName(userName);
//				if(null!=existUser){
//					model.addAttribute("errorName","用户账号已存在");
//					return editView;
//				}
//				if(StringHelper.isEmpty(password)){
//					model.addAttribute("errorPwd","请输入用户密码");
//					return editView;
//				}
//				user.setStatus(1);
//				managerInfoService.addUser(user);
//			}	
//			List<RoleInfo> roles;
//			try {
//				roles = roleInfoService.getAll();
//			} catch (Exception e1) {
//				log.error("add user error",e1);
//				return "error";
//			}
//			model.addAttribute("roles", roles);	
//			return "redirect:/managers/user/list.do";
//		} catch (Exception e) {
//			log.error("add user error",e);
//			return "error";
//		}
//	}
	
//	@Override
//	protected void onList(PageRequest pageRequest,
//			Map<String, Object> searchParams,
//			HttpServletRequest request, ModelAndView mav) throws Exception {
//		try {
//			Page<ManagerInfo>users =  managerInfoService.queryUsers(pageRequest, searchParams);
//			mav.addObject("users", users.getContent());
//			mav.addObject(PAGE_PARAM_PAGECOUNT, users.getTotalPages());
//			mav.addObject(PAGE_PARAM_TOTALCOUNT, users.getTotalElements());
//			
//			//用户按钮权限
//			HttpSession session = request.getSession();
//			Map<String, Object> btnMap = (Map<String, Object>) session.getAttribute(Constants.ATTRIBUTE_ADMIN_BTN_MENU);
//			if(btnMap.get("/managers/user/add.do") != null){
//				mav.addObject("add","yes");
//			}
//			if(btnMap.get("/managers/user/edit.do") != null){
//				mav.addObject("edit","yes");
//			}
//		} catch (Exception e) {
//			log.error("list user error",e);			
//		}
//	}	
	
	/**
	 * 
	 * @Title: toAddUser 
	 * @Description: 跳转到添加用户
	 * @param @param model
	 * @param @return
	 * @return String  
	 * @throws
	 */
//	@RequestMapping(value = "toAddUser")	
//	public String toAddUser(Model model) {
//		try {
//			List<RoleInfo>roles = roleInfoService.getAll();
//			model.addAttribute("roles", roles);
//			return "/platform/user/addUser";
//		} catch (Exception e) {
//			log.error("doAddUser error",e);
//			return "error";
//		}	
//	}
//	
	
	@ResponseBody
	@RequestMapping(value="doUpdateUserPwd.do",method = RequestMethod.POST)
	public Object doUpdateUserPwd(String oldPassword,
			String newPassword,HttpServletRequest request) {
		try {
			   if(StringUtils.isBlank(oldPassword)){
				   throw new Exception("原密码不能为空！");
			   }
			   if(StringUtils.isBlank(newPassword)){
				   throw new Exception("新密码不能为空！");
			   }
			   
			   if(oldPassword.equals(newPassword)){
				   log.debug("修改密码失败：旧密码和新密码不能相同！");
				   return AjaxResult.fail("新密码不能和旧密码相同！");
			   }
			   // 拿到session中登录用户的密码，进行比较
			   AdminUser loginUser = (AdminUser) request.getSession().getAttribute(
						Constants.ATTRIBUTE_ADMIN_USER);
			   oldPassword = MD5Util.getMD5(oldPassword);
			   if(!oldPassword.equals(loginUser.getPassword())){
				   log.debug("修改密码失败：密码输入不正确！");
				   return AjaxResult.fail("旧密码不正确！");
			   }
			   newPassword = MD5Util.getMD5(newPassword);
			   Long id = loginUser.getId();
			   AdminUser adminUser = adminUserService.get(id);
			   adminUser.setPassword(newPassword);
			   AdminUser ret = adminUserService.update(adminUser);
			   if(ret != null){
				   log.debug("密码修改成功！");
				   return AjaxResult.success("密码修改成功！");
			   }
			   return AjaxResult.fail("密码修改失败！");
			} catch (Exception e) {
				log.error("修改密码时出现系统故障："+e.getMessage());
				return AjaxResult.fail("出现系统故障！");
			}
		
	}
	
	/**
	 * @Title: logout 
	 * @Description: 登录退出
	 * @param @param request
	 * @param @return
	 * @return String  
	 * @throws
	 */
	@RequestMapping(value = "/logout.htm")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false); // session已经过期则不必创建
		if (session == null) {
			return "login";
		}
		// 注意顺序：销毁Session前需先把用户名取出来
		cancelSession(session);
		return "login";
	}
//	
//
//	@Override
//	protected void onEdit(Long id, HttpServletRequest request, Model model)
//			throws Exception {
//		if(id>0){
//			ManagerInfo user = managerInfoService.get(id);
//			List<RoleInfo>roles = roleInfoService.getAll();
//			
//			model.addAttribute("roles", roles);
//			model.addAttribute("user", user);
//		}
//	}
//	
//	/**
//	 * 判断用户名
//	 * @author yao
//	 * @since 2016-01-26 10:03
//	 * @param username 用户名
//	 * @param id 用户id
//	 */
//	@ResponseBody
//	@RequestMapping(value="judgeName",method=RequestMethod.POST,produces = MediaTypes.JSON_UTF_8)
//	public Map<String, Object> judgeName(String username,Long id) {
//		if (judgeUserName(username,id)) {
//			return error(new BusinessException("-1","此角色名已存在"));
//		}
//		return SUCCESS;
//	}
	
	/**
	 * 清除缓存
	 * @author yao
	 * @since 2016-01-05 16:08
	 * @param session
	 */
	public static void cancelSession(HttpSession session){
		session.removeAttribute(Constants.ATTRIBUTE_ADMIN_USER);
//		session.removeAttribute(Constants.ATTRIBUTE_ADMIN_MERCHANT);
		session.removeAttribute(Constants.ATTRIBUTE_ADMIN_USER_MENU);
		session.removeAttribute(Constants.ATTRIBUTE_ADMIN_BTN_MENU);
		session.removeAttribute(Constants.ATTRIBUTE_ADMIN_ALL_MENU);
		session.invalidate();
	}
//	
//	/**
//	 * 判断用户名是否存在
//	 * @param username 用户名
//	 * @param id 用户id
//	 * @return boolean true--已存在 false---不存在
//	 */
//	public boolean judgeUserName(String username,Long id) {
////		//1 判断商户是否存在相同的用户名
////		MerchantVo merchantVo = new MerchantVo();
////		merchantVo.setPhone(username);
////		Merchant merchant = merchantService.queryMerchantInfo(merchantVo);
////		if (merchant != null) {
////			return true;
////		}
//		//2 判断管理台是否已存在
//		ManagerInfo existUser =  managerInfoService.getUserByName(username);
//		if (existUser != null) {
//			if (id == null ) {
//				return true;
//			}else {
//				if (id.equals(existUser.getId())) {
//					return false;
//				}else {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
}
