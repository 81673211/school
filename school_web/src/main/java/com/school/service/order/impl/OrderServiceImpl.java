package com.school.service.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.dao.order.OrderDao;
import com.school.domain.dto.order.OrderQueryParam;
import com.school.domain.dto.order.OrderQueryResult;
import com.school.domain.entity.order.Order;
import com.school.service.order.OrderService;
import com.school.util.core.exception.FuServiceException;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 07/06/2018 19:44
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public List<OrderQueryResult> query(OrderQueryParam param) {
        return orderDao.query(param);
    }

    @Override
    public Order save(Order order) throws FuServiceException {
        return null;
    }

    @Override
    public Order update(Order order) throws FuServiceException {
        return null;
    }

    @Override
    public Order get(Long id) throws FuServiceException {
        return null;
    }

    @Override
    public void deleteById(Long id) throws FuServiceException {

    }
}
