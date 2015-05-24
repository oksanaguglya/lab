package by.bsu.guglya.library.controller.command;

import by.bsu.guglya.library.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class GoToCatalogPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.CATALOG_PATH_JSP);
        return page;
    }
}
