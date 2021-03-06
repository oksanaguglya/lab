package by.bsu.guglya.library.managers;

import java.util.ResourceBundle;
import java.util.concurrent.locks.ReentrantLock;

public class ConfigurationManager {

    private static ConfigurationManager instance;
    private ResourceBundle resourceBundle;
    private static ReentrantLock lock = new ReentrantLock();
    private final static String BUNDLE_NAME = "resources.config";
    public final static String LOGIN_PATH_JSP = "LOGIN_PATH_JSP";
    public final static String REGISTRATION_PATH_JSP = "REGISTRATION_PATH_JSP";
    public final static String HOME_PATH_JSP = "HOME_PATH_JSP";
    public final static String INDEX_PATH_JSP = "INDEX_PATH_JSP";
    public static final String DATABASE_URL = "DATABASE_URL";
    public final static String READER_PATH_JSP = "READER_PATH_JSP";
    public final static String ADMINISTRATOR_PATH_JSP = "ADMINISTRATOR_PATH_JSP";
    public final static String ABOUT_US_PATH_JSP = "ABOUT_US_PATH_JSP";
    public final static String CATALOG_PATH_JSP = "CATALOG_PATH_JSP";
    public final static String BASKET_PATH_JSP = "BASKET_PATH_JSP";
    public final static String ORDER_READER_PATH_JSP = "ORDER_READER_PATH_JSP";
    public final static String ORDER_NEW_ADMIN_PATH_JSP = "ORDER_NEW_ADMIN_PATH_JSP";
    public final static String ORDER_LOGIN_ADMIN_PATH_JSP = "ORDER_LOGIN_ADMIN_PATH_JSP";
    public final static String ERROR_PATH_JSP = "ERROR_PATH_JSP";
    public final static String BOOK_EDITOR_CATALOG_PATH_JSP = "BOOK_EDITOR_CATALOG_PATH_JSP";
    public final static String BOOK_ADDITION_CATALOG_PATH_JSP = "BOOK_ADDITION_CATALOG_PATH_JSP";

    public static ConfigurationManager getInstance() {
        try {
            lock.lock();
            if (instance == null) {
                instance = new ConfigurationManager();
                instance.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }

    public String getProperty(String key) {
        return (String) resourceBundle.getObject(key);
    }
}