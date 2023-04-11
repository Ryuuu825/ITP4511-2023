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

    public void setGuests(ArrayList<Guest> guests) {
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
            out.print("<th>#</th><th>Name</th><th>Email</th><th>Action</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            for (int i=1;i<=guests.size();i++) {
                Guest guest = guests.get(i-1);
                out.print("<tr>");
                out.print("<th scope = \"row\">" + i + "</th>");
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
