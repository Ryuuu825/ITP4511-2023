package ict.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ict.bean.User;
import ict.bean.view.UserAccount;
import ict.db.AccountDAO;
import ict.db.UserDAO;
import ict.util.DbUtil;

@WebServlet(name = "UsersAccountController", urlPatterns = {"/api/admin/users" , "/api/admin/user/"})
public class UsersAccountController extends HttpServlet{
    private AccountDAO accountDB;
    private UserDAO userDB;

    private static final int RECORD_PER_PAGE = 6;


    @Override
    protected void doGet(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException {

        ArrayList<UserAccount> userAccounts = new ArrayList<>();

        String redirectedFrom = req.getParameter("redirectedFrom");
        if (redirectedFrom == null) {
            redirectedFrom = "/admin/users.jsp";
        }

        // remove '' from the string
        redirectedFrom = redirectedFrom.replace("'", "").replace("\"", "");

        String page = req.getParameter("page");

        if (page == null) {
            page = "1"; 
        }

        // return the list of usersaccount
        ArrayList<User> users = userDB.queryRecord( RECORD_PER_PAGE , (Integer.parseInt(page) - 1) * 5 );

        // get the total number of records
        int totalRecords = userDB.getTotalRecords();

        // store number of page the front-end should display
        int totalPages = (int) Math.ceil((double) totalRecords / (double) RECORD_PER_PAGE);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("currentPage", page);

        if (userDB.hasError()) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "Query users unsuccessfully!" + "<br>Database trace:<br>" + 
                "<div style='color:red' class='ml-5'>" + userDB.getLastError() + "</div>");
        }


        for (User user : users) {
            UserAccount userAccount = new UserAccount(user, accountDB.queryRecordById(user.getId()));
            userAccount.getAccount().setPassword(""); // remove password from the response
            userAccounts.add(userAccount);
        }

        // if userAccounts exist in the request, remove it.
        // this bug occurs when user refresh the page
        if (req.getAttribute("userAccounts") != null) {
            req.removeAttribute("userAccounts");
        }

        req.setAttribute("userAccounts", userAccounts);

        // see if specific id is given
        String id = req.getParameter("id");

        if (req.getAttribute("ua") != null) {
            req.removeAttribute("ua");
        }

        if (id != null) {
            UserAccount userAccount = new UserAccount(userDB.queryRecordById(Integer.parseInt(id)), accountDB.queryRecordById(Integer.parseInt(id)));

            // mask the password
            String password = userAccount.getAccount().getPassword();
            userAccount.getAccount().setPassword(password.replaceAll(".", "*"));

            req.setAttribute("ua", userAccount);
        }
        
        // redirect back to redirectedFrom
        RequestDispatcher rd = getServletContext().getRequestDispatcher(redirectedFrom);
        rd.forward(req, res);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");

        String redirectedFrom = req.getParameter("redirectedFrom");
        if (redirectedFrom == null) {
            redirectedFrom = getServletContext().getContextPath() + "/admin/users.jsp";
        }

        // remove '' from the string
        redirectedFrom = redirectedFrom.replace("'", "").replace("\"", "");

        if (action.equals("delete")) {

            boolean result = accountDB.delRecord(Integer.parseInt(req.getParameter("id")));

            HttpSession session = req.getSession();
            if (result) {
                session.setAttribute("message", "Delete account successfully!");
            } else {
                session.setAttribute("error", "Delete account (id:" + req.getParameter("id")  + ") unsuccessfully!" + "<br>Database trace:<br>" + 
                    "<div style='color:red' class='ml-5'>" + accountDB.getLastError() + "</div>");
            }

            // redirect back to redirectedFrom
            res.sendRedirect(redirectedFrom);

        }
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
