package by.bsu.guglya.library.controllers.listeners;

import by.bsu.guglya.library.database.ConnectionPool;
import org.apache.log4j.xml.DOMConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LibraryContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        //String log4jFile = servletContext.getRealPath("/")+servletContext.getInitParameter("log4j");
        String log4jFile = servletContext.getInitParameter("log4j");
        DOMConfigurator.configure(log4jFile);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().closeConnections();
    }

}

