package com.school.manager.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.school.biz.service.security.MenuInfoService;
import com.school.manager.controller.base.BaseWebController;

@Controller
@RequestMapping(value = "/console/index")
public class IndexController extends BaseWebController{
	@Autowired
	private MenuInfoService caseMenusService;
	
    @RequestMapping(value = "main")
    public String index(Model model,HttpServletRequest request) {
    	return "index/main";
    }
    @RequestMapping(value = "top")
    public String top(Model model,HttpServletRequest request) {
        return "login";
    }
    
    /**
	 * 跳转到没有权限页面
	 * @return
	 */
	@RequestMapping("/forbidden")
	public String forbidden(Model model) {
		return "error/forbidden";
	}

}
