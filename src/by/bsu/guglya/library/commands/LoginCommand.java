package by.bsu.guglya.library.commands;

import by.bsu.guglya.library.beans.User;
import by.bsu.guglya.library.managers.ConfigurationManager;
import by.bsu.guglya.library.managers.MessageManager;
import by.bsu.guglya.library.logic.AuthenticationFacade;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);
    private final static String LOGIN_PARAM = "login";
    private final static String PASSWORD_PARAM = "password";
    private final static String LOCALE_PARAM = "locale";
    private final static String USER_ATTR = "user";
    private static final String LOCALE_ATTR = "locale";
    private static final String NO_USER_MESSAGE_ATTR = "noUserMessage";

    public LoginCommand() {
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String login = request.getParameter(LOGIN_PARAM);
        String password = request.getParameter(PASSWORD_PARAM);
        HttpSession session = request.getSession(true);
        String locale = (String)session.getAttribute(LOCALE_ATTR);
        if (AuthenticationFacade.checkLogin(login, password)) {
            User user = AuthenticationFacade.returnUser(login, password);
            session.setAttribute(USER_ATTR, user);
            switch (user.getType()) {
                case ADMINISTRATOR:
                    page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ADMINISTRATOR_PATH_JSP);
                    return page;
                case READER:
                    page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.READER_PATH_JSP);
                    return page;
            }
        }
        MessageManager messageManager = new MessageManager(locale);
        String message = messageManager.getProperty(MessageManager.LOGIN_ERROR_MESSAGE);
        if (!(login.equals("") && password.equals(""))){
            request.setAttribute(NO_USER_MESSAGE_ATTR, message);
        }
        request.setAttribute(LOCALE_PARAM, locale);
        page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.LOGIN_PATH_JSP);
        return page;
    }
}