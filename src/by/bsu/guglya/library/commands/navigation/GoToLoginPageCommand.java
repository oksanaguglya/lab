package by.bsu.guglya.library.commands.navigation;

import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.managers.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class GoToLoginPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.LOGIN_PATH_JSP);
        return page;
    }

}
