package com.school.util.wechat.button;

import java.util.List;

import lombok.Data;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 14/06/2018 12:40
 */
@Data
public class ComplexButton extends Button {
    private Button[] sub_button;
}
