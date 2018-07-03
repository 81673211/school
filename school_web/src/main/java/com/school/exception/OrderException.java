package com.school.exception;

/**
 * @author jame
 */
public class OrderException extends Exception{

    public OrderException(Throwable cause) {
        super(cause);
    }

    public OrderException(String errorMsg) {
        super(errorMsg);
    }

    public OrderException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }
}
