package com.school.domain.entity.express;

import java.util.Date;

import lombok.Data;

/**
 *
 * <b>Description:快件pojo.</b><br>
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:Express</b>
 * <br><b>Date:</b> 07/06/2018 13:40
 */
@Data
public class Express {

    /**
     * 自然主键ID.
     */
    private Long id;

    /**
     * 快递单号.
     */
    private String code;

    /**
     * 寄件人电话.
     */
    private String senderPhone;

    /**
     * 寄件人姓名.
     */
    private String senderName;

    /**
     * 寄件人地址.
     */
    private String senderAddress;

    /**
     * 寄件人省份ID.
     */
    private Long senderProvinceId;

    /**
     * 寄件人城市ID.
     */
    private Long senderCityId;

    /**
     * 寄件人区县ID.
     */
    private Long senderDistrictId;

    /**
     * 收件人电话.
     */
    private String receiverPhone;

    /**
     * 收件人姓名.
     */
    private String receiverName;

    /**
     * 收件人地址.
     */
    private String receiverAddress;

    /**
     * 收件人省份ID.
     */
    private Long receiverProvinceId;

    /**
     * 收件人城市ID.
     */
    private Long receiverCityId;

    /**
     * 收件人区县ID.
     */
    private Long receiverDistrictId;

    /**
     * 快递公司ID.
     */
    private Long companyId;

    /**
     * 快递公司名称.
     */
    private String companyName;

    /**
     * 快递公司编号.
     */
    private String companyCode;

    /**
     * 快件类型，参照:com.school.common.ExpressTypeEnum  .
     */
    private String type;

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
