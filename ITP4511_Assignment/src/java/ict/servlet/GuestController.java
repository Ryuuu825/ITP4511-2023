/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.GuestList;
import ict.db.AccountDAO;
import ict.db.GuestDAO;
import ict.db.GuestListDAO;
import ict.db.GuestListGuestDAO;
import ict.db.UserDAO;
import java.io.IOException;
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
@WebServlet(name = "GuestController", urlPatterns = {"/delGuest", "/viewGuests"})
public class GuestController extends HttpServlet {

    private GuestListDAO guestListDAO;
    private GuestListGuestDAO guestListGuestDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GuestList gl = null;
        String action = req.getParameter("action");
        if ("delete".equalsIgnoreCase(action)) {
            int bid = Integer.parseInt(req.getParameter("bookingId"));
            int guestId = Integer.parseInt(req.getParameter("guestId"));
            if (guestListGuestDAO.delRecordByGuestId(guestId)) {
                resp.sendRedirect("viewGuests?action=search&bookingId="+bid);
            };
        } else if ("search".equalsIgnoreCase(action)) {
            int bid = Integer.parseInt(req.getParameter("bookingId"));
            String searchKeys = req.getParameter("search");
            if (searchKeys != null && !searchKeys.equals("")) {
                gl = guestListDAO.queryRocordByKeyword(bid, searchKeys);

            } else {
                gl = guestListDAO.queryRocordByBookingId(bid);
            }
            RequestDispatcher rd;
            req.setAttribute("guests", gl);
            rd = getServletContext().getRequestDispatcher("/guests.jsp");
            rd.forward(req, resp);
            return;
        } else {
        }
    }

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        guestListDAO = new GuestListDAO(dbUrl, dbUser, dbPassword);
        guestListGuestDAO = new GuestListGuestDAO(dbUrl, dbUser, dbPassword);
    }

}
