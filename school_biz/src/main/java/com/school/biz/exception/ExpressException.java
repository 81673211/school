package com.school.biz.exception;

/**
 * @author jame
 */
public class ExpressException extends Exception {


    public ExpressException(Throwable cause) {
        super(cause);
    }

    public ExpressException(String errorMsg) {
        super(errorMsg);
    }

    public ExpressException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }
}
