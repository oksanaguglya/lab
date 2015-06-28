package by.bsu.guglya.library.controllers.listeners;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class LibrarySessionListener implements HttpSessionListener {

    public static final String PARAM_USER = "user";

    /**
     * This is logger which print some messages to log file
     */
    protected static Logger logger = Logger.getLogger(LibrarySessionListener.class);

    /**
     * This method adds a message in the log file on the user logs
     * @param se a HttpSessionEvent
     */
    public void sessionCreated(HttpSessionEvent se) {
        logger.info("hello");
    }

    /**
     * This method adds a message in the log file on the user logouts
     * @param se a HttpSessionEvent
     */
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("buy");
    }
}
