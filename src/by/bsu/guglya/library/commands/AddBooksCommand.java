package by.bsu.guglya.library.commands;

import javax.servlet.http.HttpServletRequest;

public class AddBooksCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        if (request.getParameter("selectedItems") != null) {
            String selectedItems = request.getParameter("selectedItems");
        }

        return null;
    }
}
