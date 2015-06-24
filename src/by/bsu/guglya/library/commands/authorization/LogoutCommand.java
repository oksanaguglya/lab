package by.bsu.guglya.library.commands.authorization;

import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.managers.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.INDEX_PATH_JSP);
        return page;
    }
}
