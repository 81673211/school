package com.school.domain.dto.order;

import lombok.Data;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 07/06/2018 17:00
 */
@Data
public class OrderQueryParam {

    /**
     * 订单状态.
     */
    private String status;

    /**
     * 寄件人电话.
     */
    private String senderPhone;

    /**
     * 收件人电话.
     */
    private String receiverPhone;

    /**
     * 快递公司编号.
     */
    private String companyCode;

}
