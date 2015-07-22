package by.bsu.guglya.library.commands.navigation;

import by.bsu.guglya.library.model.beans.User;
import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.managers.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class GoToHomePageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.HOME_PATH_JSP);
        return page;
    }
}
