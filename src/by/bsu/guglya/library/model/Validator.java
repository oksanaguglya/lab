package by.bsu.guglya.library.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final String PATTERN_LATIN_STRING_LENGTH20 = "\\w{4,20}$";
    private static final String PATTERN_STRING_LENGTH45 = "^[\\u0410-\\u044Fa-zA-Z0-9]{1,45}$";
    private static final String PATTERN_INT_LENGTH4 = "\\d{1,4}$";

    private static boolean checkWithRegex(String expression, String pattern) {
        Pattern p = Pattern.compile(pattern, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher m = p.matcher(expression);
        return m.matches();
    }

    public static boolean validatorUserLoginInput(String login, String password) throws InputException {
        boolean valid;
        if (login.length() == 0 || password.length() == 0) {
            throw new InputException("Fill all fields!");
        }
        valid = true;
        return valid;
    }


    public static boolean validatorUserRegistrationInput(String login, String password, String repeatedPassword) throws InputException {
        boolean valid;
        if (login.length() == 0 || password.length() == 0 || repeatedPassword.length() == 0) {
            throw new InputException("Fill all fields!");
        }
        if (!checkWithRegex(login, PATTERN_LATIN_STRING_LENGTH20)) {
            throw new InputException("Incorrect login format (input 8-20 latin symbols)!");
        }
        if (!checkWithRegex(password, PATTERN_LATIN_STRING_LENGTH20)) {
            throw new InputException("Incorrect password format (input 8-20 latin symbols)!");
        }
        if (!password.equals(repeatedPassword)) {
            throw new InputException("The repeated password is not equals to password!");
        }
        valid = true;
        return valid;
    }

    public static boolean validatorCatalogItemInput(String title, String author, String year, String quantity) throws InputException {
        boolean valid;
        if (title.length() == 0 || author.length() == 0 || year.length() == 0 || quantity.length() == 0) {
            throw new InputException("Fill all fields!");
        }
        /*if (!checkWithRegex(title, PATTERN_STRING_LENGTH45)) {
            throw new InputException("Incorrect title format (input 1-45 symbols)!");
        }
        if (!checkWithRegex(author, PATTERN_STRING_LENGTH45)) {
            throw new InputException("Incorrect author format (input 1-45 symbols)!");
        }*/
        if (!checkWithRegex(year, PATTERN_INT_LENGTH4)) {
            throw new InputException("Incorrect year format (input 1-4 numeric)!");
        }
        if (!checkWithRegex(quantity, PATTERN_INT_LENGTH4)) {
            throw new InputException("Incorrect quantity format (input 1-4 numeric)!");
        }
        valid = true;
        return valid;
    }

}
