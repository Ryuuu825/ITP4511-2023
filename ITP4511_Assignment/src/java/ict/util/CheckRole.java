package ict.util;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckRole {

    public static boolean checkIfRoleIs(HttpSession session, String[] roles) {
        String role = (String) session.getAttribute("role");
        for (String r : roles) {
            if (r.equalsIgnoreCase(role)) {
                return true;
            }
        }
        return false;
    }

    public static void redirect(HttpServletRequest req , HttpServletResponse resp , String _redirectFrom ,  String msg) throws IOException {
        req.getSession().setAttribute("unautherror", msg);
        req.getSession().setAttribute("redirectFrom", _redirectFrom );
        resp.sendRedirect( req.getServletContext().getContextPath() + "/login.jsp" );
    }

    public static void didNotLogin(HttpServletRequest req , HttpServletResponse resp , String _redirectFrom) throws IOException {
        redirect(req, resp, _redirectFrom, "You need to login first");
    }

    public static void notEnoughPermission(HttpServletRequest req , HttpServletResponse resp , String _redirectFrom) throws IOException {
        redirect(req, resp, _redirectFrom, "You don't have enough permission to access this page");
    }
    
}
