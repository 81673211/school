package com.school.exception;

/**
 * @author jame
 * @date 2018/7/31
 * @desc description
 */
public class CalcCostException extends Exception {

    public CalcCostException(Throwable cause) {
        super(cause);
    }

    public CalcCostException(String errorMsg) {
        super(errorMsg);
    }

    public CalcCostException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }
}
