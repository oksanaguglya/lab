package by.bsu.guglya.library.commands.authorization;

import by.bsu.guglya.library.beans.User;
import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.logic.LogicException;
import by.bsu.guglya.library.managers.ConfigurationManager;
import by.bsu.guglya.library.managers.MessageManager;
import by.bsu.guglya.library.logic.AuthenticationLogic;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {

    private final static String LOGIN_PARAM = "login";
    private final static String PASSWORD_PARAM = "password";
    private final static String USER_ATTR = "user";
    private final static String LOCALE_ATTR = "locale";
    private static final String NO_USER_MESSAGE_ATTR = "noUserMessage";
    private static final String EMPTY_FIELD_MESSAGE_ATTR = "emptyFieldMessage";
    private static final String DATABASE_ERROR_MESSAGE_ATTR = "errorDatabaseMessage";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String login = request.getParameter(LOGIN_PARAM);
        String password = request.getParameter(PASSWORD_PARAM);
        HttpSession session = request.getSession(true);
        String locale = (String) session.getAttribute(LOCALE_ATTR);
        MessageManager messageManager = new MessageManager(locale);
        if (login.equals("") || password.equals("")) {
            request.setAttribute(EMPTY_FIELD_MESSAGE_ATTR, "Fill in all the fields!");
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.LOGIN_PATH_JSP);
            return page;
        }
        try {
            if (AuthenticationLogic.checkUserExist(login, password)) {
                User user = AuthenticationLogic.returnUser(login, password);
                session.setAttribute(USER_ATTR, user);
                page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.HOME_PATH_JSP);
                return page;
            }
        } catch (LogicException ex) {
            request.setAttribute(DATABASE_ERROR_MESSAGE_ATTR, ex.getMessage());
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PATH_JSP);
            return page;
        }
        String message = messageManager.getProperty(MessageManager.LOGIN_ERROR_MESSAGE);
        request.setAttribute(NO_USER_MESSAGE_ATTR, message);
        page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.LOGIN_PATH_JSP);
        return page;
    }
}