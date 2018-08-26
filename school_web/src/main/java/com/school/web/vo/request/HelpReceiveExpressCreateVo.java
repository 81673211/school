package com.school.web.vo.request;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author jame
 */
@Validated
@Data
public class HelpReceiveExpressCreateVo {
    @NotNull(message = "openId不能为空")
    private String openId;
    @NotNull(message = "收件人联系电话不为空")
    private String receiverPhone;
    @NotNull(message = "收件人姓名不为空")
    private String receiverName;
    @NotNull(message = "快递公司不为空")
    private Long companyId;
    @NotNull(message = "取件地址不为空")
    private String helpReceiveAddr;
    //取件码，可不填
    private String helpReceiveCode;
}
