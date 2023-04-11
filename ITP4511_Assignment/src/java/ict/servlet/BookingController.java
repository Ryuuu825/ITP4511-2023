/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.view.BookingDTO;
import ict.db.AccountDAO;
import ict.db.BookingDAO;
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
@WebServlet(name = "BookingController", urlPatterns = {"/searchBookings", "/editBookingRecord", "/viewBooking"})
public class BookingController extends HttpServlet {

    private AccountDAO accountDB;
    private UserDAO userDB;
    private BookingDAO bookingDB;

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
        String bookingId = req.getParameter("bookingId");
        String searchKeys = req.getParameter("search");
        ArrayList<BookingDTO> bdtos = null;
        if (bookingId != null) {
            int bid = Integer.parseInt(bookingId);
            BookingDTO b = bookingDB.queryRecordToDTOByBookingId(bid);
            if (b.getBooking().getId() == bid) {
                RequestDispatcher rd;
                req.setAttribute("bookingDTO", b);
                rd = getServletContext().getRequestDispatcher("/viewBooking.jsp");
                rd.forward(req, resp);
                return;
            }
        } else if (searchKeys != null && !searchKeys.equals("")) {
            bdtos = bookingDB.queryRecordToDTOByKeyword(searchKeys);
        } else {
            bdtos = bookingDB.queryRecordToDTO();
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
        bookingDB = new BookingDAO(dbUrl, dbUser, dbPassword);
    }

}
