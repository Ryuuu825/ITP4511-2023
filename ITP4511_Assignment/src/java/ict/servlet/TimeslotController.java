/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.Timeslot;
import ict.bean.Venue;
import ict.db.TimeslotDAO;
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
@WebServlet(name = "TimeslotController", urlPatterns = {"/searchTimeslots"})
@MultipartConfig
public class TimeslotController extends HttpServlet {

    private VenueDAO venueDAO;
    private TimeslotDAO timeslotDAO;
    private VenueTimeslotDAO vtsDAO;
    

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("add".equalsIgnoreCase(action)) {
            HttpSession session = req.getSession(true);

            resp.sendRedirect("searchTimeslots");
        } else if ("update".equalsIgnoreCase(action)) {

        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("delete".equalsIgnoreCase(action)) {
            int vid = Integer.parseInt(req.getParameter("venueId"));
            HttpSession session = req.getSession(true);
            if (venueDAO.delRecord(vid)) {
                session.setAttribute("message", "Delete venue " + vid + " successfully!");
            } else {
                session.setAttribute("error", "Delete venue " + vid + " failed!" + "<br>Database trace:<br>"
                        + "<div style='color:red' class='ml-5'>" + venueDAO.getLastError() + "</div>");
            };
            resp.sendRedirect("searchTimeslots");
        } else if ("search".equalsIgnoreCase(action)) {
            
        } else if ("edit".equalsIgnoreCase(action)) {
            
        } else {
            req.removeAttribute("venueDTO");
            ArrayList<Venue> venues = venueDAO.queryRecord();
            ArrayList<Timeslot> timeslots = timeslotDAO.queryRecord();
            RequestDispatcher rd;
            req.setAttribute("venues", venues);
            req.setAttribute("timeslots", timeslots);
            rd = getServletContext().getRequestDispatcher("/timeslot.jsp");
            rd.forward(req, resp);
        }
    }

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        venueDAO = new VenueDAO(dbUrl, dbUser, dbPassword);
        timeslotDAO = new TimeslotDAO(dbUrl, dbUser, dbPassword);
    }

}
