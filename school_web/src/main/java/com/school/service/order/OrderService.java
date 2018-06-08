package com.school.service.order;

import java.util.List;

import com.school.dao.order.OrderDao;
import com.school.domain.dto.order.OrderQueryParam;
import com.school.domain.dto.order.OrderQueryResult;
import com.school.domain.entity.order.Order;
import com.school.service.base.BaseService;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 07/06/2018 19:40
 */
public interface OrderService extends BaseService<Order, OrderDao> {

    List<OrderQueryResult> query(OrderQueryParam param);
}
