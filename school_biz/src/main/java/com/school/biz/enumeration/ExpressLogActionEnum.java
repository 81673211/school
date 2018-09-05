package com.school.biz.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 2018/9/5 21:07
 */
@AllArgsConstructor
@Getter
public enum ExpressLogActionEnum {

    CREATE_HELP_RECEIVE_EXPRESS("创建帮我取件");

    private String msg;
}
