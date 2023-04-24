/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.User;
import ict.bean.Venue;
import ict.bean.VenueTimeslot;
import ict.bean.view.CalendarTimeslot;
import ict.bean.view.VenueDTO;
import ict.bean.view.VenueTimeslots;
import ict.db.UserDAO;
import ict.db.VenueDAO;
import ict.db.VenueTimeslotDAO;
import java.io.IOException;
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
@WebServlet(name = "VenueBookingController", urlPatterns = {"/findVenue", "/getCalendar"})
@MultipartConfig
public class VenueBookingController extends HttpServlet {

    private VenueDAO venueDAO;
    private UserDAO userDAO;
    private VenueTimeslotDAO vtsDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equalsIgnoreCase(action)) {

            int userId = Integer.parseInt(req.getParameter("staff"));

            HttpSession session = req.getSession(true);
//            if (venueDAO.addRecord(name, district, address, capacity, type, imgurl, description, userId, hourlyRate)) {
//                session.setAttribute("message", "Add venue " + name + " successfully!");
//            } else {
//                session.setAttribute("error", "The venue " + name + " already exists!" + "<br>Database trace:<br>"
//                        + "<div style='color:red' class='ml-5'>" + venueDAO.getLastError() + "</div>");
//            }
            resp.sendRedirect("searchVenues");
        } else if ("update".equalsIgnoreCase(action)) {
            HttpSession session = req.getSession(true);
//            if (venueDAO.editRecord(v)) {
//                session.setAttribute("message", "Update venue " + v.getName() + " successfully!");
//            } else {
//                session.setAttribute("error", "Update venue " + v.getName() + " failed!" + "<br>Database trace:<br>"
//                        + "<div style='color:red' class='ml-5'>" + venueDAO.getLastError() + "</div>");
//            }
            resp.sendRedirect("searchVenues");
        } else {
            resp.sendRedirect("findVenues");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        ArrayList<VenueDTO> vdtos = null;
        ArrayList<Venue> venues = venueDAO.queryRecord();
        req.setAttribute("venueList", venues);
        if ("delete".equalsIgnoreCase(action)) {
            int vid = Integer.parseInt(req.getParameter("venueId"));
            HttpSession session = req.getSession(true);
            if (venueDAO.delRecord(vid)) {
                session.setAttribute("message", "Delete venue " + vid + " successfully!");
            } else {
                session.setAttribute("error", "Delete venue " + vid + " failed!" + "<br>Database trace:<br>"
                        + "<div style='color:red' class='ml-5'>" + venueDAO.getLastError() + "</div>");
            };
            resp.sendRedirect("searchVenues");
        } else if ("search".equalsIgnoreCase(action)) {
            String searchKeys = req.getParameter("search");
            if (searchKeys != null && !searchKeys.equals("")) {
                vdtos = venueDAO.queryRecordToDTOByKeyword(searchKeys);
            } else {
                vdtos = venueDAO.queryRecordToDTO();
            }
            RequestDispatcher rd;
            req.removeAttribute("venueDTO");
            req.setAttribute("venueDTOs", vdtos);
            ArrayList<User> staff = userDAO.queryRecordByRole(2);
            req.setAttribute("staff", staff);
            rd = getServletContext().getRequestDispatcher("/venues.jsp");
            rd.forward(req, resp);
        } else if ("calendar".equalsIgnoreCase(action)) {
            String org_path = req.getServletPath();
            User user = (User) req.getSession(false).getAttribute("userInfo");
            if (user == null) {
                resp.sendRedirect("login.jsp");
                return;
            }
            int vid = Integer.parseInt(req.getParameter("venueId"));
            RequestDispatcher rd;
            ArrayList<CalendarTimeslot> calendar = vtsDAO.queryCalendarByVenueId(vid);
            req.setAttribute("calendar", calendar);
            rd = getServletContext().getRequestDispatcher("/findvenue.jsp");
            rd.forward(req, resp);
        } else {
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
