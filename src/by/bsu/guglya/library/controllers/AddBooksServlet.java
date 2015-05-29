package by.bsu.guglya.library.controllers;

import by.bsu.guglya.library.commands.Command;
import by.bsu.guglya.library.commands.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddBooksServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("command") != null) {
            int t = 0;
        }
    }
}

