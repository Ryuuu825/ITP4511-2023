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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author jyuba
 */
@WebServlet(name = "VenueController", urlPatterns = {"/delVenue", "/editVenue", "/handleVenue", "/viewVenue", "/enableVenue",
    "/searchVenues"})
@MultipartConfig
public class VenueController extends HttpServlet {

    private VenueDAO venueDAO;
    private UserDAO userDAO;

    public String writeImage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Part filePart = req.getPart("img");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        InputStream inputStream = filePart.getInputStream();
        System.err.println(getServletContext().getRealPath("").split("build")[0]);
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        String url = getServletContext().getRealPath("").split("build")[0] + "web/img/venues/" + fileName;
        if (isWindows) {
            url = getServletContext().getRealPath("").split("build")[0] + "web\\img\\venues\\" + fileName;
        }
        FileOutputStream outputStream = new FileOutputStream(url);
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        inputStream.close();
        return "img/venues/" + fileName;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equalsIgnoreCase(action)) {
            String name = req.getParameter("name");
            String district = req.getParameter("district");
            String address = req.getParameter("address");
            int capacity = Integer.parseInt(req.getParameter("capacity"));
            int type = Integer.parseInt(req.getParameter("type"));
            String description = req.getParameter("description");
            int userId = Integer.parseInt(req.getParameter("staff"));
            double hourlyRate = Double.parseDouble(req.getParameter("hourlyRate"));
            String imgurl = writeImage(req, resp); // write a file to server folder
            HttpSession session = req.getSession(true);
            if (venueDAO.addRecord(name, district, address, capacity, type, imgurl, description, userId, hourlyRate)) {
                session.setAttribute("message", "Add venue " + name + " successfully!");
            } else {
                session.setAttribute("error", "The venue " + name + " already exists!" + "<br>Database trace:<br>"
                        + "<div style='color:red' class='ml-5'>" + venueDAO.getLastError() + "</div>");
            }
            resp.sendRedirect("searchVenues");
        } else if ("update".equalsIgnoreCase(action)) {
            int vid = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            String district = req.getParameter("district");
            String address = req.getParameter("address");
            int capacity = Integer.parseInt(req.getParameter("capacity"));
            int type = Integer.parseInt(req.getParameter("type"));
            String description = req.getParameter("description");
            int userId = Integer.parseInt(req.getParameter("staff"));
            double hourlyRate = Double.parseDouble(req.getParameter("hourlyRate"));
            boolean imgChg = Boolean.parseBoolean(req.getParameter("changeImage"));
            Venue v = venueDAO.queryRecordById(vid);
            v.setAddress(address);
            v.setCapacity(capacity);
            v.setDescription(description);
            v.setHourlyRate(hourlyRate);
            if (imgChg) {
                String imgurl = writeImage(req, resp); // write a file to server folder
                v.setImg(imgurl);
            }
            v.setDistrict(district);
            v.setName(name);
            v.setType(type);
            v.setUserId(userId);

            HttpSession session = req.getSession(true);
            if (venueDAO.editRecord(v)) {
                session.setAttribute("message", "Update venue " + v.getName() + " successfully!");
            } else {
                session.setAttribute("error", "Update venue " + v.getName() + " failed!" + "<br>Database trace:<br>"
                        + "<div style='color:red' class='ml-5'>" + venueDAO.getLastError() + "</div>");
            }
            resp.sendRedirect("searchVenues");
        } else if ("enable".equalsIgnoreCase(action)) {
            int vid = Integer.parseInt(req.getParameter("id"));
            int disable = Integer.parseInt(req.getParameter("enable"));
            Venue v = venueDAO.queryRecordById(vid);
            v.setEnable(disable > 0);
            HttpSession session = req.getSession(true);
            if (venueDAO.editRecord(v)) {
                session.setAttribute("message", "Enable venue " + v.getName() + " successfully!");
            } else {
                session.setAttribute("error", "Enable venue " + v.getName() + " failed!" + "<br>Database trace:<br>"
                        + "<div style='color:red' class='ml-5'>" + venueDAO.getLastError() + "</div>");
            }
            resp.sendRedirect("searchVenues");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        ArrayList<VenueDTO> vdtos = null;
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
