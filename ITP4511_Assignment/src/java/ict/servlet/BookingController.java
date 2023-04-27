/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.Guest;
import ict.bean.GuestList;
import ict.bean.User;
import ict.bean.VenueTimeslot;
import ict.bean.view.BookingDTO;
import ict.db.AccountDAO;
import ict.db.BookingDAO;
import ict.db.GuestDAO;
import ict.db.GuestListDAO;
import ict.db.GuestListGuestDAO;
import ict.db.UserDAO;
import ict.db.VenueTimeslotDAO;
import ict.util.CheckRole;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
@WebServlet(name = "BookingController", urlPatterns = {"/searchBookings", "/updateBooking", "/viewBooking", "/addBooking"})
public class BookingController extends HttpServlet {

    private AccountDAO accountDB;
    private UserDAO userDB;
    private BookingDAO bookingDB;
    private VenueTimeslotDAO vtsDB;
    private GuestListDAO guestListDB;
    private GuestListGuestDAO guestListGuestDB;
    private GuestDAO guestDB;

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

            String userRole = (String) req.getSession().getAttribute("role");
            if (!userRole.equals("Member")) {
                resp.sendRedirect("searchBookings");
            } else {
                resp.sendRedirect(getServletContext().getContextPath() + "/member/booking");
            }
        } else if ("add".equalsIgnoreCase(action)) {
            HttpSession session = req.getSession();
            HashMap<String, ArrayList<Integer>> bookingVenus = (HashMap<String, ArrayList<Integer>>) session.getAttribute("bookingVenues");
            //add booking
            //get user id from session
            User user = (User) session.getAttribute("userInfo");
            int userId = user.getId();
            double total = Double.parseDouble(req.getParameter("total"));
            //add booking record and get booking id
            int bookingId = bookingDB.addRecord(userId, total, 1);
            if (bookingId != 0) {
                System.err.println(bookingId);
                //check timeslot vaildable
                boolean canBook = true;
                for (Map.Entry<String, ArrayList<Integer>> vs : bookingVenus.entrySet()) {
                    String venueId = vs.getKey();
                    ArrayList<Integer> vtsIds = vs.getValue();
                    for (int vtsId : vtsIds) {
                        VenueTimeslot vts = vtsDB.queryRocordById(vtsId);
                        //check venue whether is booked
                        if (vts.getBookingId() != 0) {
                            canBook = false;
                        }
                    }
                };
                if (canBook == true) {

                    // get the guest list from the session
                    GuestList guestList = (GuestList) session.getAttribute("guests");
                    //update record
                    for (Map.Entry<String, ArrayList<Integer>> vs : bookingVenus.entrySet()) {
                        String venueId = vs.getKey();
                        ArrayList<Integer> vtsIds = vs.getValue();
                        for (int vtsId : vtsIds) {
                            vtsDB.updateRecordBookingId(vtsId, bookingId);

                            //add guest list
                            int guestListId = guestListDB.addRecord( bookingId , Integer.parseInt(venueId) );
                            System.err.println("guestListId:" + guestListId);
                            for (Guest guest : guestList.getGuests()) {
                                // add the guest
                                int guestId = guestDB.addRecord(userId, guest.getName(), guest.getEmail());
                                System.err.println("guestId:" + guestId);
                                // add the guest to the guest list
                                guestListGuestDB.addRecord(guestListId, guestId);
                            }

                            session.removeAttribute("bookingVenues");
                            session.removeAttribute("bookingDates");
                            session.removeAttribute("guests");
                            session.setAttribute("message", "The venue is booked successfully! <br>The reservation order will be reserved for 24 hours! <br>Please pay in time, otherwise the reservation will be cancelled.");
                        }
                    }
                } else {
                    session.removeAttribute("bookingVenues");
                    session.removeAttribute("bookingDates");
                    bookingDB.delRecord(bookingId);//remove the created order which is booked fail
                    session.setAttribute("error", "The venue is booked fail! <br>Sorry, the selected time is already booked.");
                }
            } else {
                bookingDB.delRecord(bookingId);//remove the created order which is booked fail
                session.setAttribute("error", "Server Erro! <br>Sorry, the booking cannot be created, please try again!");
            }
            String userRole = (String) req.getSession().getAttribute("role");
            if (!userRole.equals("Member")) {
                resp.sendRedirect("searchBookings");
            } else {
                resp.sendRedirect(getServletContext().getContextPath() + "/member/booking");
            }
        } else {
            String userRole = (String) req.getSession().getAttribute("role");
            if (!userRole.equals("Member")) {
                resp.sendRedirect("searchBookings");
            } else {
                resp.sendRedirect(getServletContext().getContextPath() + "/member/booking");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isAuth = CheckRole.checkIfRoleIs(req.getSession(), new String[]{"Admin", "Staff", "Member"});

        String bookingId = req.getParameter("bookingId");

        if (!isAuth && bookingId != null) {
            CheckRole.redirect(req, resp, "/viewBooking?bookingId=" + bookingId, "You need to login first!");
            return;
        } else {
            if (!isAuth) {
                CheckRole.redirect(req, resp, "/member/booking", "You need to login first!");
                return;
            }
        }

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
        vtsDB = new VenueTimeslotDAO(dbUrl, dbUser, dbPassword);
        guestListDB = new GuestListDAO(dbUrl, dbUser, dbPassword);
        guestListGuestDB = new GuestListGuestDAO(dbUrl, dbUser, dbPassword);
        guestDB = new GuestDAO(dbUrl, dbUser, dbPassword);
        
    }

}
