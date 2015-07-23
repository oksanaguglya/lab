package by.bsu.guglya.library.model;

import by.bsu.guglya.library.managers.MessageManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final String PATTERN_LATIN_STRING_LENGTH20 = "^([a-zA-Z0-9_-]{8,20})$";
    private static final String PATTERN_STRING_LENGTH45 = "^([a-zA-Z\\u0430-\\u044F\\u0410-\\u042F0\\s-9_-]{1,45})$";
    private static final String PATTERN_YEAR = "^[1-2][0-9]{3}$";
    private static final String PATTERN_DIGIT = "^[0-9]+$";

    private static boolean checkWithRegex(String expression, String pattern) {
        Pattern p = Pattern.compile(pattern, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher m = p.matcher(expression);
        return m.matches();
    }

    public static boolean validatorUserLoginInput(String login, String password) throws InputException {
        boolean valid;
        if (login.length() == 0 || password.length() == 0) {
            throw new InputException(MessageManager.EMPTY_FIELD_VALID_MESSAGE);
        }
        valid = true;
        return valid;
    }

    public static boolean validatorUserRegistrationInput(String login, String password, String repeatedPassword) throws InputException {
        boolean valid;
        if (login.length() == 0 || password.length() == 0 || repeatedPassword.length() == 0) {
            throw new InputException(MessageManager.EMPTY_FIELD_VALID_MESSAGE);
        }
        if (!checkWithRegex(login, PATTERN_LATIN_STRING_LENGTH20)) {
            throw new InputException(MessageManager.INCORRECT_LOGIN_VALID_MESSAGE);
        }
        if (!checkWithRegex(password, PATTERN_LATIN_STRING_LENGTH20)) {
            throw new InputException(MessageManager.INCORRECT_PASSWORD_MESSAGE);
        }
        if (!password.equals(repeatedPassword)) {
            throw new InputException(MessageManager.INCORRECT_REP_PASSWORD_VALID_MESSAGE);
        }
        valid = true;
        return valid;
    }

    public static boolean validatorCatalogItemInput(String title, String author, String year, String quantity) throws InputException {
        boolean valid;
        if (title.length() == 0 || author.length() == 0 || year.length() == 0 || quantity.length() == 0) {
            throw new InputException(MessageManager.EMPTY_FIELD_VALID_MESSAGE);
        }
        if (!checkWithRegex(title, PATTERN_STRING_LENGTH45)) {
            throw new InputException(MessageManager.INCORRECT_TITLE_VALID_MESSAGE);
        }
        if (!checkWithRegex(author, PATTERN_STRING_LENGTH45)) {
            throw new InputException(MessageManager.INCORRECT_AUTHOR_VALID_MESSAGE);
        }
        if (!checkWithRegex(year, PATTERN_YEAR)) {
            throw new InputException(MessageManager.INCORRECT_YEAR_VALID_MESSAGE);
        }
        if (!checkWithRegex(quantity, PATTERN_DIGIT)) {
            throw new InputException(MessageManager.INCORRECT_QUANTITY_VALID_MESSAGE);
        }
        valid = true;
        return valid;
    }

}
