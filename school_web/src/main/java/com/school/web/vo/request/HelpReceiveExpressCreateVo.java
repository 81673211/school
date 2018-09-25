package com.school.web.vo.request;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import lombok.Data;

/**
 * @author jame
 */
@Validated
@Data
public class HelpReceiveExpressCreateVo {
    @NotNull(message = "openId不能为空")
    private String openId;
    @NotNull(message = "收件人联系电话不能为空")
    private String receiverPhone;
    @NotNull(message = "收件人姓名不能为空")
    private String receiverName;
    @NotNull(message = "快递公司不能为空")
    private Long companyId;
    @NotNull(message = "取件地址不能为空")
    private String helpReceiveAddr;
    @NotNull(message = "配送方式不能为空")
    private int distributionType;
    @Min(value = 1, message = "填写的重量最小为1KG")
    private BigDecimal expressWeight;
    @NotNull(message = "取件码不能为空")
    private String helpReceiveCode;

    private String code;
    private String remark;
}
