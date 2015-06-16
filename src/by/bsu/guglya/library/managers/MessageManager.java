package by.bsu.guglya.library.managers;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {

    private ResourceBundle resourceBundle;
    private static final String BUNDLE_NAME = "by.bsu.guglya.library.resources.messages";
    public static final String LOGIN_ERROR_MESSAGE = "LOGIN_ERROR_MESSAGE";
    public static final String WRONG_REPEATED_PASSWORD_MESSAGE = "WRONG_REPEATED_PASSWORD_MESSAGE";
    public static final String EXIST_LOGIN_MESSAGE = "EXIST_LOGIN_MESSAGE";
    public static final String REGISTRATION_SUCCESS_MESSAGE = "REGISTRATION_SUCCESS_MESSAGE";
    public static final String REGISTRATION_ERROR_MESSAGE = "REGISTRATION_ERROR_MESSAGE";
    public static final String ORDER_SUCCESS_MESSAGE = "ORDER_SUCCESS_MESSAGE";
    public static final String ORDER_NO_CHECKS_MESSAGE = "ORDER_NO_CHECKS_MESSAGE";
    public static final String DEL_BOOK_FROM_BASKET_MESSAGE = "DEL_BOOK_FROM_BASKET_MESSAGE";
    public static final String EMPTY_BASKET_MESSAGE = "EMPTY_BASKET_MESSAGE";

    public MessageManager(String locale) {
        String language = locale.substring(0, 2);
        String country = locale.substring(3);
        this.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale(language, country));
    }

    public String getProperty(String key) {
        return (String) resourceBundle.getObject(key);
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

}