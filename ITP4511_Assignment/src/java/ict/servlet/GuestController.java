/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.view.BookingDTO;
import ict.db.AccountDAO;
import ict.db.BookingDAO;
import ict.db.GuestDAO;
import ict.db.UserDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jyuba
 */
@WebServlet(name = "BookingController", urlPatterns = {"/delGuest", "/editBookingRecord", "/viewBooking"})
public class GuestController extends HttpServlet {

    private AccountDAO accountDB;
    private UserDAO userDB;
    private GuestDAO guestDB;

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        if (accountDB.delRecord(id)) {
            resp.sendRedirect("index.jsp");
        } else {
            RequestDispatcher rd;
            req.setAttribute("error", "Delete account(s) unsuccessfully!");
            rd = getServletContext().getRequestDispatcher("/login.jsp");
            rd.forward(req, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String guestId = req.getParameter("guestId");
        String searchKeys = req.getParameter("search");
        ArrayList<BookingDTO> bdtos = null;
        if (guestId != null) {
            int bid = Integer.parseInt(guestId);
           
        } else if (searchKeys != null && !searchKeys.equals("")) {
        } else {
        }
        RequestDispatcher rd;
        req.setAttribute("bookingDTOs", bdtos);
        rd = getServletContext().getRequestDispatcher("/bookings.jsp");
        rd.forward(req, resp);
    }

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        accountDB = new AccountDAO(dbUrl, dbUser, dbPassword);
        userDB = new UserDAO(dbUrl, dbUser, dbPassword);
        guestDB = new GuestDAO(dbUrl, dbUser, dbPassword);
    }

}
