package by.bsu.guglya.library.commands;

import by.bsu.guglya.library.managers.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class GoToRegistrationPageCommand implements Command{

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.REGISTRATION_PATH_JSP);
        return page;
    }
}
