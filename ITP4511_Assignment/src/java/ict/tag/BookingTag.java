/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.tag;

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
public class BookingTag extends SimpleTagSupport {

    private ArrayList<BookingDTO> bookings;
    private String role;

    public ArrayList<BookingDTO> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<BookingDTO> bookings) {
        this.bookings = bookings;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private String[] status = {"Pending Approval", "Rejected", "Pending Check in", "Check in", "Check out", "Cancel", "Complete"};
    private String[] color = {"warning", "danger", "warning", "primary", "success", "danger", "success"};

    @Override
    public void doTag() throws JspException, IOException {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            out.print("<table class=\"table text-center table-striped\">");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>ID</th><th>Member</th><th>No. of booked venues</th><th>Amount</th><th>Create Date</th><th>Status</th><th>Details</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            for (BookingDTO booking : bookings) {
                out.print("<tr class=\"align-middle\">");
                out.print("<th scope = \"row\">" + booking.getBooking().getId() + "</th>");
                out.print("<td>" + booking.getMember().getFirstName() + " " + booking.getMember().getLastName() + "</td>");
                out.print("<td>" + booking.getVenueTimeslotses().size() + "</td>");
                out.print("<td>" + booking.getBooking().getAmount() + "</td>");
                out.print("<td>" + booking.getBooking().getCreateDate() + "</td>");
                out.print("<td class=\"text-" + color[booking.getBooking().getStatus() - 1] + "\">");
                out.print("<label class=\"border border-2 rounded-pill px-3 py-1 border-" + color[booking.getBooking().getStatus() - 1] + "\">");
                out.print(status[booking.getBooking().getStatus() - 1] + "</label></td>");
                out.print("<td><a class=\"btn btn-link btn-rounded btn-sm\" role=\"button\" href=\"viewBooking?bookingId=" + booking.getBooking().getId() + "\">View</a></td>");
                out.print("</tr>");
            }
            out.print("</tbody></table>");

        } catch (IOException ioe) {
            System.out.println("Error generating prime: " + ioe);
        }
    }

}
