package by.bsu.guglya.library.managers;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {

    private ResourceBundle resourceBundle;
    private static final String BUNDLE_NAME = "by.bsu.guglya.library.resources.messages";
    public static final String LOGIN_ERROR_MESSAGE = "LOGIN_ERROR_MESSAGE";
    public static final String WRONG_REPEATED_PASSWORD_MESSAGE = " WRONG_REPEATED_PASSWORD_MESSAGE";


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