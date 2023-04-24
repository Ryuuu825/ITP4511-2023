package ict.tag;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class CheckRoleTag extends SimpleTagSupport {

    private String roleStr; 
    private String redirectFrom;

    public void setRoleStr(String roleStr) {
        this.roleStr = roleStr;
    }

    public String getRoleStr() {
        return roleStr;
    }

    public String getRedirectFrom() {
        return redirectFrom;
    }

    public void setRedirectFrom(String redirectFrom) {
        this.redirectFrom = redirectFrom;
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            HttpSession session = ((PageContext) getJspContext()).getSession();
            HttpServletRequest request = (HttpServletRequest) ((PageContext) getJspContext()).getRequest();
            HttpServletResponse response = (HttpServletResponse) ((PageContext) getJspContext()).getResponse();
            
            String role = (String) session.getAttribute("role");
            System.out.println("role: " + role);

            String _redirectFrom = this.redirectFrom == null ? request.getServletPath() : this.redirectFrom;

            if ( role == null ) {
                session.setAttribute("redirectFrom", _redirectFrom );
                session.setAttribute("unautherror", "You must login to access this page");
                response.sendRedirect( request.getContextPath() + "/login.jsp" ); 
                return;
            } else {
                String[] roles = roleStr.split(",");
        
                if ( ! role.toLowerCase().equals("SeniorManager") ) {
                    for ( String r : roles ) {
                        if ( role.toLowerCase().equals(r.toLowerCase()) ) {
                            return;
                        }
                    }

                    session.setAttribute("redirectFrom", _redirectFrom );
                    session.setAttribute("unautherror", "You are not authorized to access this page");
                    response.sendRedirect( request.getContextPath() + "/login.jsp" );
                    return;
                }
            }

            
        } catch (IOException ioe) {
            System.out.println("Error generating prime: " + ioe);
        }
    }

}
