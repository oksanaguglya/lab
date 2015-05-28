package by.bsu.guglya.library.commands;

import by.bsu.guglya.library.managers.ConfigurationManager;
import by.bsu.guglya.library.managers.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RegistrationCommand implements Command{

    private static final String LOGIN_PARAM = "login";
    private static final String PASSWORD_PARAM = "password";
    private static final String REPEATED_PASSWORD_PARAM = "repeatPassword";
    private static final String LOCALE_PARAM = "locale";
    private static final String WRONG_REPEATED_PASSWORD_ATTR = "wrongRepPassword";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(LOCALE_PARAM);
        MessageManager messageManager = new MessageManager(locale);
        String login = request.getParameter(LOGIN_PARAM);
        String password = request.getParameter(PASSWORD_PARAM);
        String repeatedPassword = request.getParameter(REPEATED_PASSWORD_PARAM);
        if (!password.equals(repeatedPassword)) {
            String message = messageManager.getProperty(MessageManager.WRONG_REPEATED_PASSWORD_MESSAGE);
            request.setAttribute(WRONG_REPEATED_PASSWORD_ATTR, message);
            //page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.REGISTRATION_PATH_JSP);
        }
        page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.REGISTRATION_PATH_JSP);
        return page;
    }
}
