package com.school.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 07/06/2018 13:01
 */
@AllArgsConstructor
@Getter
public enum OrderStatusEnum {

    PROXY_RECIEVED("proxy_received", "代理处已签收"),
    WAITING_PROXY_OBTAIN("waiting_proxy_obtain", "待从代理处取件"),
    PROXY_OBTAINED("", "已从代理处取件"),
    UNPAID("unpaid", "待支付，中间状态，用户不可见"),
    PAID("paid", "已支付"),
    CABINET_RECIEVED("cabinet_received", "已放入柜子"),
    CABINET_OBTAINED("cabinet_obtained", "已从柜子取件"),
    RETRIEVED("retrieved", "已回收");

    private String code;

    private String message;
}
