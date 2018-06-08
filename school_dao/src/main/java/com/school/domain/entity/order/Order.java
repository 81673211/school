package com.school.domain.entity.order;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 *
 * <b>Description:订单pojo.</b><br>
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:Order</b>
 * <br><b>Date:</b> 07/06/2018 12:54
 */
@Data
public class Order {

    /**
     * 订单号.
     */
    private String id;

    /**
     * 快件ID.
     */
    private Long expressId;

    /**
     * 订单状态，参照 com.school.common.OrderStatusEnum .
     */
    private String status;

    /**
     * 订单金额.
     */
    private BigDecimal amount = BigDecimal.ZERO;

    /**
     * 订单是否已完成.
     */
    private boolean finished;

    /**
     * 版本.
     */
    private int version;

    /**
     * 创建人.
     */
    private String creator;
    /**
     * 创建时间.
     */
    private Date createdTime;

    /**
     * 修改人.
     */
    private String modifier;

    /**
     * 修改时间.
     */
    private Date modifiedTime;

    /**
     * 是否已删除.
     */
    private boolean deleted;
}
