package com.school.web.wechat;

import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.school.service.wechat.WxPayService;

@Controller
@RequestMapping("/wxpay")
public class WxPayController {

	@Autowired
	private WxPayService wxPayService;
	
	@RequestMapping("/pay")
	public ModelAndView  wxpay(){
		ModelAndView mv = new ModelAndView();
		String openId = "oSAxK1JJDAm5rT9m4K7CWBOyDb2E";
		// 统一下单
		TreeMap<String, String> resultMap = wxPayService.doUnifiedOrder(openId);
		System.out.println(resultMap);
		resultMap.put("pkg", resultMap.get("package"));
		resultMap.remove("package");
		mv.setViewName("wxpay/wxpay");
		mv.addObject("resultMap",resultMap);
		return mv;
	}
	
}
