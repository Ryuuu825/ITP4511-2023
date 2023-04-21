/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.tag;

import ict.util.DbUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class CheckDbTag extends SimpleTagSupport {
    
    
    @Override
    public void doTag() throws JspException, IOException {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            DbUtil dbUtil;


            String dbUser = pageContext.getServletContext().getInitParameter("dbUser");
            String dbPassword = pageContext.getServletContext().getInitParameter("dbPassword");
            String dbUrl = pageContext.getServletContext().getInitParameter("dbUrl");

            dbUtil = new DbUtil(dbUrl, dbUser, dbPassword);
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

            // get its url
            String url = request.getRequestURL().toString();

            boolean dbAvailable = dbUtil.testConnectionWithDB();
            if (!dbAvailable) {
                // forward to error page , getServletContext().getContextPath() + "/http/500.jsp"
                pageContext.getRequest().setAttribute("error", "Database is not available");
                pageContext.getRequest().setAttribute("redirect", url);
                pageContext.getRequest().getRequestDispatcher("/http/500.jsp").forward(pageContext.getRequest(), pageContext.getResponse());
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
