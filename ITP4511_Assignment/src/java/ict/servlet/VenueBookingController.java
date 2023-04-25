/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.User;
import ict.bean.Venue;
import ict.bean.view.CalendarTimeslot;
import ict.db.UserDAO;
import ict.db.VenueDAO;
import ict.db.VenueTimeslotDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
@WebServlet(name = "VenueBookingController", urlPatterns = {"/findVenue", "/getCalendar", "/selectDate", "/handleBooking"})
public class VenueBookingController extends HttpServlet {

    private VenueDAO venueDAO;
    private UserDAO userDAO;
    private VenueTimeslotDAO vtsDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("addBookingVenue".equalsIgnoreCase(action)) {
            String venueId = (String) req.getParameter("venueId");
            String[] selectedTimeslotIds = req.getParameterValues("timeOption");
            int[] toIntTimeslotIds = new int[0];
            if (selectedTimeslotIds != null) {
                toIntTimeslotIds = new int[selectedTimeslotIds.length];
                for (int i = 0; i < selectedTimeslotIds.length; i++) {
                    System.out.println(selectedTimeslotIds[i]);
                    toIntTimeslotIds[i] = Integer.parseInt(selectedTimeslotIds[i]);
                }
            }

            HttpSession session = req.getSession();
            HashMap<String, int[]> bookingVenus = null;
            if (session.getAttribute("bookingVenues") != null) {
                bookingVenus = (HashMap<String, int[]>) session.getAttribute("bookingVenues");
            } else {
                bookingVenus = new HashMap<>();
            }
            System.out.println(bookingVenus);
            bookingVenus.put(venueId, toIntTimeslotIds);
            session.setAttribute("bookingVenues", bookingVenus);
//            ArrayList<Venue> venues = venueDAO.queryRecord();
//            req.setAttribute("venueList", venues);
//            RequestDispatcher rd;
//            rd = getServletContext().getRequestDispatcher("/findvenue.jsp");
//            rd.forward(req, resp);
            resp.sendRedirect("findVenue");
        } else if ("update".equalsIgnoreCase(action)) {
            resp.sendRedirect("findVenue");
        } else {
            resp.sendRedirect("findVenue");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("delete".equalsIgnoreCase(action)) {
            int vid = Integer.parseInt(req.getParameter("venueId"));
            HttpSession session = req.getSession(true);
            resp.sendRedirect("findVenue");
        } else if ("search".equalsIgnoreCase(action)) {
            ArrayList<Venue> venues = venueDAO.queryRecord();
            req.setAttribute("venueList", venues);
            String searchKeys = req.getParameter("search");
            if (searchKeys != null && !searchKeys.equals("")) {
                venues = venueDAO.queryRecordByName("");
            } else {
                venues = venueDAO.queryRecord();
            }
            RequestDispatcher rd;
            req.setAttribute("venueList", venues);
            ArrayList<User> staff = userDAO.queryRecordByRole(2);
            req.setAttribute("staff", staff);
            rd = getServletContext().getRequestDispatcher("/venues.jsp");
            rd.forward(req, resp);
        } else if ("calendar".equalsIgnoreCase(action)) {
            ArrayList<Venue> venues = venueDAO.queryRecord();
            req.setAttribute("venueList", venues);
            int vid = Integer.parseInt(req.getParameter("venueId"));
            req.setAttribute("selectedVenue", vid);
            RequestDispatcher rd;
            String[] selectedDate = req.getParameterValues("selectedDate");
            HttpSession session = req.getSession();
            session.setAttribute("selectedDate", selectedDate);
            ArrayList<ArrayList<CalendarTimeslot>> monthlyDateTimeslot = vtsDAO.queryMonthlyCalendarByVenueId(vid);
            System.out.print("sssss: "+monthlyDateTimeslot);
            req.setAttribute("monthlyDateTimeslot", monthlyDateTimeslot);
            System.err.println(monthlyDateTimeslot.size());
            rd = getServletContext().getRequestDispatcher("/findvenue.jsp");
            rd.forward(req, resp);
        } else {
            ArrayList<Venue> venues = venueDAO.queryRecord();
            req.setAttribute("venueList", venues);
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/findvenue.jsp");
            rd.forward(req, resp);
        }
    }

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        venueDAO = new VenueDAO(dbUrl, dbUser, dbPassword);
        userDAO = new UserDAO(dbUrl, dbUser, dbPassword);
        vtsDAO = new VenueTimeslotDAO(dbUrl, dbUser, dbPassword);
    }

}
