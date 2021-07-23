package com.playernguyen.dbcollective;

import java.util.logging.Logger;

/**
 * Abstract class of dispatch, which contains verbose and logger class.
 */
public abstract class DispatchAbstract implements Dispatch {

    private boolean verbose = false;
    private Logger logger = Logger.getGlobal();

    /**
     * A constructor for a dispatch contains verbose and global . Verbose forcibly
     * create a message (log message) whenever triggers event
     * 
     * @param verbose turn on or off verbose mode
     * @param logger  a logger to log message
     */
    public DispatchAbstract(boolean verbose, Logger logger) {
        this.verbose = verbose;
        this.logger = logger;
    }

    /**
     * No args need, just add a new keyword and start to use
     */
    public DispatchAbstract() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Logger getLogger() {
        return logger;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVerbose(boolean b) {
        this.verbose = b;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVerbose() {
        return verbose;
    }

}