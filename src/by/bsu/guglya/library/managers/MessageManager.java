package by.bsu.guglya.library.managers;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {

    private ResourceBundle resourceBundle;
    private static final String BUNDLE_NAME = "resources.messages";
    public static final String LOGIN_ERROR_MESSAGE = "LOGIN_ERROR_MESSAGE";
    public static final String WRONG_REPEATED_PASSWORD_MESSAGE = "WRONG_REPEATED_PASSWORD_MESSAGE";
    public static final String EXIST_LOGIN_MESSAGE = "EXIST_LOGIN_MESSAGE";
    public static final String REGISTRATION_SUCCESS_MESSAGE = "REGISTRATION_SUCCESS_MESSAGE";
    public static final String REGISTRATION_ERROR_MESSAGE = "REGISTRATION_ERROR_MESSAGE";
    public static final String ORDER_SUCCESS_MESSAGE = "ORDER_SUCCESS_MESSAGE";
    public static final String ORDER_NO_CHECKS_MESSAGE = "ORDER_NO_CHECKS_MESSAGE";
    public static final String DEL_BOOK_FROM_BASKET_MESSAGE = "DEL_BOOK_FROM_BASKET_MESSAGE";
    public static final String EMPTY_BASKET_MESSAGE = "EMPTY_BASKET_MESSAGE";
    public static final String EMPTY_SEARCH_RESULT_MESSAGE = "EMPTY_SEARCH_RESULT_MESSAGE";
    public static final String ORDER_MADE_SUCCESS_MESSAGE = "ORDER_MADE_SUCCESS_MESSAGE";
    public static final String ORDER_MADE_NO_SUCCESS_MESSAGE = "ORDER_MADE_NO_SUCCESS_MESSAGE";
    public static final String EMPTY_SEARCH_ORDER_MESSAGE = "EMPTY_SEARCH_ORDER_MESSAGE";
    public static final String EMPTY_SEARCH_NEW_ORDER_MESSAGE = "EMPTY_SEARCH_NEW_ORDER_MESSAGE";
    public static final String EMPTY_SEARCH_LOGIN_ORDER_MESSAGE = "EMPTY_SEARCH_LOGIN_ORDER_MESSAGE";
    public static final String DEL_BOOK_FROM_CATALOG_MESSAGE = "DEL_BOOK_FROM_CATALOG_MESSAGE";
    public static final String NOT_DEL_BOOK_FROM_CATALOG_MESSAGE = "NOT_DEL_BOOK_FROM_CATALOG_MESSAGE";
    public static final String ORDER_PROCESS_APPROVED_MESSAGE = "ORDER_PROCESS_APPROVED_MESSAGE";
    public static final String ORDER_PROCESS_DENIED_MESSAGE = "ORDER_PROCESS_DENIED_MESSAGE";
    public static final String ORDER_PROCESS_NO_SUCCESS_MESSAGE = "ORDER_PROCESS_NO_SUCCESS_MESSAGE";
    public static final String ORDER_PROCESS_NOT_ENOUGH_MESSAGE = "ORDER_PROCESS_NO_ENOUGH_MESSAGE";
    public static final String DEL_USER_ORDER_MESSAGE = "DEL_USER_ORDER_MESSAGE";
    public static final String RETURN_USER_ORDER_MESSAGE = "RETURN_USER_ORDER_MESSAGE";
    public static final String ADD_BOOK_TO_CATALOG_SUCCESS_MESSAGE = "ADD_BOOK_TO_CATALOG_SUCCESS_MESSAGE";
    public static final String BOOK_EXIST_IN_CATALOG_MESSAGE = "BOOK_EXIST_IN_CATALOG_MESSAGE";
    public static final String ADD_NEW_BOOK_TO_CATALOG_SUCCESS_MESSAGE = "ADD_NEW_BOOK_TO_CATALOG_SUCCESS_MESSAGE";
    public static final String ADD_BOOK_TO_CATALOG_UNSUCCESS_MESSAGE = "ADD_BOOK_TO_CATALOG_UNSUCCESS_MESSAGE";
    public static final String EDIT_BOOK_IN_CATALOG_SUCCESS_MESSAGE = "EDIT_BOOK_IN_CATALOG_SUCCESS_MESSAGE";
    public static final String EDIT_BOOK_IN_CATALOG_UNSUCCESS_MESSAGE = "EDIT_BOOK_IN_CATALOG_UNSUCCESS_MESSAGE";
    public static final String EMPTY_FIELD_VALID_MESSAGE = "EMPTY_FIELD_VALID_MESSAGE";
    public static final String INCORRECT_LOGIN_VALID_MESSAGE = "INCORRECT_LOGIN_VALID_MESSAGE";
    public static final String INCORRECT_PASSWORD_MESSAGE = "INCORRECT_PASSWORD_MESSAGE";
    public static final String INCORRECT_REP_PASSWORD_VALID_MESSAGE = "INCORRECT_REP_PASSWORD_VALID_MESSAGE";
    public static final String INCORRECT_TITLE_VALID_MESSAGE = "INCORRECT_TITLE_VALID_MESSAGE";
    public static final String INCORRECT_AUTHOR_VALID_MESSAGE = "INCORRECT_AUTHOR_VALID_MESSAGE";
    public static final String INCORRECT_YEAR_VALID_MESSAGE = "INCORRECT_YEAR_VALID_MESSAGE";
    public static final String INCORRECT_QUANTITY_VALID_MESSAGE = "INCORRECT_QUANTITY_VALID_MESSAGE";
    public static final String DEFAULT_LANGUAGE = "ru";
    public static final String DEFAULT_COUNTRY = "RU";

    public MessageManager(String locale) {
       /* if(locale == null){
            this.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale(DEFAULT_LANGUAGE, DEFAULT_COUNTRY));
        }else{*/
            String language = locale.substring(0, 2);
            String country = locale.substring(3);
            this.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale(language, country));
        //}
    }

    public String getProperty(String key) {
        return (String) resourceBundle.getObject(key);
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

}