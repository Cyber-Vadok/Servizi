package it.unisalento.pae.primawebapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "searchServlet", value = "/search-servlet")
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String q = req.getParameter("q");
        // ...
        String res = "risultato";
        req.setAttribute("lista", q);
        RequestDispatcher rd = req.getRequestDispatcher("results.jsp");
        rd.forward(req, resp);
    }
}
