package by.bsu.guglya.library.controllers.listeners;

import by.bsu.guglya.library.database.ConnectionPool;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LibraryContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String log4jFile = "/WEB-INF/classes/" +servletContext.getInitParameter("log4j");
        //String log4jFile = servletContext.getRealPath("/") + "/WEB-INF/classes/" + servletContext.getInitParameter("log4j");
        PropertyConfigurator.configureAndWatch(log4jFile);
        Logger log = Logger.getLogger(LibraryContextListener.class);
        log.info("Application is started!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().closeConnections();
    }

}

