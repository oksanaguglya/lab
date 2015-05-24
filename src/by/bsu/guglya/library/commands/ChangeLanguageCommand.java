package by.bsu.guglya.library.commands;

import by.bsu.guglya.library.managers.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLanguageCommand implements Command {

    private enum Locale{
        RU("ru_RU"),
        EN("en_EN");
        private String lang;
        Locale(String lang){
            this.lang = lang;
        }
        public String getLang(){
            return lang;
        }
    }

    private static final String LOCALE_ATTR = "locale";

    public ChangeLanguageCommand() {
    }

    @Override
    public String execute(HttpServletRequest request) {
        String locale = request.getParameter(LOCALE_ATTR);
        String lang = Locale.valueOf(locale).getLang();
        HttpSession session = request.getSession(true);
        session.setAttribute(LOCALE_ATTR, lang);
        String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.HOME_PATH_JSP);
        return page;
    }
}