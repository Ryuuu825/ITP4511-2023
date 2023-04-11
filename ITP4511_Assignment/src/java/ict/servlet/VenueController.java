/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.GuestList;
import ict.bean.view.VenueDTO;
import ict.db.GuestListGuestDAO;
import ict.db.VenueDAO;
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
@WebServlet(name = "VenueController", urlPatterns = {"/delVenue", "/addVenue", "/editVenue", "/viewVenue"})
public class VenueController extends HttpServlet {

    private VenueDAO venueDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
//        if ("add".equalsIgnoreCase(action)) {
//            int bid = Integer.parseInt(req.getParameter("bookingId"));
//            int guestId = Integer.parseInt(req.getParameter("guestId"));
//            if (venueDAO.delRecordByGuestId(guestId)) {
//                resp.sendRedirect("viewGuests?action=search&bookingId=" + bid);
//            };
//        } else if ("update".equalsIgnoreCase(action)) {
//            int bid = Integer.parseInt(req.getParameter("bookingId"));
//            String searchKeys = req.getParameter("search");
//            if (searchKeys != null && !searchKeys.equals("")) {
//                gl = venueDAO.queryRocordByKeyword(bid, searchKeys);
//
//            } else {
//                gl = venueDAO.queryRocordByBookingId(bid);
//            }
//            RequestDispatcher rd;
//            req.setAttribute("venueDTOs", gl);
//            rd = getServletContext().getRequestDispatcher("/guests.jsp");
//            rd.forward(req, resp);
//        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        ArrayList<VenueDTO> vdtos = null;
        if ("delete".equalsIgnoreCase(action)) {
            int vid = Integer.parseInt(req.getParameter("venueId"));
            if (venueDAO.delRecord(vid)) {
                resp.sendRedirect("viewVenue?action=search");
            };
        } else if ("search".equalsIgnoreCase(action)) {
            String searchKeys = req.getParameter("search");
            if (searchKeys != null && !searchKeys.equals("")) {
                vdtos = venueDAO.queryRecordToDTOByKeyword(searchKeys);
            } else {
                vdtos = venueDAO.queryRecordToDTO();
            }
            RequestDispatcher rd;
            req.setAttribute("venueDTOs", vdtos);
            rd = getServletContext().getRequestDispatcher("/venues.jsp");
            rd.forward(req, resp);
        } else{
            vdtos = venueDAO.queryRecordToDTO();
            RequestDispatcher rd;
            req.setAttribute("venueDTOs", vdtos);
            rd = getServletContext().getRequestDispatcher("/venues.jsp");
            rd.forward(req, resp);
        }
    }

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        venueDAO = new VenueDAO(dbUrl, dbUser, dbPassword);
    }

}
