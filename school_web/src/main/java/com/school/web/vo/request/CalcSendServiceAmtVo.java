package com.school.web.vo.request;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * @author jame
 * @date 2018/9/2
 * @desc description
 */
@Validated
@Data
public class CalcSendServiceAmtVo {

    @Min(value = 1, message = "填写的重量最小为1KG")
    private BigDecimal expressWeight;

}
