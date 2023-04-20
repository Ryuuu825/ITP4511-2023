/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ict.util.DbUtil;

/**
 *
 * @author jyuba
 */
@WebServlet(name = "DbConnectionController", urlPatterns = {"/dbConnection"})
public class DbConnectionController extends HttpServlet {

    private DbUtil dbUtil;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean dbAvailable = dbUtil.testConnectionWithDB();
        String redirect = req.getParameter("redirect");
        redirect = getServletContext().getContextPath() + redirect;

        if (!dbAvailable) {
            // forward to error page , getServletContext().getContextPath() + "/http/500.jsp"
            req.setAttribute("error", "Database is not available");
            req.setAttribute("redirect", redirect);
            req.getRequestDispatcher("/http/500.jsp").forward(req, resp);
        }
    }

    @Override
    public void init() throws ServletException {

        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");

        dbUtil = new DbUtil(dbUrl, dbUser, dbPassword);

        
    }

}
