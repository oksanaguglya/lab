package by.bsu.guglya.library.commands.authorization;

import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.managers.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
/**
 * This class implements a pattern command
 * This class logout users
 * @author Oksana
 */
public class LogoutCommand implements Command {
    /**
     * This method invalidate user session
     * @param request a httpServletRequest
     */
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.INDEX_PATH_JSP);
        return page;
    }

}
