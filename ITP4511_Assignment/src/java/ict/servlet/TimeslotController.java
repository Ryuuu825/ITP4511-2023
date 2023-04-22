/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.Timeslot;
import ict.bean.User;
import ict.bean.Venue;
import ict.bean.VenueTimeslot;
import ict.db.TimeslotDAO;
import ict.db.VenueDAO;
import ict.db.VenueTimeslotDAO;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jyuba
 */
@WebServlet(name = "TimeslotController", urlPatterns = {"/searchTimeslots", "/addTimeslot", "/delTimeslot"})
@MultipartConfig
public class TimeslotController extends HttpServlet {

    private VenueDAO venueDAO;
    private TimeslotDAO timeslotDAO;
    private VenueTimeslotDAO vtsDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("delete".equalsIgnoreCase(action)) {
            int vid = Integer.parseInt(req.getParameter("venueId"));
            int tid = Integer.parseInt(req.getParameter("timeslotId"));
            int day = Integer.parseInt(req.getParameter("day"));
            HttpSession session = req.getSession(true);
            if (vtsDAO.delRecordForManagement(tid, vid, day)) {
                session.setAttribute("message", "The time slot is set to not available!");
            } else {
                session.setAttribute("error", "Set the time slot to not available failed!" + "<br>Database trace:<br>"
                        + "<div style='color:red' class='ml-5'>" + venueDAO.getLastError() + "</div>");
            };
            resp.sendRedirect("searchTimeslots?venueId="+vid);
        } else if ("add".equalsIgnoreCase(action)) {
            int vid = Integer.parseInt(req.getParameter("venueId"));
            int tid = Integer.parseInt(req.getParameter("timeslotId"));
            int day = Integer.parseInt(req.getParameter("day"));
            HttpSession session = req.getSession(true);
            if (vtsDAO.addRecordForSetAvailable(tid, vid, day)) {
                session.setAttribute("message", "The time slot is set to available!");
            } else {
                session.setAttribute("error", "Set the time slot to available failed!" + "<br>Database trace:<br>"
                        + "<div style='color:red' class='ml-5'>" + venueDAO.getLastError() + "</div>");
            };
            resp.sendRedirect("searchTimeslots?venueId="+vid);
        } else {
            int vid = 0;
            if (req.getParameter("venueId") != null) {
                vid = Integer.parseInt(req.getParameter("venueId"));
            } else {
                User user = (User) req.getSession().getAttribute("userInfo");
                vid = venueDAO.queryRecordByUserId(user.getId()).get(0).getId();
            }
            ArrayList<Venue> venues = venueDAO.queryRecord();
            ArrayList<Timeslot> timeslots = timeslotDAO.queryRecord();
            ArrayList<VenueTimeslot> vts = vtsDAO.queryWeeklyRocordByVenueId(vid);
            handleTimeslotData(req, vts);
            RequestDispatcher rd;
            req.setAttribute("venues", venues);
            req.setAttribute("timeslots", timeslots);
            req.setAttribute("vts", vts);
            req.setAttribute("selectedVenue", vid);
            rd = getServletContext().getRequestDispatcher("/timeslot.jsp");
            rd.forward(req, resp);
        }
    }

    public void handleTimeslotData(HttpServletRequest req, ArrayList<VenueTimeslot> vts) {
        int[] mon = new int[12];
        int[] tue = new int[12];
        int[] wed = new int[12];
        int[] thu = new int[12];
        int[] fri = new int[12];
        int[] sun = new int[12];
        int[] sat = new int[12];
        for (VenueTimeslot vt : vts) {
            DayOfWeek day = vt.getDate().getDayOfWeek();
            if (day.compareTo(DayOfWeek.MONDAY) == 0) {
                mon[vt.getTimeslotId() - 1] = 1;
            }
            if (day.compareTo(DayOfWeek.TUESDAY) == 0) {
                tue[vt.getTimeslotId() - 1] = 1;
            }
            if (day.compareTo(DayOfWeek.WEDNESDAY) == 0) {
                wed[vt.getTimeslotId() - 1] = 1;
            }
            if (day.compareTo(DayOfWeek.THURSDAY) == 0) {
                thu[vt.getTimeslotId() - 1] = 1;
            }
            if (day.compareTo(DayOfWeek.FRIDAY) == 0) {
                fri[vt.getTimeslotId() - 1] = 1;
            }
            if (day.compareTo(DayOfWeek.SATURDAY) == 0) {
                sat[vt.getTimeslotId() - 1] = 1;
            }
            if (day.compareTo(DayOfWeek.SUNDAY) == 0) {
                sun[vt.getTimeslotId() - 1] = 1;
            }
        }
        req.setAttribute("mon", mon);
        req.setAttribute("tue", tue);
        req.setAttribute("wed", wed);
        req.setAttribute("thu", thu);
        req.setAttribute("fri", fri);
        req.setAttribute("sat", sat);
        req.setAttribute("sun", sun);
    }

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        venueDAO = new VenueDAO(dbUrl, dbUser, dbPassword);
        timeslotDAO = new TimeslotDAO(dbUrl, dbUser, dbPassword);
        vtsDAO = new VenueTimeslotDAO(dbUrl, dbUser, dbPassword);
    }

}
