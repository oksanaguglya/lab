package by.bsu.guglya.library.controllers.listeners;

import by.bsu.guglya.library.database.ConnectionPool;
import by.bsu.guglya.library.managers.ConfigurationManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class LibraryContextListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(LibraryContextListener.class);
    private static final String nameLogParam = "log4j";

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

