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
    
    public ArrayList<BookingDTO> getBookings() {
        return bookings;
    }
    
    public void setBookings(ArrayList<BookingDTO> bookings) {
        this.bookings = bookings;
    }
    
    @Override
    public void doTag() throws JspException, IOException {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            
            out.print("<table class=\"table table-striped\">");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>ID</th><th>Member</th><th>No. of venues</th><th>No. of guests</th><th>Amount</th><th>Status</th><th>Details</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            for (BookingDTO booking : bookings) {
                System.out.println("wei"+booking);
                out.print("<tr>");
                out.print("<th scope = \"row\">" + booking.getBooking().getId() + "</th>");
                out.print("<td>" + booking.getMember() + "</td>");
                out.print("<td>" + booking.getVenues().size() + "</td>");
                out.print("<td>" + booking.getGuestLists().size() + "</td>");
                out.print("<td>" + booking.getBooking().getAmount() + "</td>");
                out.print("<td>" + booking.getBooking().getStatus() + "</td>");
                out.print("<td><a href=\"viewBooking?"+booking.getBooking().getId()+"\">View</a></td>");
                out.print("</tr>");
            }
            out.print("</tbody></table>");
            
        } catch (IOException ioe) {
            System.out.println("Error generating prime: " + ioe);
        }
    }
    
}
