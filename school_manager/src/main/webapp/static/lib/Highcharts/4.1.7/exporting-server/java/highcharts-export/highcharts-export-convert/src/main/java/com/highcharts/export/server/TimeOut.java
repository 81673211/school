package com.highcharts.export.server;

import org.apache.log4j.Logger;

import java.util.TimerTask;


public class TimeOut extends TimerTask {
    private final Server server;
    protected static Logger logger = Logger.getLogger("utils");

    public TimeOut(Server server) {
        this.server = server;
    }

    @Override
    public void run(){
        logger.debug("Timed out while downloading.");
        server.setState(ServerState.TIMEDOUT);
    }
};
