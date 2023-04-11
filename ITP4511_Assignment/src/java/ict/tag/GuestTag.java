/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.tag;

import ict.bean.Guest;
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

    private ArrayList<Guest> guests;

    public ArrayList<Guest> getGuests() {
        return guests;
    }

    public void setGuest(ArrayList<Guest> guests) {
        this.guests = guests;
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            out.print("<table class=\"table text-center table-striped\">");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>ID</th><th>Member</th><th>No. of booked venues</th><th>Amount</th><th>Status</th><th>Details</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            for (Guest guest : guests) {
                out.print("<tr>");
                out.print("<th scope = \"row\">" + guest.getId() + "</th>");
                out.print("<td>" + guest.getName()+ "</td>");
                out.print("<td>" + guest.getEmail()+ "</td>");
                out.print("<td><a class=\"btn btn-danger btn-rounded btn-sm\" role=\"button\" href=\"delGuest?guestId=" + guest.getId() + "\">Delete</a></td>");
                out.print("</tr>");
            }
            out.print("</tbody></table>");

        } catch (IOException ioe) {
            System.out.println("Error generating prime: " + ioe);
        }
    }

}
