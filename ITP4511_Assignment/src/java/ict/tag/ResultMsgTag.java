/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.tag;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.http.HttpSession;




public class ResultMsgTag extends SimpleTagSupport {
    
    
    @Override
    public void doTag() throws JspException, IOException {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            HttpSession session = pageContext.getSession();

            // <% 
            //     if (session.getAttribute("message") != null) {
            // %>
            //     <div class="alert alert-success" role="alert">
            //         <%= session.getAttribute("message") %>
            //     </div>
            // <%   
            //     session.removeAttribute("message");
            // } %>

            // <% 
            //     if (session.getAttribute("error") != null) {
            // %>
            //     <div class="alert alert-danger" role="alert">
            //         <%= session.getAttribute("error") %>
            //     </div>
            // <%   
            //     session.removeAttribute("error");
            // } %>
            if (session.getAttribute("message") != null) {
                out.print("<div class=\"alert alert-success\" role=\"alert\">");
                out.print(session.getAttribute("message"));
                out.print("</div>");
                session.removeAttribute("message");
            }

            if (session.getAttribute("error") != null) {
                out.print("<div class=\"alert alert-danger\" role=\"alert\">");
                out.print(session.getAttribute("error"));
                out.print("</div>");
                session.removeAttribute("error");
            }
            
        } catch (IOException ioe) {
            System.out.println("Error generating prime: " + ioe);
        }
    }
    
}
