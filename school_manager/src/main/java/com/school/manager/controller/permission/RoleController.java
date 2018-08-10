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
import com.school.biz.domain.entity.security.Role;
import com.school.biz.domain.entity.user.AdminUser;
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
@RequestMapping(value = "/permission/role")
public class RoleController extends BaseEasyWebController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AdminUserService adminUserService;
	
	{
		listView = "permission/role";
	}
	
	/**
	 * 分页查询
	 */
	@Override
	protected void onList(PageInfo pageInfo, Map<String, Object> searchParams, HttpServletRequest request,
						  ModelAndView mav) throws FuBusinessException {
		try {
			mav.addObject("listData",JSON.toJSON(roleService.queryPage(searchParams)));
			mav.addObject(PAGE_PARAM_PAGECOUNT, pageInfo.getTotalPage());
			mav.addObject(PAGE_PARAM_TOTALCOUNT, pageInfo.getTotalRecord());
			
			mav.addObject(ConstantUrl.DETAIL_URL, ConstantUrl.ROLE_DETAIL_URL);// 详情url
			mav.addObject(ConstantUrl.EDIT_URL, ConstantUrl.ROLE_EDIT_URL);// 编辑url
			mav.addObject(ConstantUrl.DEL_URL,ConstantUrl.ROLE_DEL_URL);// 删除url
		} catch (Exception e) {
			log.error("角色分页查询出现错误："+e.getMessage());
			throw webExp(e);
		}
	}
	
	/**
	 * 查看详情
	 */
	@RequestMapping("/detail.do")
	public ModelAndView detail(Long id){
		try{
    		ModelAndView mv = new ModelAndView("permission/roleDetail");
    		Role role = roleService.get(id);
    		mv.addObject("role", JSON.toJSON(role));
    		
    		// 根据角色获取资源
    		mv.addObject("zNodes",JSON.toJSON(roleService.getZNodes(id)));
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
		mav.setViewName("permission/roleEdit");
		Role role = roleService.get(id);
		mav.addObject("role", JSON.toJSON(role));
		
		// 根据角色获取资源
		mav.addObject("zNodes",JSON.toJSON(roleService.getZNodes(id)));
		return mav;
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save.do")
	public AjaxResult save(Role role,String resIds){
		try{
			if(StringUtils.isBlank(role.getRoleName())){
				return AjaxResult.fail("请填写角色名称");
			}
			roleService.saveOrUpdate(role);
			
			// 保存资源
			roleService.saveRoleResource(role.getId(),resIds);
			return AjaxResult.success("保存成功", JSON.toJSON(role));
		}catch(Exception e){
			log.error("保存角色出错：" + e.getMessage());
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
			if(id.equals(user.getRoleId())){
				return AjaxResult.fail("不能删除自己的角色");
			}
			
			AdminUser admin = adminUserService.findByLoginName(Constants.ADMIN_LOGIN_NAME);
			if(id.equals(admin.getRoleId())){
				return AjaxResult.fail("不能删除admin账户的角色");
			}
			
			roleService.deleteById(id);
			return AjaxResult.success("删除成功");
		} catch (Exception e) {
			log.error("删除角色出错：" + e.getMessage());
			return AjaxResult.fail("删除失败");
		}
	}
	
	/**
	 * 判断角色名称是否不重复（如果是修改，则还需要判断是否真的更改）
	 */
	@ResponseBody
	@RequestMapping("/checkIsNotExist")
	public Object checkIsNotExist(Long roleId,String roleName){
		return roleService.checkIsNotExist(roleId,roleName);
	}
}
