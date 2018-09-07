package com.school.biz.service.order.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.biz.dao.order.RefundOrderInfoMapper;
import com.school.biz.domain.entity.order.OrderInfo;
import com.school.biz.domain.entity.order.RefundOrderInfo;
import com.school.biz.enumeration.ExpressTypeEnum;
import com.school.biz.enumeration.RefundOrderStatusEnum;
import com.school.biz.service.base.impl.BaseServiceImpl;
import com.school.biz.service.order.RefundOrderInfoService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class RefundOrderInfoServiceImpl extends BaseServiceImpl<RefundOrderInfo, RefundOrderInfoMapper> implements RefundOrderInfoService {
	
	@Autowired
	private RefundOrderInfoMapper refundOrderInfoMapper;
	
	@Override
	public void refundOrderUpdateToSuccess(RefundOrderInfo refundOrderInfo) {
		if (refundOrderInfo == null) {
            return;
        }
        refundOrderInfo.setStatus(RefundOrderStatusEnum.SUCCESS.getCode());
        refundOrderInfo.setSucTime(new Date());
        refundOrderInfoMapper.updateByPrimaryKeySelective(refundOrderInfo);
	}

	@Override
	public void refundOrderUpdateToRefunding(RefundOrderInfo refundOrderInfo) {
		if (refundOrderInfo == null) {
            return;
        }
        refundOrderInfo.setStatus(RefundOrderStatusEnum.REFUNDING.getCode());
        refundOrderInfo.setSucTime(new Date());
        refundOrderInfoMapper.updateByPrimaryKeySelective(refundOrderInfo);
	}

	@Override
	public void refundOrderUpdateToFailed(RefundOrderInfo refundOrderInfo) {
		if (refundOrderInfo == null) {
            return;
        }
        refundOrderInfo.setStatus(RefundOrderStatusEnum.FAILED.getCode());
        refundOrderInfo.setSucTime(new Date());
        refundOrderInfoMapper.updateByPrimaryKeySelective(refundOrderInfo);
	}

	@Override
	public List<RefundOrderInfo> getNotRefundOrder() {
		return refundOrderInfoMapper.getNotRefundOrder();
	}

	@Override
	public List<OrderInfo> queryPage(Map<String, Object> paramMap) {
		return refundOrderInfoMapper.queryPage(paramMap);
	}

	@Override
	public void fillExpressNo(Long expressId, String expressNo) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("expressId", expressId);
		map.put("expressNo", expressNo);
		map.put("expressType", ExpressTypeEnum.SEND.getFlag());
		refundOrderInfoMapper.fillExpressNo(map);
	}

}