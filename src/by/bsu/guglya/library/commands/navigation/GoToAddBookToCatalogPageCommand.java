package by.bsu.guglya.library.commands.navigation;

import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.managers.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class GoToAddBookToCatalogPageCommand implements Command{

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.BOOK_ADDITION_CATALOG_PATH_JSP);
        return page;
    }
}
