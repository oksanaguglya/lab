package by.bsu.guglya.library.commands;

import by.bsu.guglya.library.commands.authorization.LoginCommand;
import by.bsu.guglya.library.commands.authorization.LogoutCommand;
import by.bsu.guglya.library.commands.authorization.RegistrationCommand;
import by.bsu.guglya.library.commands.catalog.AddBooksToBasketCommand;
import by.bsu.guglya.library.commands.basket.BasketCommand;
import by.bsu.guglya.library.commands.basket.DelBookFromBasketCommand;
import by.bsu.guglya.library.commands.basket.MakeOrderCommand;
import by.bsu.guglya.library.commands.catalog.CatalogCommand;
import by.bsu.guglya.library.commands.navigation.GoToAboutUsPageCommand;
import by.bsu.guglya.library.commands.navigation.GoToHomeCommand;
import by.bsu.guglya.library.commands.navigation.GoToLoginPageCommand;
import by.bsu.guglya.library.commands.navigation.GoToRegistrationPageCommand;
import by.bsu.guglya.library.commands.order.OrderLoginAdminCommand;
import by.bsu.guglya.library.commands.order.OrderNewAdminCommand;
import by.bsu.guglya.library.commands.order.OrderReaderCommand;
import by.bsu.guglya.library.commands.util.ChangeLanguageCommand;

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
        commands.put("home", new GoToHomeCommand());
        commands.put("go_to_about_us_page", new GoToAboutUsPageCommand());
        commands.put("go_to_catalog_page", new CatalogCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("add_books_to_basket", new AddBooksToBasketCommand());
        commands.put("my_basket", new BasketCommand());
        commands.put("del_book_from_basket", new DelBookFromBasketCommand());
        commands.put("make_an_order", new MakeOrderCommand());
        commands.put("go_to_order_reader_page", new OrderReaderCommand());
        commands.put("go_to_new_order_admin_page", new OrderNewAdminCommand());
        commands.put("go_to_login_order_admin_page", new OrderLoginAdminCommand());
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
