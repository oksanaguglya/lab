package by.bsu.guglya.library.commands;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class CommandFactory {

    private static ReentrantLock lock = new ReentrantLock();
    private static CommandFactory instance = null;
    private HashMap<String, Command> commands = new HashMap<String, Command>();

    private CommandFactory() {
        commands.put("change_language", new ChangeLanguageCommand());
        commands.put("login", new LoginCommand());
        commands.put("go_to_login_page", new GoToLoginPageCommand());
        commands.put("go_to_registration_page", new GoToRegistrationPageCommand());
        commands.put("log_out", new LogoutCommand());
        commands.put("home", new HomeCommand());
        commands.put("go_to_about_us_page", new GoToAboutUsPageCommand());
        commands.put("go_to_catalog_page", new GoToCatalogPageCommand());
    }

    public Command getCommand(HttpServletRequest request) {
        String action = request.getParameter("command");
        Command command = commands.get(action);
        return command;
    }

    public static CommandFactory getInstance() {
        try {
            lock.lock();
            if (instance == null) {
                instance = new CommandFactory();
            }
            return instance;
        } finally {
            lock.unlock();
        }
    }
}
