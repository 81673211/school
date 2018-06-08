package com.school.domain.entity.order;

import java.util.Date;

import lombok.Data;

/**
 *
 * <b>Description:订单日志pojo.</b><br>
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:OrderLog</b>
 * <br><b>Date:</b> 08/04/2018 16:03
 */
@Data
public class OrderLog {

    /**
     * 日志ID.
     */
    private Long id;

    /**
     * 订单号.
     */
    private String orderId;

    /**
     * 修改前的状态.
     */
    private String preStatus;

    /**
     * 修改后的状态.
     */
    private String postStatus;

    /**
     * 备注.
     */
    private String remark;

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
