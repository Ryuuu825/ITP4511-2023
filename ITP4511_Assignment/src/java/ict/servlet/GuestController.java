/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.Guest;
import ict.bean.GuestList;
import ict.bean.User;
import ict.db.GuestDAO;
import ict.db.GuestListDAO;
import ict.db.GuestListGuestDAO;
import ict.util.CheckRole;

import java.io.IOException;
import java.util.Enumeration;

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
@WebServlet(name = "GuestController", urlPatterns = {"/delGuest", "/viewGuests" , "/addGuest" , "/editGuest" })
public class GuestController extends HttpServlet {

    private GuestListDAO guestListDAO;
    private GuestListGuestDAO guestListGuestDAO;
    private GuestDAO guestDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String guestName;
        String guestEmail;
        String bookingIdStr;
        String venueIdStr;


        bookingIdStr = req.getParameter("bookingId");
        venueIdStr = req.getParameter("venueId");

        if ("add".equalsIgnoreCase(action)) {
            // add guest
            guestName = req.getParameter("name");
            guestEmail = req.getParameter("email");

            

            int bookingId = Integer.parseInt(bookingIdStr);
            int venueId = Integer.parseInt(venueIdStr);

            // get user id 
            User user = (User) req.getSession().getAttribute("userInfo");
            int userId = user.getId();


            guestDAO.addRecord(userId , guestName , guestEmail);
            // get guest id from guestname, guestemail ( from ben ) : goodest idea
            Guest guest = guestDAO.queryRecordByGuestNameAndEmail(guestName, guestEmail);
            System.out.println("guest id: " + guest.getId());


            // get the guestlist id from booking id and venue id
            GuestList gl =  guestListDAO.queryRocordByBooking(bookingId, venueId );
            int guestListId = gl.getId();

            // add guest to guestlist
            boolean success = guestListGuestDAO.addRecord(guestListId, guest.getId());

            req.getSession().setAttribute("message", "Add Guest <span class='fw-bold'>" + guestName + "</span> successfully!");


        } else if ("update".equalsIgnoreCase(action)) {

            String guestId = req.getParameter("id");

            Enumeration<String> params = req.getParameterNames();
            while (params.hasMoreElements()) {
                String paramName = params.nextElement();
                System.out.println("Parameter Name - " + paramName + ", Value - " + req.getParameter(paramName));
            }
                     
            guestName = req.getParameter("editGuestName");
            guestEmail = req.getParameter("editGuestEmail");

            if (guestName == null || guestName.equals("") || guestEmail == null || guestEmail.equals("")) {
                req.getSession().setAttribute("error", "Guest name and email cannot be empty!");
            } else {
                 // get the guest information from the database
                Guest guest = guestDAO.queryRecordById(Integer.parseInt(guestId));
                guest.setName(guestName);
                guest.setEmail(guestEmail);
                // update the guest information to the database
                guestDAO.editRecord(guest);

                req.getSession().setAttribute("message", "Updated Guest <span class='fw-bold'>" + guestName + "</span> successfully!");
            }

           

        }
        // forward to viewGuests
        resp.sendRedirect("viewGuests?action=search&bookingId="+bookingIdStr+"&venueId="+venueIdStr);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean isAuth = CheckRole.checkIfRoleIs( req.getSession() , new String[] {"SeniorManager", "Member" , "Staff"});

        GuestList gl = null;
        String action = req.getParameter("action");
        if ("delete".equalsIgnoreCase(action)) {
            int bid = Integer.parseInt(req.getParameter("bookingId"));
            int guestId = Integer.parseInt(req.getParameter("guestId"));
            // get guest name
            Guest guest = guestDAO.queryRecordById(guestId);
            String guestName = guest.getName();
            int vid = Integer.parseInt(req.getParameter("venueId"));
            if (guestListGuestDAO.delRecordByGuestId(guestId)) {
                req.getSession().setAttribute("message", "Deleted Guest <span class='fw-bold'>" + guestName + "</span> successfully!");
                resp.sendRedirect("viewGuests?action=search&bookingId="+bid+"&venueId="+vid);
            };
        } else if ("search".equalsIgnoreCase(action)) {
            int bid = Integer.parseInt(req.getParameter("bookingId"));
            int vid = Integer.parseInt(req.getParameter("venueId"));

            if ( ! isAuth ) {
                CheckRole.didNotLogin(req, resp, "/viewGuests?bookingId="+bid+"&venueId="+vid+"&action=search");
                return;
            }
            String searchKeys = req.getParameter("search");
            if (searchKeys != null && !searchKeys.equals("")) {
                gl = guestListDAO.queryRocordByKeyword(bid, vid, searchKeys);

            } else {
                gl = guestListDAO.queryRocordByBooking(bid, vid);
            }
            RequestDispatcher rd;
            req.setAttribute("guests", gl);
            rd = getServletContext().getRequestDispatcher("/guests.jsp");
            rd.forward(req, resp);
        } else if ("edit".equalsIgnoreCase(action)) {
            String guestId = req.getParameter("guestId");

            // get the guest information from the database 
            Guest guest = guestDAO.queryRecordById(Integer.parseInt(guestId));

            // set the guest information to the request
            req.setAttribute("guest", guest);

            // forward to the edit page
            RequestDispatcher rd;
            // /viewGuests?action=search&bookingId=1&venueId=1
            // get the oringal url that the user come from
            rd = getServletContext().getRequestDispatcher("/viewGuests?bookingId="+req.getParameter("bookingId")+"&venueId="+req.getParameter("venueId")+ "&action=search");
            rd.forward(req, resp);
        }
    }

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        guestListDAO = new GuestListDAO(dbUrl, dbUser, dbPassword);
        guestListGuestDAO = new GuestListGuestDAO(dbUrl, dbUser, dbPassword);
        guestDAO = new GuestDAO(dbUrl, dbUser, dbPassword);
    }

}
