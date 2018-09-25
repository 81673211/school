package com.school.biz.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jame
 * @date 2018/7/31
 * @desc 配送方式枚举
 */
public enum ReceiveExpressDistributionTypeEnum {

    SELF(0, "自提"),
    DISTRIBUTION_BOX(1, "入柜"),
    DISTRIBUTION_DOOR(2, "送货上门");
    private int flag;
    private String message;

    ReceiveExpressDistributionTypeEnum(int flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
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
        ReceiveExpressDistributionTypeEnum[] distributionTypeEnums = ReceiveExpressDistributionTypeEnum.values();
        for (ReceiveExpressDistributionTypeEnum distributionTypeEnum : distributionTypeEnums) {
            resultMap.put(distributionTypeEnum.flag, distributionTypeEnum.message);
        }
        return resultMap;
    }
}
