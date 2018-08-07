package com.school.util.wechat;

import java.util.List;

import lombok.Data;

/**
 *
 * <b>Description:.</b><br> 
 * @author <b>sil.zhou</b>
 * <br><b>ClassName:</b> 
 * <br><b>Date:</b> 07/08/2018 10:21
 */
@Data
public class Template {

    private String id;
    private String toUser;
    private String url;
    private String first;
    private List<String> keywords;
    private String remark;
    private String color;
}
