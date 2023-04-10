/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.view.BookingDTO;
import ict.db.AccountDAO;
import ict.db.BookingDAO;
import ict.db.GuestDAO;
import ict.db.TimeslotDAO;
import ict.db.UserDAO;
import ict.db.VenueDAO;
import ict.db.VenueTimeslotDAO;
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
        if ("login".equalsIgnoreCase(action)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String role = accountDB.isVaildAccount(username, password);
            if (role != null) {
                HttpSession session = req.getSession();
                session.setAttribute("role", role);
                resp.sendRedirect("index.jsp");
            } else {
                RequestDispatcher rd;
                req.setAttribute("error", "Username or password incorrect!");
                rd = getServletContext().getRequestDispatcher("/login.jsp");
                rd.forward(req, resp);
            }
        } else if ("register".equalsIgnoreCase(action)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            boolean isSuccess = false;
            isSuccess = accountDB.addRecord(username, password, 1);
            if (isSuccess) {
                int accountId = accountDB.queryRecordByUsername(username).getId();
                isSuccess = userDB.addRecord(accountId, firstName, lastName, email, phone);
                if (isSuccess) {
                    resp.sendRedirect("registerResult.jsp");
                } else {
                    accountDB.delRecord(accountId);
                    RequestDispatcher rd;
                    req.setAttribute("error", "User information invalid.\nCreate account unsuccessfully!");
                    rd = getServletContext().getRequestDispatcher("/register.jsp");
                    rd.forward(req, resp);
                }
            } else {
                RequestDispatcher rd;
                req.setAttribute("error", "Username had existed!");
                rd = getServletContext().getRequestDispatcher("/register.jsp");
                rd.forward(req, resp);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookingId = req.getParameter("bookingId");
        if (bookingId != null) {
            int bid = Integer.parseInt(bookingId);
            BookingDTO b = bookingDB.queryRecordToDTOByBookingId(bid);
            if (b.getBooking().getId() == bid) {
                RequestDispatcher rd;
                req.setAttribute("BookingDTO", b);
                rd = getServletContext().getRequestDispatcher("/viewBooking.jsp");
                rd.forward(req, resp);
            }
        } else {
            ArrayList<BookingDTO> bdtos = bookingDB.queryRecordToDTO();
            RequestDispatcher rd;
            req.setAttribute("BookingDTOs", bdtos);
            rd = getServletContext().getRequestDispatcher("/bookingManagement.jsp");
            rd.forward(req, resp);
        }
    }

    @Override
    public void init() throws ServletException {
        System.out.println("ict.servlet.LoginController.init()");
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        accountDB = new AccountDAO(dbUrl, dbUser, dbPassword);
        userDB = new UserDAO(dbUrl, dbUser, dbPassword);
        bookingDB = new BookingDAO(dbUrl, dbUser, dbPassword);
    }

}
