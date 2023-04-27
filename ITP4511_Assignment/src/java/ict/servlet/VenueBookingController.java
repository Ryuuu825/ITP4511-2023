/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.Timeslot;
import ict.bean.User;
import ict.bean.Venue;
import ict.bean.VenueTimeslot;
import ict.bean.view.CalendarTimeslot;
import ict.db.TimeslotDAO;
import ict.db.UserDAO;
import ict.db.VenueDAO;
import ict.db.VenueTimeslotDAO;
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
@WebServlet(name = "VenueBookingController", urlPatterns = {"/findVenue", "/getCalendar", "/selectDate", "/handleBooking", "/getCart", "/delCartVenue"})
public class VenueBookingController extends HttpServlet {

    private VenueDAO venueDAO;
    private TimeslotDAO timeslotDAO;
    private UserDAO userDAO;
    private VenueTimeslotDAO vtsDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("addBookingVenue".equalsIgnoreCase(action)) {
            String venueId = (String) req.getParameter("venueId");
            String[] selectedTimeslotIds = req.getParameterValues("timeOption");
            ArrayList<String> selectedDate = new ArrayList<>();
            ArrayList<Integer> toIntTimeslotIds = new ArrayList<>();
            if (selectedTimeslotIds != null) {
                for (int i = 0; i < selectedTimeslotIds.length; i++) {
                    System.out.println(selectedTimeslotIds[i]);
                    int vtsId = Integer.parseInt(selectedTimeslotIds[i]);
                    toIntTimeslotIds.add(vtsId);
                    VenueTimeslot vts = vtsDAO.queryRocordById(vtsId);
                    System.err.println(vts.getDate().toString());
                    if (vts != null && selectedDate.contains(vts.getDate().toString())) {
                        continue;
                    } else {
                        selectedDate.add(vts.getDate().toString());
                    }
                }
            }
            System.err.println(selectedDate);
            HttpSession session = req.getSession();
            HashMap<String, ArrayList<Integer>> bookingVenus = null;
            if (session.getAttribute("bookingVenues") != null) {
                bookingVenus = (HashMap<String, ArrayList<Integer>>) session.getAttribute("bookingVenues");
            } else {
                bookingVenus = new HashMap<>();
            }

            HashMap<String, ArrayList<String>> bookingDates = null;
            if (session.getAttribute("bookingDates") != null) {
                bookingDates = (HashMap<String, ArrayList<String>>) session.getAttribute("bookingDates");
            } else {
                bookingDates = new HashMap<>();
            }
            bookingDates.put(venueId, selectedDate);
            session.setAttribute("bookingDates", bookingDates);
            bookingVenus.put(venueId, toIntTimeslotIds);
            session.setAttribute("bookingVenues", bookingVenus);
            resp.sendRedirect("findVenue");
        } else if ("delCartVenue".equalsIgnoreCase(action)) {
            HttpSession session = req.getSession();
            String venueId = (String) req.getParameter("venueId");
            HashMap<String, ArrayList<Integer>> bookingVenus = (HashMap<String, ArrayList<Integer>>) session.getAttribute("bookingVenues");
            HashMap<String, ArrayList<String>> bookingDates = (HashMap<String, ArrayList<String>>) session.getAttribute("bookingDates");
            bookingVenus.remove(venueId);
            bookingDates.remove(venueId);
            session.setAttribute("bookingDates", bookingDates);
            session.setAttribute("bookingVenues", bookingVenus);
            resp.sendRedirect("findVenue");
        } else if ("delVenueTimeslot".equalsIgnoreCase(action)) {
            HttpSession session = req.getSession();
            String venueId = (String) req.getParameter("venueId");
            String vtsId = (String) req.getParameter("vtsId");
            HashMap<String, ArrayList<Integer>> bookingVenus = (HashMap<String, ArrayList<Integer>>) session.getAttribute("bookingVenues");
            bookingVenus.get(venueId).remove(vtsId);
            session.setAttribute("bookingVenues", bookingVenus);
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
        } else if ("cart".equalsIgnoreCase(action)) {
            HashMap<String, ArrayList<Integer>> bookingVenues = (HashMap<String, ArrayList<Integer>>) req.getSession().getAttribute("bookingVenues");
            HashMap<String, Venue> cartVenues = new HashMap<>();
            HashMap<String, HashMap<String, ArrayList<Timeslot>>> venueDateTimes = new HashMap<>();
            if (bookingVenues == null || bookingVenues.isEmpty()) {
                resp.sendRedirect("findVenue");
                return;
            }
            for (Map.Entry<String, ArrayList<Integer>> entry : bookingVenues.entrySet()) {
                String vid = entry.getKey();
                int venueId = Integer.parseInt(vid);
                ArrayList<Integer> vtsIds = entry.getValue();
                Venue v = venueDAO.queryRecordById(venueId);
                cartVenues.put(vid, v);
                HashMap<String, ArrayList<Timeslot>> dateTimes = new HashMap<>();
                for (int vtsId : vtsIds) {
                    VenueTimeslot vts = vtsDAO.queryRocordById(vtsId);
                    ArrayList<Timeslot> tss = new ArrayList<>();
                    for (int t : vtsIds) {
                        VenueTimeslot vt = vtsDAO.queryRocordById(t);
                        Timeslot ts = timeslotDAO.queryRecordById(vt.getTimeslotId());
                        tss.add(ts);
                    }
                    dateTimes.put(vts.getDate().toString(), tss);
                }
                venueDateTimes.put("vid"+vid, dateTimes);
                System.err.println(venueDateTimes.get("vid"+vid));
            }
            req.setAttribute("cartVenues", cartVenues);
            req.setAttribute("venueDateTimes", venueDateTimes);
            RequestDispatcher rd;
            rd = this.getServletContext().getRequestDispatcher("/cart.jsp");
            rd.forward(req, resp);
        } else if ("calendar".equalsIgnoreCase(action)) {
            ArrayList<Venue> venues = venueDAO.queryRecord();
            req.setAttribute("venueList", venues);
            User u = (User) req.getSession().getAttribute("userInfo");
            if (u == null) {
                resp.sendRedirect("login.jsp");
                return;
            }
            String venueId = req.getParameter("venueId");
            int vid = Integer.parseInt(venueId);
            req.setAttribute("selectedVenue", venueId);
            RequestDispatcher rd;
            ArrayList<ArrayList<CalendarTimeslot>> monthlyDateTimeslot = vtsDAO.queryMonthlyCalendarByVenueId(vid);
            req.setAttribute("monthlyDateTimeslot", monthlyDateTimeslot);
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
        timeslotDAO = new TimeslotDAO(dbUrl, dbUser, dbPassword);
    }

}
