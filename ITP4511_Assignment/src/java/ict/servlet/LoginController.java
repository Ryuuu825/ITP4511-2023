/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.Account;
import ict.bean.User;
import ict.db.AccountDAO;
import ict.db.UserDAO;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jyuba
 */
@WebServlet(name = "loginController", urlPatterns = {"/handleLogin", "/handleRegister", "/logout"})
public class LoginController extends HttpServlet {

    private AccountDAO accountDB;
    private UserDAO userDB;

    private static HashMap<String, String> loginSuccessRedirectMap = new HashMap<String, String>() {
        {
            put("SeniorManager", "admin/index.jsp");
            put("Staff", "searchBookings");
            put("Member", "findVenue");
        }
    };

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("login".equalsIgnoreCase(action)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            if (accountDB.isVaildAccount(username, password)) {
                doLogin(req, resp);
            } else {
                RequestDispatcher rd;
                req.setAttribute("error", "Username or password incorrect!");
                rd = getServletContext().getRequestDispatcher("/login.jsp");
                rd.forward(req, resp);
            }
        } else if ("register".equalsIgnoreCase(action)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            boolean isSuccess = false;
            isSuccess = accountDB.addRecord(username, password, 1);
            if (isSuccess) {
                int accountId = accountDB.queryRecordByUsername(username).getId();
                isSuccess = userDB.addRecord(accountId, firstName, lastName, email, phone);
                if (isSuccess) {
                    resp.sendRedirect("registerResult.jsp");
                } else {
                    accountDB.delRecord(accountId);
                    RequestDispatcher rd;
                    req.setAttribute("error", "User information invalid.\nCreate account unsuccessfully!");
                    rd = getServletContext().getRequestDispatcher("/register.jsp");
                    rd.forward(req, resp);
                }
            } else {
                RequestDispatcher rd;
                req.setAttribute("error", "Username had existed!");
                rd = getServletContext().getRequestDispatcher("/register.jsp");
                rd.forward(req, resp);
            }
        }
    }

    private void doLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        Account acc = accountDB.queryRecordByUsername(username);
        User u = userDB.queryRecordByAccountId(acc.getId());
        HttpSession session = req.getSession(true);

        String redirectedFrom = req.getParameter("redirectFrom");

        session.setAttribute("userInfo", u);
        session.setAttribute("role", acc.getRoleString());


        if ( redirectedFrom == null ) {
            resp.sendRedirect(loginSuccessRedirectMap.get(acc.getRoleString()));
        }
        else {
            resp.sendRedirect( getServletContext().getContextPath() + redirectedFrom ); // dont allow cross domain
        }
        
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute("userInfo");
            session.removeAttribute("role");
            session.invalidate();
        }
        resp.sendRedirect("login.jsp");
    }

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        accountDB = new AccountDAO(dbUrl, dbUser, dbPassword);
        userDB = new UserDAO(dbUrl, dbUser, dbPassword);
    }

}
