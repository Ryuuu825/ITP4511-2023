/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.Booking;
import ict.bean.Guest;
import ict.bean.Timeslot;
import ict.bean.User;
import ict.bean.Venue;
import ict.bean.VenueTimeslot;
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
@WebServlet(name = "BookingController", urlPatterns = {"/searchBookings", "/editBookingRecord"})
public class BookingController extends HttpServlet {

    private AccountDAO accountDB;
    private UserDAO userDB;
    private VenueTimeslotDAO venueTimeslotDB;
    private BookingDAO bookingDB;
    private GuestDAO guestDB;
    private VenueDAO venueDB;
    private TimeslotDAO timeslotDB;

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
        ArrayList<BookingDTO> bdtos = new ArrayList<>();
        ArrayList<Booking> bookings = bookingDB.queryRecord();
        ArrayList<VenueTimeslot> vts = null;
        ArrayList<ArrayList<Guest>> gss = null;
        ArrayList<Venue> vs = null;
        ArrayList<Timeslot> tss = null;
        BookingDTO bdto = null;
        if (bookings.size() != 0) {
            for (Booking b : bookings) {
                bdto = new BookingDTO();
                bdto.setBooking(b);
                vts = venueTimeslotDB.queryRocordByBookingId(b.getId());

                if (vts.size() != 0) {
                    vs = new ArrayList<>();
                    tss = new ArrayList<>();
                    for (VenueTimeslot vt : vts) {
                        Venue v = venueDB.queryRecordById(vt.getVenueId());
                        vs.add(v);
                        Timeslot ts = timeslotDB.queryRecordById(vt.getTimeslotId());
                        tss.add(ts);
                        ArrayList<Guest> gs = guestDB.queryRecordByBooking(b.getId(), vt.getVenueId());
                        gss.add(gs);
                    }
                }

                bdto.setVenues(vs);
                bdto.setGuestLists(gss);
                bdto.setTimeslots(tss);
                User u = userDB.queryRecordById(b.getUserId());
                bdto.setMember(u.getFirstName() + " " + u.getLastName());
                bdtos.add(bdto);
            }
        }
        RequestDispatcher rd;
        req.setAttribute("BookingDTOs", bdtos);
        rd = getServletContext().getRequestDispatcher("/bookingManagement.jsp");
        rd.forward(req, resp);
    }

    @Override
    public void init() throws ServletException {
        System.out.println("ict.servlet.LoginController.init()");
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        accountDB = new AccountDAO(dbUrl, dbUser, dbPassword);
        userDB = new UserDAO(dbUrl, dbUser, dbPassword);
        venueTimeslotDB = new VenueTimeslotDAO(dbUrl, dbUser, dbPassword);
        bookingDB = new BookingDAO(dbUrl, dbUser, dbPassword);
        guestDB = new GuestDAO(dbUrl, dbUser, dbPassword);
        venueDB = new VenueDAO(dbUrl, dbUser, dbPassword);
        timeslotDB = new TimeslotDAO(dbUrl, dbUser, dbPassword);
    }

}
