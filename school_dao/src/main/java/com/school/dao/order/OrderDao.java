package com.school.dao.order;

import java.util.List;

import com.school.domain.dto.order.OrderQueryParam;
import com.school.domain.dto.order.OrderQueryResult;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 07/06/2018 15:19
 */
public interface OrderDao {

    List<OrderQueryResult> query(OrderQueryParam param);
}
