package by.bsu.guglya.library.controller.command;

import by.bsu.guglya.library.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class GoToLoginPageCommand implements Command{

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.LOGIN_PATH_JSP);
        return page;
    }
}
