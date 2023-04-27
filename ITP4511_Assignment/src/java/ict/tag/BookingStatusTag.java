/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author jyuba
 */
public class BookingStatusTag extends SimpleTagSupport {

    private String[] statusString = {"Pending Approval", "Rejected", "Pending Check in", "Check in", "Check out", "Cancelled", "Completed"};
    private String[] color = {"btn-warning", "btn-danger", "btn-primary", "btn-success", "btn-danger", "btn-success"};

    private int status;
    private String role;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            if ("Member".equalsIgnoreCase(role)) {
                switch (status) {
                    case 1:
                    case 3:
                        out.print("<button type=\"submit\" class=\"btn btn-lg btn-primary btn-block \">");
                        out.print("Update");
                        out.print("</button>");
                        out.print("<button name=\"status\" value=\"6\" type=\"submit\" class=\"btn btn-lg btn-block btn-danger \">");
                        out.print("Cancel");
                        out.print("</button>");
                        break;
                    default:
                        out.print("<button name=\"status\" disabled class=\"btn btn-lg btn-secondary btn-block \">");
                        out.print(statusString[status-1]);
                        out.print("</button>");
                        break;
                }
            } else {
                switch (status) {
                    case 1:
                        out.print("<button name=\"status\" value=\"3\" type=\"submit\" class=\"btn btn-lg btn-success btn-block \">");
                        out.print("Approve");
                        out.print("</button>");
                        out.print("<button name=\"status\" value=\"2\" type=\"submit\" class=\"btn btn-lg btn-block btn-danger \">");
                        out.print("Reject");
                        out.print("</button>");
                        break;
                    case 3:
                        out.print("<button name=\"status\" value=\"4\" type=\"submit\" class=\"btn btn-lg btn-success btn-block \">");
                        out.print("Check in");
                        out.print("</button>");
                        break;
                    case 5:
                        out.print("<button name=\"status\" value=\"7\" type=\"submit\" class=\"btn btn-lg btn-success btn-block \">");
                        out.print("Check out");
                        out.print("</button>");
                        break;
                }
            }
        } catch (IOException ioe) {
            System.out.println("Error generating prime: " + ioe);
        }
    }
}
