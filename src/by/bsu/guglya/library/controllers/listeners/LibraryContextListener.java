package by.bsu.guglya.library.controllers.listeners;

import by.bsu.guglya.library.database.ConnectionPool;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

/**
 * This class handle notification events about ServletContext lifecycle changes
 */
public class LibraryContextListener implements ServletContextListener {
    /**
     * This is logger which print some messages to log file
     */
    private static final Logger logger = Logger.getLogger(LibraryContextListener.class);
    private static final String nameLogParam = "log4j";

    /**
     * This method load file with settings for logger
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        String rootPath = servletContext.getRealPath("/");
        if(rootPath != null){
            String log4jFile = rootPath + servletContext.getInitParameter(nameLogParam);
            PropertyConfigurator.configureAndWatch(log4jFile);
        }
        logger.info("Application started!");
    }

    /**
     * This method destroy connection pool instance
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
       try{
           ConnectionPool.getInstance().closeConnections();
           logger.info("Application destroyed!");
        }catch(SQLException ex){
            logger.error(ex);
        }
    }
}

