package com.diveplane.interview;

/**
 * Just a simple Logger implementation to debug through the application,
 * as well as print valuable info.
 */
public class Logger {
    private final boolean debug;

    public Logger() {
        // by default, set the debug to false;
        this.debug = false;
    }

    public Logger(Boolean debug) {
        this.debug = debug;
    }

    public void debug(String msg) {
        if (debug)
            System.out.println("DEBUG: " + msg);
    }

    public void info(String msg) {
        System.out.println("INFO: " + msg);
    }

    public void warn(String msg) {
        System.out.println("WARN: " + msg);
    }
}
