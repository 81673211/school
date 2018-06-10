package com.school.util.core;


import org.apache.log4j.Logger;

/**
 * @author jame
 */
public class Log {
    public static Logger low = null;
    public static Logger error = null;

    static {
        try {
            low = Logger.getLogger("LOW");
            error = Logger.getLogger("ERROR");
        } catch (Exception e) {
            System.exit(-1);
        }
    }
}
