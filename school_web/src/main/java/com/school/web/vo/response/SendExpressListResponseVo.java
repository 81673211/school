package com.school.web.vo.response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 13/08/2018 11:26
 */
@Data
public class SendExpressListResponseVo {
    private Long id;
    private String code;
    private Long companyId;
    private String companyName;
    private Integer expressStatus;
    private String receiverPhone;    //收件人电话
    private String receiverName;//	收件人名字
    private String receiverAddr;//  收件人地址
    private Date endTime;
    private Date createdTime;
    private String receiverProvince;
    private String receiverCity;
    private String receiverDistrict;
    private BigDecimal transportPrice;
    private BigDecimal serviceAmt;
    private BigDecimal agio;
}
