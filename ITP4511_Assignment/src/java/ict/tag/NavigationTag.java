/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.tag;

import java.io.IOException;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author jyuba
 */
public class NavigationTag extends SimpleTagSupport {

    private String role;

    public void setRole(String role) {
        this.role = role;
    }

    private Map<String, String> linkMap;

    public NavigationTag() {
        switch (role) {
            case "Staff":
                linkMap.put("Venue", "viewVenue");
                linkMap.put("Timeslot", "viewTimeslot");
                linkMap.put("Booking", "viewBooking");
        }
    }
    
    public void setLinkMap(Map<String, String> linkMap) {
        this.linkMap = linkMap;
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            out.print("<header class=\"z-3 w-100 d-flex flex-row align-items-center justify-content-between p-3\"");
            out.print("        style=\"background-color: #144272; height: 5rem;\">");
            out.print("    <div class=\"title text-uppercase ms-3 me-auto fw-bold text-white\" style=\"font-size: 1.5rem;line-height: 2rem;\">");
            out.print("        <a href=\"index.jsp\" class=\"text-white text-decoration-none\">");
            out.print("            Event Point Limited");
            out.print("        </a>");
            out.print("    </div>");
            out.print("    <ul class=\"nav justify-content-end fs-6 fw-semibold flex align-items-center mr-5\">");
            for (Map.Entry<String, String> entry : linkMap.entrySet()) {
                Object key = entry.getKey();
                Object val = entry.getValue();
                out.print("        <li class=\"nav-item border-start nav-hover\">");
                out.print("            <a class=\"text-white mx-3 text-decoration-none\" href=\"" + entry.getValue() + "\">" + entry.getKey() + "</a>");
                out.print("        </li>");
            }
            out.print("        <li class=\"nav-item border bg-light rounded-1 py-1\">");
            out.print("            <a class=\"mx-3 text-dark text-decoration-none\" href=\"logout\"> Sign Out </a>");
            out.print("        </li>");
            out.print("    </ul>");
            out.print("</header>");
        } catch (IOException ioe) {
            System.out.println("Error generating prime: " + ioe);
        }
    }
}
