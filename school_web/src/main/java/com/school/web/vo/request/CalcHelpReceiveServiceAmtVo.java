package com.school.web.vo.request;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author jame
 * @date 2018/9/2
 * @desc description
 */
@Validated
@Data
public class CalcHelpReceiveServiceAmtVo {

    @Min(value = 1, message = "填写的重量最小为1KG")
    private BigDecimal expressWeight;

    @NotNull(message = "配送方式不为空")
    private int distributionType;
}
