package com.school.web.customer.request;

import java.io.Serializable;

import lombok.Data;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 28/06/2018 21:57
 */
@Data
public class CustomerProfileEditRequest implements Serializable {
    private static final long serialVersionUID = -7141696454602997831L;

    private String openId;

    private String phone;

    private String name;

    private String email;

    private String addr;

    private String code;
}
