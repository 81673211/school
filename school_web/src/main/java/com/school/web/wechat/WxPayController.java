package com.school.web.wechat;

import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.school.domain.entity.order.OrderInfo;
import com.school.service.order.OrderInfoService;
import com.school.service.wechat.WxPayService;
import com.school.vo.request.OrderCreateVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/wxpay")
public class WxPayController {

	@Autowired
	private WxPayService wxPayService;

	@Autowired
	private OrderInfoService orderInfoService;

	/**
	 * 微信支付
	 */
	@RequestMapping(value = "/pay")
	public ModelAndView wxpay(Long expressId) {
		try {
			String orderNo;
			OrderInfo orderInfo = orderInfoService.findByExpressReceiveId(expressId);
			if (orderInfo == null) {
				OrderCreateVo orderCreateVo = new OrderCreateVo();
				orderCreateVo.setExpressId(expressId);
				orderNo = orderInfoService.createReceiveOrder(orderCreateVo);
			} else {
				orderNo = orderInfo.getOrderNo();
			}
			ModelAndView mv = new ModelAndView();
			// 统一下单
			if (StringUtils.isBlank(orderNo)) {
				throw new RuntimeException("订单号异常");
			}
			TreeMap<String, String> resultMap = wxPayService.doUnifiedOrder(orderNo);
			// 避免关键字package，将package换成pkg
			resultMap.put("pkg", resultMap.get("package"));
			resultMap.remove("package");
			mv.setViewName("wxpay/wxpay");
			mv.addObject("resultMap",resultMap);
			return mv;
		} catch (Exception e) {
			log.error("收件ID:{}, 支付失败，原因:", expressId, e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * 支付通知
	 */
	@ResponseBody
	@RequestMapping("/notify")
	public String wxNotify(HttpServletRequest request){
	    try {
	    	log.info("===============>>>>微信支付回调获取数据开始");  
			String resXml = wxPayService.wxPayNotify(request);
			//向微信输出处理结果，如果成功（SUCCESS），微信就不会继续调用了，否则微信会连续调用8次
			return "SUCCESS";
		} catch (Exception e) {
			log.error("微信支付通知失败:" + e.getMessage());
			return null;
		}
	}

    @RequestMapping("/success")
	public ModelAndView success(String amountVal) {
	    ModelAndView mav = new ModelAndView("wxpay/pay_suc");
	    mav.addObject("amount", amountVal);
	    return mav;
    }
}
