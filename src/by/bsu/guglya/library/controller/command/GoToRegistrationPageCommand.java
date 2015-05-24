package by.bsu.guglya.library.controller.command;

import by.bsu.guglya.library.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class GoToRegistrationPageCommand implements Command{

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.REGISTRATION_PATH_JSP);
        return page;
    }
}
