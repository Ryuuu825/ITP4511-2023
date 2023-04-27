/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.tag;

import ict.bean.Guest;
import ict.bean.GuestList;
import ict.bean.view.BookingDTO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author jyuba
 */
public class GuestTag extends SimpleTagSupport {

    private GuestList guests;
    private String role;

    public GuestList getGuests() {
        return guests;
    }

    public void setGuests(GuestList guests) {
        this.guests = guests;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            out.print("<table class=\"table text-center table-striped\">");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>#</th><th>Name</th><th>Email</th>");
            if ("Member".equalsIgnoreCase(role)) {
                out.print("<th>Action</th>");
            }
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            try {
                for (int i = 1; i <= guests.getGuests().size(); i++) {
                    Guest guest = guests.getGuests().get(i - 1);
                    out.print("<tr class=\"align-middle\">");
                    out.print("<th scope = \"row\">" + i + "</th>");
                    out.print("<td>" + guest.getName() + "</td>");
                    out.print("<td>" + guest.getEmail() + "</td>");
                    if ("Member".equalsIgnoreCase(role)) {
                        out.print("<td><a class=\"btn btn-primary btn-rounded me-2 btn-sm\" role=\"button\"\" href=\"editGuest?action=edit&bookingId=" + guests.getBookingId() + "&venueId=" + guests.getVenueId() + "&guestId=" + guest.getId() + "\">Edit</a>"
                                + "<a class=\"btn btn-danger btn-rounded btn-sm\" role=\"button\" href=\"delGuest?action=delete&bookingId=" + guests.getBookingId() + "&venueId=" + guests.getVenueId() + "&guestId=" + guest.getId() + "\">Delete</a></td>");
                    }
                    out.print("</tr>");
                }
            } catch (Exception e) {
                System.out.println("Error generating prime: " + e);
            }

            out.print("</tbody></table>");

        } catch (IOException ioe) {
            System.out.println("Error generating prime: " + ioe);
        }
    }

}
