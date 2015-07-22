package by.bsu.guglya.library.commands.authorization;

import by.bsu.guglya.library.model.InputException;
import by.bsu.guglya.library.model.Validator;
import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.logic.LogicException;
import by.bsu.guglya.library.logic.RegistrationLogic;
import by.bsu.guglya.library.managers.ConfigurationManager;
import by.bsu.guglya.library.managers.MessageManager;
import by.bsu.guglya.library.utils.MD5Encryptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

public class RegistrationCommand implements Command {

    private static final String LOGIN_PARAM = "login";
    private static final String PASSWORD_PARAM = "password";
    private static final String REPEATED_PASSWORD_PARAM = "repeatPassword";
    private static final String LOCALE_PARAM = "locale";
    private final static String RESULT_MESSAGE_ATTR = "resultRegMessage";
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
        try {
            String message;
            if (Validator.validatorUserRegistrationInput(login, password, repeatedPassword)) {
                if (RegistrationLogic.checkLoginExist(login)) {
                    message = messageManager.getProperty(MessageManager.EXIST_LOGIN_MESSAGE);
                } else {
                    if (!password.equals(repeatedPassword)) {
                        message = messageManager.getProperty(MessageManager.WRONG_REPEATED_PASSWORD_MESSAGE);
                    } else {
                        MD5Encryptor encryptor = new MD5Encryptor();
                        if (RegistrationLogic.registrateClient(login, encryptor.Encrypt(password))) {
                            message = messageManager.getProperty(MessageManager.REGISTRATION_SUCCESS_MESSAGE);
                            request.setAttribute(RESULT_MESSAGE_ATTR, message);
                            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.LOGIN_PATH_JSP);
                            return page;
                        } else {
                            message = messageManager.getProperty(MessageManager.REGISTRATION_ERROR_MESSAGE);
                        }
                    }
                }
                request.setAttribute(RESULT_MESSAGE_ATTR, message);
            }
        } catch (InputException ex) {
            request.setAttribute(RESULT_MESSAGE_ATTR, ex.getMessage());
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.REGISTRATION_PATH_JSP);
            return page;
        }catch(NoSuchAlgorithmException ex){
            request.setAttribute(DATABASE_ERROR_MESSAGE_ATTR, ex.getMessage());
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PATH_JSP);
            return page;
        } catch (LogicException ex) {
            request.setAttribute(DATABASE_ERROR_MESSAGE_ATTR, ex.getMessage());
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PATH_JSP);
            return page;
        }
        page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.REGISTRATION_PATH_JSP);
        return page;
    }

}
