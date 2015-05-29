package by.bsu.guglya.library.controllers;

import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddBooksServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] selectedItems = request.getParameterValues("selectedItems[]");
        request.getSession().setAttribute("selectedItems", selectedItems);
        response.getWriter().write("{}");
    }
}

