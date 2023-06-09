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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
@WebServlet(name = "GuestController", urlPatterns = {"/delGuest", "/viewGuests", "/addGuest", "/editGuest", "/createGuests"})
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

        if ("tempListUpdate".equalsIgnoreCase(action)) {

            // get the guestlist from the session
            GuestList guests = (GuestList) req.getSession().getAttribute("guests");
            ArrayList<Guest> guestList = guests.getGuests();

            User user = (User) req.getSession().getAttribute("userInfo");

            guestName = req.getParameter("name");
            guestEmail = req.getParameter("email");

            // add guest to the guestlist
            Guest newGuest = new Guest();
            newGuest.setName(guestName);
            newGuest.setEmail(guestEmail);
            newGuest.setUserId(user.getId());
            newGuest.setId(guestList.size() + 1); // for temp list, the id will change will the guestlist is added to the database

            guestList.add(newGuest);

            // update the guestlist in the session
            guests.setGuests(guestList);

            // update the session
            req.getSession().setAttribute("guests", guests);

            // forward to the same page
            resp.sendRedirect(req.getContextPath() + "/createGuests?action=addlist");

        } else {

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

                guestDAO.addRecord(userId, guestName, guestEmail);
                // get guest id from guestname, guestemail ( from ben ) : goodest idea
                Guest guest = guestDAO.queryRecordByGuestNameAndEmail(guestName, guestEmail);

                // get the guestlist id from booking id and venue id
                GuestList gl = guestListDAO.queryRocordByBooking(bookingId, venueId);
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
            resp.sendRedirect("viewGuests?action=search&bookingId=" + bookingIdStr + "&venueId=" + venueIdStr);

        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean isAuth = CheckRole.checkIfRoleIs(req.getSession(), new String[]{"SeniorManager", "Member", "Staff"});

        GuestList gl = null;
        String action = req.getParameter("action");
        if ("delete".equalsIgnoreCase(action)) {
            String bookingId = req.getParameter("bookingId");
            String venueId = req.getParameter("venueId");
            if (bookingId != null && venueId != null) {
                int bid = Integer.parseInt(req.getParameter("bookingId"));
                int guestId = Integer.parseInt(req.getParameter("guestId"));
                // get guest name
                Guest guest = guestDAO.queryRecordById(guestId);
                String guestName = guest.getName();
                int vid = Integer.parseInt(req.getParameter("venueId"));
                if (guestListGuestDAO.delRecordByGuestId(guestId)) {
                    req.getSession().setAttribute("message", "Deleted Guest <span class='fw-bold'>" + guestName + "</span> successfully!");
                    resp.sendRedirect("viewGuests?action=search&bookingId=" + bid + "&venueId=" + vid);
                };
            } else {
                // delete the temp guest form the temp list
                GuestList guests = (GuestList) req.getSession().getAttribute("guests");
                ArrayList<Guest> guestList = guests.getGuests();
                int guestId = Integer.parseInt(req.getParameter("guestId"));
                for (int i = 0; i < guestList.size(); i++) {
                    if (guestList.get(i).getId() == guestId) {
                        guestList.remove(i);
                        break;
                    }
                } // cannot remove the guest by its index, because the index will change
                // forward to the same page
                resp.sendRedirect(req.getContextPath() + "/createGuests?action=addlist");
            }

        } else if ("search".equalsIgnoreCase(action)) {
            int bid = Integer.parseInt(req.getParameter("bookingId"));
            int vid = Integer.parseInt(req.getParameter("venueId"));

            if (!isAuth) {
                CheckRole.didNotLogin(req, resp, "/viewGuests?bookingId=" + bid + "&venueId=" + vid + "&action=search");
                return;
            }

            User u = (User) req.getSession().getAttribute("userInfo");

            // get all the regiested guests that related to the user but not on current guestlist
            if (!CheckRole.checkIfRoleIs(req.getSession(), new String[]{"SeniorManager", "Staff"})) {
                String userId = u.getId() + "";
                ArrayList<Guest> l = guestDAO.queryRecordRelatedToUser(userId);
                GuestList l2 = guestListDAO.queryRocordByBooking(bid, vid);

                ArrayList<Guest> res = new ArrayList<Guest>();

                for (Guest g : l) {
                    boolean found = false;
                    try {

                        for (Guest g2 : l2.getGuests()) {
                            if (g.getId() == g2.getId() || (g.getName().equals(g2.getName()) && g.getEmail().equals(g2.getEmail()))) {
                                found = true;
                                break;
                            }
                        }
                    } catch (Exception e) {
                    }

                    if (!found) {
                        res.add(g);
                    }
                }

                req.setAttribute("guestsNotOnList", res);

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
            rd = getServletContext().getRequestDispatcher("/viewGuests?bookingId=" + req.getParameter("bookingId") + "&venueId=" + req.getParameter("venueId") + "&action=search");
            rd.forward(req, resp);

        } else if ("addlist".equalsIgnoreCase(action)) {
            // create a guest list in the session
            if (req.getSession().getAttribute("guests") == null) {
                GuestList gl2 = new GuestList();
                gl2.setGuests(new ArrayList<>());
                LocalDate ld = LocalDate.now();
                gl2.setCreateDate(ld);
                req.getSession().setAttribute("guests", gl2);
            }

            User u = (User) req.getSession().getAttribute("userInfo");
            ArrayList<Guest> l = null;

            if (!CheckRole.checkIfRoleIs(req.getSession(), new String[]{"SeniorManager", "Staff"})) {
                String userId = u.getId() + "";
                l = guestDAO.queryRecordRelatedToUser(userId);
            }

            // try to get the guest list on the session
            gl = (GuestList) req.getSession().getAttribute("guests");
            if (gl != null) {
                // check of the guest is already on the list
                for (Guest g : gl.getGuests()) {
                    for (Guest g2 : l) {
                        if (g.getId() == g2.getId() || (g.getName().equals(g2.getName()) && g.getEmail().equals(g2.getEmail()))) {
                            l.remove(g2);
                            break;
                        }
                    }
                }
            }

            req.setAttribute("guestsNotOnList", l);
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/guests_temp.jsp");
            rd.forward(req, resp);

        } else {
            resp.sendRedirect("viewBookings");
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
