/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.User;
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
@WebServlet(name = "MemberBookingController", urlPatterns = {"/member/booking"})
public class MemberBookingController extends HttpServlet {

    private AccountDAO accountDB;
    private UserDAO userDB;
    private BookingDAO bookingDB;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("updateStatus".equalsIgnoreCase(action)) {
            int bookingId = Integer.parseInt(req.getParameter("bookingId"));
            System.err.println("bid:" + bookingId);
            int status = Integer.parseInt(req.getParameter("status"));
            System.err.println("status:" + bookingId);
            HttpSession session = req.getSession(true);
            if (bookingDB.updateStatus(bookingId, status)) {
                session.setAttribute("message", "Update booking " + bookingId + " successfully!");
            } else {
                session.setAttribute("message", "Update booking " + bookingId + " failed!");
            };
            resp.sendRedirect("searchBookings");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookingId = req.getParameter("bookingId");
        String searchKeys = req.getParameter("search");
        User user = (User) req.getSession().getAttribute("userInfo");
        String action = req.getParameter("action");

        if (user == null) {
            req.getSession().setAttribute("redirectFrom",  "/member/booking");
            req.getSession().setAttribute("unautherror", "You need to login first!");
            resp.sendRedirect(getServletContext().getContextPath() + "/login.jsp");
            return;
        }
        //  if user is not a member, redirect to searchBookings ( /bookings.jsp points to this servlet by default)
        if (! req.getSession().getAttribute("role").equals("Member")) {
            resp.sendRedirect(getServletContext().getContextPath() + "/searchBookings");
            return;
        }

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
            bdtos = bookingDB.queryRecordToDTOWithUserId( user.getId());

            if ("upcoming".equalsIgnoreCase(action) ) {
                bdtos = bookingDB.queryUpcomingFiveDayRecord(Integer.toString(user.getId()));
            }
        }
        RequestDispatcher rd;
        req.setAttribute("bookingDTOs", bdtos);
        req.setAttribute("upcomingBookings", bookingDB.queryUpcomingFiveDayRecord(Integer.toString(user.getId())));
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

    public ArrayList<BookingDTO> getRecendBooking(String userId) {
        // get upcoming booking ( 5 days from now )
        return bookingDB.queryUpcomingFiveDayRecord(userId);
    }

}
