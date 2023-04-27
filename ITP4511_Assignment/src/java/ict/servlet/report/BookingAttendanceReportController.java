package ict.servlet.report;

import java.io.IOException;
import java.sql.Date;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ict.bean.Booking;
import ict.bean.User;
import ict.bean.Venue;
import ict.bean.Booking.BookingStatus;
import ict.bean.view.BookingDTO;
import ict.bean.view.BookingReportDTO;
import ict.bean.view.MemberAttendanceDTO;
import ict.db.BookingDAO;
import ict.db.UserDAO;
import ict.db.VenueDAO;
import ict.servlet.ReportController;
import ict.util.CsvUtil;
import ict.util.DbUtil;
import ict.util.JsonArray;
import ict.util.JsonObject;
import ict.util.JsonUtil;
import ict.util.JsonResponse;

@WebServlet(name = "BookingAttendanceReportController", urlPatterns = { "/api/report/bookingarate" })
public class BookingAttendanceReportController extends HttpServlet {

    private DbUtil dbUtil;

    private JsonUtil jsonUtil;
    private BookingDAO bookingDB;
    private UserDAO userDB;

    private ReportController controller;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!dbUtil.testConnectionWithDB()) {
            resp.setStatus(500);
            resp.getWriter().write(dbUtil.getErrorMsg());
            return;
        }

        System.out.println("BookingRateReportController");

        // get all member   
        ArrayList<User> users = userDB.queryRecordByRole( 1 );
        MemberAttendanceDTO dto = new MemberAttendanceDTO();

        LinkedHashMap<User, Integer> attendance = new LinkedHashMap<User, Integer>();

        for (User user : users) {
            // get the booking associate with the member    
            ArrayList<Booking> bookings = bookingDB.queryRecordByUserId(user.getId());
            int count = 0;
            int totalBooking = bookings.size();

            for (Booking booking : bookings) {
                // CANCLE, REJECTED
                if (booking.getStatus() == BookingStatus.CANCEL.ordinal() ) {
                    count++;
                }

            }

            double rate = (double) (totalBooking - count) / totalBooking;
            attendance.put(user, (int) (rate * 100));
        }

        
        dto.setAttendance(attendance);
        // forward to jsp
        RequestDispatcher rd = req.getRequestDispatcher( "/admin/report.jsp?report=barate");

        req.setAttribute("attendance", dto);


        rd.forward(req, resp);
            
    }

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");

        dbUtil = new DbUtil(dbUrl, dbUser, dbPassword);

        jsonUtil = new JsonUtil();
        bookingDB = new BookingDAO(dbUrl, dbUser, dbPassword);
        userDB = new UserDAO(dbUrl, dbUser, dbPassword);

    }



    

}
