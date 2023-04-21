/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.User;
import ict.bean.Venue;
import ict.bean.view.VenueDTO;
import ict.db.UserDAO;
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
@WebServlet(name = "VenueController", urlPatterns = { "/delVenue", "/editVenue","/handleVenue", "/viewVenue",
        "/searchVenues" })
public class VenueController extends HttpServlet {

    private VenueDAO venueDAO;
    private UserDAO userDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String name = req.getParameter("name");
        String district = req.getParameter("district");
        String address = req.getParameter("address");
        int capacity = Integer.parseInt(req.getParameter("capacity"));
        int type = Integer.parseInt(req.getParameter("type"));
//        String img = req.getParameter("img");
        String description = req.getParameter("description");
        int userId = Integer.parseInt(req.getParameter("staff"));
        double hourlyRate = Double.parseDouble(req.getParameter("hourlyRate"));
        if ("add".equalsIgnoreCase(action)) {
            if (venueDAO.addRecord(name, district, address, capacity, type, "/img/venues/chai-wan.jpg", description, userId, hourlyRate)) {
                resp.sendRedirect("searchVenues");
            }
        } else if ("update".equalsIgnoreCase(action)) {
            int vid = Integer.parseInt(req.getParameter("id"));
            Venue v = venueDAO.queryRecordById(vid);
            v.setAddress(address);
            v.setCapacity(capacity);
            v.setDescription(description);
            v.setHourlyRate(hourlyRate);
//            v.setImg(img);
            v.setDistrict(district);
            v.setName(name);
            v.setType(type);
            v.setUserId(userId);

            if (venueDAO.editRecord(v)) {
                resp.sendRedirect("searchVenues");
            }else {
                resp.sendRedirect("searchVenues");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        ArrayList<VenueDTO> vdtos = null;
        if ("delete".equalsIgnoreCase(action)) {
            int vid = Integer.parseInt(req.getParameter("venueId"));
            if (venueDAO.delRecord(vid)) {
                resp.sendRedirect("searchVenues");
            }
            ;
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
        } else if ("edit".equalsIgnoreCase(action)) {
            int vid = Integer.parseInt(req.getParameter("venueId"));
            RequestDispatcher rd;
            VenueDTO vdto = venueDAO.queryRecordToDTOById(vid);
            req.setAttribute("venueDTO", vdto);
            vdtos = venueDAO.queryRecordToDTO();
            req.setAttribute("venueDTOs", vdtos);
            ArrayList<User> staff = userDAO.queryRecordByRole(2);
            req.setAttribute("staff", staff);
            rd = getServletContext().getRequestDispatcher("/venues.jsp");
            rd.forward(req, resp);
        } else {
            req.removeAttribute("venueDTO");
            vdtos = venueDAO.queryRecordToDTO();
            RequestDispatcher rd;
            System.err.println(vdtos);
            req.setAttribute("venueDTOs", vdtos);
            ArrayList<User> staff = userDAO.queryRecordByRole(2);
            req.setAttribute("staff", staff);
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
        userDAO = new UserDAO(dbUrl, dbUser, dbPassword);
    }

}
