package com.school.exception;

/**
 * @author jame
 */
public class RegionException extends Exception {


    public RegionException(Throwable cause) {
        super(cause);
    }

    public RegionException(String errorMsg) {
        super(errorMsg);
    }

    public RegionException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
    }
}
