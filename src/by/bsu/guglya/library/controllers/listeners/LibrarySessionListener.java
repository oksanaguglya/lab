package by.bsu.guglya.library.controllers.listeners;

import by.bsu.guglya.library.model.beans.User;
import com.sun.deploy.net.HttpRequest;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class LibrarySessionListener implements HttpSessionListener {

    private final static String USER_ATTR = "user";

    /**
     * This is logger which print some messages to log file
     */
    protected static Logger logger = Logger.getLogger(LibrarySessionListener.class);

    /**
     * This method adds a message in the log file on the user logs
     * @param se a HttpSessionEvent
     */
    public void sessionCreated(HttpSessionEvent se) {
         logger.info("Session created!");
    }

    /**
     * This method adds a message in the log file on the user logouts
     * @param se a HttpSessionEvent
     */
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        User user = (User)session.getAttribute(USER_ATTR);
        if (user != null) {
            logger.info("Session destroyed for user: " + user.getLogin());
        } else {
            logger.info("Session destroyed!");
        }
         /*long now = new java.util.Date().getTime();
            boolean timeout = (now - session.getLastAccessedTime()) >= ((long)session.getMaxInactiveInterval() * 1000L);*/
    }

}
