/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.tag;

import ict.bean.Venue;
import ict.bean.view.BookingDTO;
import ict.bean.view.VenueDTO;
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
public class VenueTag extends SimpleTagSupport {

    private ArrayList<VenueDTO> venues;

    public ArrayList<VenueDTO> getVenues() {
        return venues;
    }

    public void setVenues(ArrayList<VenueDTO> venues) {
        this.venues = venues;
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            out.print("<table class=\"table text-center table-striped\">");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>ID</th><th>Name</th><th>Location</th><th>Address</th><th>Capacity</th><th>Type</th><th>Description</th><th>Person in charge</th><th>HourlyRate</th>");
            out.print("<th class=\"text-end\"><button class=\"btn btn-success btn-rounded btn-sm me-2\" role=\"button\">Add Venue</button></th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            for (VenueDTO v : venues) {
                out.print("<tr class=\"align-middle\">");
                out.print("<th scope = \"row\">" + v.getVenue().getId() + "</th>");
                out.print("<td>" + v.getVenue().getName() + "</td>");
                out.print("<td>" + v.getVenue().getLocation() + "</td>");
                out.print("<td class=\"text-wrap text-start\">" + v.getVenue().getAddress() + "</td>");
                out.print("<td>" + v.getVenue().getCapacity() + "</td>");
                out.print("<td>" + v.getVenue().getTypeString() + "</td>");
                out.print("<td class=\"text-wrap text-start\">" + v.getVenue().getDescription() + "</td>");
                out.print("<td>" + v.getUser().getFirstName() + " " + v.getUser().getLastName() + "</td>");
                out.print("<td>" + v.getVenue().getHourlyRate() + "</td>");
                out.print("<td><a class=\"btn btn-primary btn-rounded btn-sm me-2\" role=\"button\" href=\"editVenue?venueId=" + v.getVenue().getId() + "\">edit</a>");
                out.print("<a class=\"btn btn-danger btn-rounded btn-sm\" role=\"button\" href=\"delVenue?venueId=" + v.getVenue().getId() + "\">delete</a></td>");
                out.print("</tr>");
            }
            out.print("</tbody></table>");

        } catch (IOException ioe) {
            System.out.println("Error generating prime: " + ioe);
        }
    }

}
