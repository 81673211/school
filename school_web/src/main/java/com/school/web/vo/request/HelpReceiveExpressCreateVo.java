package com.school.web.vo.request;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
    @NotNull(message = "快递单号不为空")
    private String code;
    @NotNull(message = "配送方式不为空")
    private String distributionType;
    @Min(value = 1, message = "填写的重量最小为1KG")
    private BigDecimal expressWeight;
    //取件码，可不填
    private String helpReceiveCode;
    private String remark;
}
