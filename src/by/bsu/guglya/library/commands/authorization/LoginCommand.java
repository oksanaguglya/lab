package by.bsu.guglya.library.commands.authorization;

import by.bsu.guglya.library.model.InputException;
import by.bsu.guglya.library.model.Validator;
import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.logic.LogicException;
import by.bsu.guglya.library.managers.ConfigurationManager;
import by.bsu.guglya.library.managers.MessageManager;
import by.bsu.guglya.library.logic.AuthenticationLogic;
import by.bsu.guglya.library.utils.MD5Encryptor;
import by.bsu.guglya.library.model.beans.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
/**
 * This class implements a pattern command
 * This class authorizes users in system
 * @author Oksana
 */
public class LoginCommand implements Command {

    private final static String LOGIN_PARAM = "login";
    private final static String PASSWORD_PARAM = "password";
    private final static String USER_ATTR = "user";
    private final static String LOCALE_ATTR = "locale";
    private final static String RESULT_MESSAGE_ATTR = "resultLoginMessage";
    private static final String DATABASE_ERROR_MESSAGE_ATTR = "errorDatabaseMessage";

    /**
     * This gets the login and password from request, check their correctness and find user in database
     * If such user exist this user will add to session and user will enter in system
     * @param request a httpServletRequest
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String login = request.getParameter(LOGIN_PARAM);
        String password = request.getParameter(PASSWORD_PARAM);
        HttpSession session = request.getSession(true);
        String locale = (String) session.getAttribute(LOCALE_ATTR);
        MessageManager messageManager = new MessageManager(locale);
        try {
            if(Validator.validatorUserLoginInput(login, password)){
                MD5Encryptor encryptor = new MD5Encryptor();
                String password_enc = encryptor.Encrypt(password);
                if (AuthenticationLogic.checkUserExist(login, password_enc)) {
                    User user = AuthenticationLogic.returnUser(login, password_enc);
                    session.setAttribute(USER_ATTR, user);
                    page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.HOME_PATH_JSP);
                    return page;
                }
            }
        } catch (InputException ex) {
            request.setAttribute(RESULT_MESSAGE_ATTR, messageManager.getProperty(ex.getMessage()));
            page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.LOGIN_PATH_JSP);
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
        String message = messageManager.getProperty(MessageManager.LOGIN_ERROR_MESSAGE);
        request.setAttribute(RESULT_MESSAGE_ATTR, message);
        page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.LOGIN_PATH_JSP);
        return page;
    }

}