package com.school.manager.controller.permission;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.school.biz.constant.ConstantUrl;
import com.school.biz.constant.Constants;
import com.school.biz.constant.StatusManage;
import com.school.biz.domain.entity.user.AdminUser;
import com.school.biz.domain.entity.user.AdminUserVo;
import com.school.biz.exception.FuBusinessException;
import com.school.biz.util.pager.PageInfo;
import com.school.manager.vo.AjaxResult;
import com.school.biz.service.security.RoleService;
import com.school.biz.service.user.AdminUserService;
import com.school.manager.util.SessionUtils;
import com.school.manager.controller.base.BaseEasyWebController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/permission/adminUser")
public class AdminUserController extends BaseEasyWebController {

	@Autowired
	private AdminUserService adminUserService;
	
	@Autowired
	private RoleService roleService;
	
	{
		listView = "permission/adminUser";
	}
	
	/**
	 * 分页查询
	 */
	@Override
	protected void onList(PageInfo pageInfo, Map<String, Object> searchParams, HttpServletRequest request,
						  ModelAndView mav) throws FuBusinessException {
		try {
			mav.addObject("listData",JSON.toJSON(adminUserService.queryPage(searchParams)));
			mav.addObject(PAGE_PARAM_PAGECOUNT, pageInfo.getTotalPage());
			mav.addObject(PAGE_PARAM_TOTALCOUNT, pageInfo.getTotalRecord());
			
			mav.addObject(ConstantUrl.DETAIL_URL, ConstantUrl.ADMIN_USER_DETAIL_URL);// 详情url
			mav.addObject(ConstantUrl.EDIT_URL, ConstantUrl.ADMIN_USER_EDIT_URL);// 编辑url
			mav.addObject(ConstantUrl.DEL_URL,ConstantUrl.ADMIN_USER_DEL_URL);// 删除url
			
			mav.addObject("adminUserStatusMap",JSON.toJSON(StatusManage.adminUserStatusMap));
			mav.addObject("roles",JSON.toJSON(roleService.getAll()));
		} catch (Exception e) {
			log.error("用户分页查询出现错误："+e.getMessage());
			throw webExp(e);
		}
	}
	
	/**
	 * 查看详情
	 */
	@RequestMapping("/detail.do")
	public ModelAndView detail(Long id){
		try{
    		ModelAndView mv = new ModelAndView("permission/adminUserDetail");
    		AdminUserVo adminUser = adminUserService.detail(id);
    		mv.addObject("adminUser", JSON.toJSON(adminUser));
    		mv.addObject("adminUserStatusMap",JSON.toJSON(StatusManage.adminUserStatusMap));
    		return mv;
    	}catch(Exception e){
    		throw webExp(e);
    	}
	}
	
	/**
	 * 编辑页面
	 */
	@RequestMapping(value = "/edit.do")
	public ModelAndView edit(Long id, HttpServletRequest request){
		ModelAndView mav= new ModelAndView();
		mav.setViewName("permission/adminUserEdit");
		AdminUser adminUser = adminUserService.get(id);
		mav.addObject("user", JSON.toJSON(adminUser));
		mav.addObject("adminUserStatusMap",StatusManage.adminUserStatusMap);
		mav.addObject("roles",JSON.toJSON(roleService.getAll()));
		return mav;
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save.do")
	public AjaxResult save(AdminUser adminUser,HttpServletRequest request){
		try{
			if(StringUtils.isBlank(adminUser.getAdminName())){
				return AjaxResult.fail("请填写用户名称");
			}
			
			// 不能失效自己和admin账户
			AdminUser user = SessionUtils.getSessionUser(request);
			AdminUser originUser = adminUserService.get(adminUser.getId());
			if(adminUser.getStatus().equals(StatusManage.ADMIN_USER_STATUS_OFF)){
				if(adminUser.getId().equals(user.getId())){
					return AjaxResult.fail("不能将自己失效");
				}
				if(Constants.ADMIN_LOGIN_NAME.equals(originUser.getLoginName())){
					return AjaxResult.fail("不能将admin账户失效");
				}
			}
			
			// 不能修改admin账户的角色
			if(!adminUser.getRoleId().equals(originUser.getRoleId()) && Constants.ADMIN_LOGIN_NAME.equals(originUser.getLoginName())){
				return AjaxResult.fail("不能修改admin账户的角色");
			}
			
			adminUserService.saveOrUpdate(adminUser);
			return AjaxResult.success("保存成功", JSON.toJSON(adminUser));
		}catch(Exception e){
			log.error("保存用户出错：" + e.getMessage());
			return AjaxResult.fail("保存失败");
		}
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/del.do")
	public AjaxResult del(Long id,HttpServletRequest request){
		try {
			AdminUser user = SessionUtils.getSessionUser(request);
			if(id.equals(user.getId())){
				return AjaxResult.fail("不能删除自己");
			}
			AdminUser adminUser = adminUserService.get(id);
			if(Constants.ADMIN_LOGIN_NAME.equals(adminUser.getLoginName())){
				return AjaxResult.fail("不能删除admin账户");
			}
			adminUserService.deleteById(id);
			return AjaxResult.success("删除成功");
		} catch (Exception e) {
			log.error("删除用户出错：" + e.getMessage());
			return AjaxResult.fail("删除失败");
		}
	}
	
	/**
	 * 判断登录账号是否不重复（如果是修改，则还需要判断是否真的更改）
	 */
	@ResponseBody
	@RequestMapping("/checkIsNotExist")
	public Object checkIsNotExist(Long userId,String loginName){
		return adminUserService.checkIsNotExist(userId,loginName);
	}
}
