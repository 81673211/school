package com.school.biz.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jame
 * @date 2018/7/31
 * @desc 配送方式枚举
 */
public enum HelpDistributionTypeEnum {

    DOOR("door", "送货上门"),
    BOX("box", "入柜");
    private String flag;
    private String message;

    HelpDistributionTypeEnum(String flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public static Map<Object, String> getAllTypeEnum() {
        Map<Object, String> resultMap = new HashMap<Object, String>();
        HelpDistributionTypeEnum[] helpDistributionTypeEnums = HelpDistributionTypeEnum.values();
        for (HelpDistributionTypeEnum helpDistributionTypeEnum : helpDistributionTypeEnums) {
            resultMap.put(helpDistributionTypeEnum.flag, helpDistributionTypeEnum.message);
        }
        return resultMap;
    }
}
