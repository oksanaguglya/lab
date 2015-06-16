package by.bsu.guglya.library.commands;

import by.bsu.guglya.library.beans.User;
import by.bsu.guglya.library.managers.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HomeCommand implements Command {

    private final static String USER_ATTR = "user";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(true);
        User user = (User)session.getAttribute(USER_ATTR);
        if(user != null){
            switch (user.getType()){
                case ADMINISTRATOR:
                    page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.HOME_PATH_JSP);
                    return page;
                case READER:
                    page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.HOME_PATH_JSP);
                    return page;
            }
        }
        page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.HOME_PATH_JSP);
        return page;
    }
}
