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
            out.print("<th>ID</th><th>Name</th><th>District</th><th>Address</th><th>Capacity</th><th>Type</th><th>Person in charge</th><th>HourlyRate(HK$)</th><th>Enable</th>");
            out.print("<th class=\"text-end\"><button id=\"addvenue\" class=\" btn btn-success py-2 px-4 btn-sm me-2\" role=\"button\">Add Venue</button></th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            for (VenueDTO v : venues) {
                out.print("<tr class=\"align-middle\">");
                out.print("<th scope = \"row\">" + v.getVenue().getId() + "</th>");
                out.print("<td>" + v.getVenue().getName() + "</td>");
                out.print("<td>" + v.getVenue().getDistrict() + "</td>");
                out.print("<td class=\"text-wrap text-start\">" + v.getVenue().getAddress() + "</td>");
                out.print("<td>" + v.getVenue().getCapacity() + "</td>");
                out.print("<td>" + v.getVenue().getTypeString() + "</td>");
                if (v.getUser() != null) {
                    out.print("<td>" + v.getUser().getFirstName() + " " + v.getUser().getLastName() + "</td>");
                } else {
                    out.print("<td>Null</td>");
                }
                out.print("<td>" + v.getVenue().getHourlyRate() + "</td>");

                out.print("<td><form action=\"enableVenue\" method=\"post\" class=\"form-check form-switch\">");
                out.print("<input type=\"hidden\" name=\"id\" value=\"" + v.getVenue().getId() + "\">");
                out.print("<input type=\"hidden\" name=\"action\" value=\"enable\">");
                String chk = v.getVenue().getEnable()? "checked" : "";
                out.print("<input class=\"d-none\" type=\"radio\" name=\"enable\" value=\"0\">");
                out.print("<input class=\"d-none\" type=\"radio\" name=\"enable\" value=\"1\" "+ chk +">");
                out.print("<input class=\"form-check-input\" type=\"checkbox\" role=\"switch\" id=\"flexSwitchCheckChecked\" " + chk + " />");
                out.print("</form></td>");

                out.print("<td><a id=\"editvenue\" class=\"btn btn-primary btn-rounded btn-sm me-2\" href=\"editVenue?action=edit&venueId=" + v.getVenue().getId() + "\" onclick=\"editVenue(" + v.getVenue().getId() + ")\" role=\"button\">edit</a>");
                out.print("<a class=\"btn btn-danger btn-rounded btn-sm\" role=\"button\" href=\"delVenue?action=delete&venueId=" + v.getVenue().getId() + "\">delete</a></td>");
                out.print("</tr>");
            }
            out.print("</tbody></table>");

        } catch (IOException ioe) {
            System.out.println("Error generating prime: " + ioe);
        }
    }

}
