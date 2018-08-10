package com.school.biz.enumeration;

/**
 * @author jame
 * @date 2018/7/31
 * @desc 配送方式枚举
 */
public enum DistributionTypeEnum {

    SELF(0, "自提"),
    DISTRIBUTION(1, "配送");
    private int flag;
    private String message;

    DistributionTypeEnum(int flag, String message) {
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
}
