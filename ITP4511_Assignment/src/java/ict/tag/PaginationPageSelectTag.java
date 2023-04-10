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
public class PaginationPageSelectTag extends SimpleTagSupport {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;

        // remove page=* from the query string, if exist
        if ( queryString != null ){
            if (queryString.contains("page=")) {
                String[] queryStrings = queryString.split("&");
                String newQueryString = "";
                for (String query : queryStrings) {
                    if (!query.contains("page=")) {
                        newQueryString += query + "&";
                    }
                }
                this.queryString = newQueryString.substring(0, newQueryString.length() - 1);
            }
        }
        
    }


    @Override
    public void doTag() throws JspException, IOException {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();

            int totalPages = java.lang.Integer.parseInt(pageContext.getRequest().getAttribute("totalPages").toString());
            int currentPage = java.lang.Integer.parseInt(pageContext.getRequest().getAttribute("currentPage").toString());

            out.println("<div class=\"pagination flex flex-row justify-center absolute bottom-0 w-full\">");
            for (int i = 1; i <= totalPages; i++) {
                if (i != currentPage) {
                    // out.println("<a href="?page=<%= i %><% if (request.getParameter("id") != null) { %>&id=<%= request.getParameter("id") %><% } %>" class="mx-3 underline"><%= i %></a>");
                    if (queryString == null) {
                        out.println("<a href=\"?page=" + i + "\" class=\"mx-3 underline\">" + i + "</a>");
                    } else {
                        out.println("<a href=\"?page=" + i + "&" + queryString + "\" class=\"mx-3 underline\">" + i + "</a>");
                    }
                } else {
                    out.println("<span class=\"mx-3 disabled text-primary font-bold\">" + i + "</span>");
                }
            }
            out.println("</div>");

        } catch (IOException ioe) {
        }
    }

}
