package com.school.util.wechat.button;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 14/06/2018 12:39
 */
@Data
@EqualsAndHashCode
public class CommonButton extends Button {

    private String type;
    private String key;
    private String url;
}
