package by.bsu.guglya.library.commands;

import by.bsu.guglya.library.managers.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command{

    @Override
    public String execute(HttpServletRequest request) {

        request.getSession().invalidate();
        String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.HOME_PATH_JSP);
        return page;
    }
}
