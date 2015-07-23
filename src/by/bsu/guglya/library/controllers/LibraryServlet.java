package by.bsu.guglya.library.controllers;

import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.commands.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class implements the pattern MVC
 * This is Servlet which handles requests
 */
public class LibraryServlet extends HttpServlet {

    /**
     * This method gets a instance of <code>Command</code> from <code>CommandFactory</code>
     * by request and execute this command. Then it gos to next jsp.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Command command = CommandFactory.getInstance().getCommand(request);
        String page = command.execute(request);
        request.getRequestDispatcher(page).forward(request, response);
    }

    /**
     * This method handles requests
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * This method handles requests
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * This method returns servlet info
     * @return
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
