package by.bsu.guglya.library.controller.command;

import by.bsu.guglya.library.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command{

    @Override
    public String execute(HttpServletRequest request) {

        request.getSession().invalidate();
        String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.HOME_PATH_JSP);
        return page;
    }
}
