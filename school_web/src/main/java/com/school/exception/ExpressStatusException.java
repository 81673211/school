package com.school.exception;

/**
 * @author jame
 */
public class ExpressStatusException extends RuntimeException{

    public ExpressStatusException(Throwable cause) {
        super(cause);
    }

    public ExpressStatusException(String errorMsg) {
        super(errorMsg);
    }

    public ExpressStatusException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }
}
