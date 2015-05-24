package by.bsu.guglya.library.controller;

import by.bsu.guglya.library.controller.command.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class RequestHelper {

    private static ReentrantLock lock = new ReentrantLock();
    private static RequestHelper instance = null;
    private HashMap<String, Command> commands = new HashMap<String, Command>();

    private RequestHelper() {
        commands.put("change_language", new ChangeLanguageCommand());
        commands.put("login", new LoginCommand());
        commands.put("go_to_login_page", new GoToLoginPageCommand());
        commands.put("go_to_registration_page", new GoToRegistrationPageCommand());
        commands.put("log_out", new LogoutCommand());
        commands.put("home", new HomeCommand());
        commands.put("go_to_about_us_page", new GoToAboutUsPageCommand());
    }

    public Command getCommand(HttpServletRequest request) {
        String action = request.getParameter("command");
        Command command = commands.get(action);
        return command;
    }

    public static RequestHelper getInstance() {
        try {
            lock.lock();
            if (instance == null) {
                instance = new RequestHelper();
            }
            return instance;
        } finally {
            lock.unlock();
        }
    }
}
