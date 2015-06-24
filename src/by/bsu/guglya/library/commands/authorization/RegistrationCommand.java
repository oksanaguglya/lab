package by.bsu.guglya.library.commands.authorization;

import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.logic.LogicException;
import by.bsu.guglya.library.logic.RegistrationLogic;
import by.bsu.guglya.library.managers.ConfigurationManager;
import by.bsu.guglya.library.managers.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RegistrationCommand implements Command {

    private static final String LOGIN_PARAM = "login";
    private static final String PASSWORD_PARAM = "password";
    private static final String REPEATED_PASSWORD_PARAM = "repeatPassword";
    private static final String LOCALE_PARAM = "locale";
    private static final String WRONG_REPEATED_PASSWORD_ATTR = "wrongRepPassword";
    private final static String EXIST_LOGIN_MESSAGE_ATTR = "existLoginMessage";
    private final static String SUCCESS_MESSAGE_ATTR = "successRegMessage";
    private final static String ERROR_MESSAGE_ATTR = "errorRegMessage";
    private static final String EMPTY_FIELD_MESSAGE_ATTR = "emptyFieldMessage";
    private static final String DATABASE_ERROR_MESSAGE_ATTR = "errorDatabaseMessage";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession(true);
        String locale = (String) session.getAttribute(LOCALE_PARAM);
        MessageManager messageManager = new MessageManager(locale);
        String login = request.getParameter(LOGIN_PARAM);
        String password = request.getParameter(PASSWORD_PARAM);
        String repeatedPassword = request.getParameter(REPEATED_PASSWORD_PARAM);
        if (login.equals("") || password.equals("") || repeatedPassword.equals("")) {
            request.setAttribute(EMPTY_FIELD_MESSAGE_ATTR, "Fill in all the fields!");
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.REGISTRATION_PATH_JSP);
            return page;
        }
        try {
            if (RegistrationLogic.checkLoginExist(login)) {
                String message = messageManager.getProperty(MessageManager.EXIST_LOGIN_MESSAGE);
                request.setAttribute(EXIST_LOGIN_MESSAGE_ATTR, message);
                page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.REGISTRATION_PATH_JSP);
            } else {
                if (!password.equals(repeatedPassword)) {
                    String message = messageManager.getProperty(MessageManager.WRONG_REPEATED_PASSWORD_MESSAGE);
                    request.setAttribute(WRONG_REPEATED_PASSWORD_ATTR, message);
                    page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.REGISTRATION_PATH_JSP);
                } else {
                    if (RegistrationLogic.registrateClient(login, password)) {
                        String message = messageManager.getProperty(MessageManager.REGISTRATION_SUCCESS_MESSAGE);
                        request.setAttribute(SUCCESS_MESSAGE_ATTR, message);
                        page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.LOGIN_PATH_JSP);
                    } else {
                        String message = messageManager.getProperty(MessageManager.REGISTRATION_ERROR_MESSAGE);
                        request.setAttribute(ERROR_MESSAGE_ATTR, message);
                        page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.REGISTRATION_PATH_JSP);
                    }
                }
            }
        } catch (LogicException ex) {
            request.setAttribute(DATABASE_ERROR_MESSAGE_ATTR, ex.getMessage());
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PATH_JSP);
        }
        return page;
    }
}
