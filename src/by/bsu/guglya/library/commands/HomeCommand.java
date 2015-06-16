package by.bsu.guglya.library.commands;

import by.bsu.guglya.library.beans.User;
import by.bsu.guglya.library.managers.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HomeCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.HOME_PATH_JSP);
        return page;
    }
}
