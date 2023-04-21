package ict.servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ict.bean.Venue;
import ict.bean.view.BookingDTO;
import ict.db.BookingDAO;
import ict.db.VenueDAO;
import ict.util.DbUtil;
import ict.util.JsonArray;
import ict.util.JsonObject;
import ict.util.JsonUtil;

@WebServlet(name = "VeuneReportController", urlPatterns = {"/api/report/venue"})
public class VeuneReportController extends HttpServlet {

    private DbUtil dbUtil;

    private JsonUtil jsonUtil;

    private VenueDAO venueDB;
    private BookingDAO bookingDB;

    private void setHttpHeader(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }


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

        ArrayList<BookingDTO> bdtos = bookingDB.queryRecordToDTO();
        String redirectBackTo = getServletContext().getContextPath() + "/admin/reports/report_venue.jsp";
        RequestDispatcher rd;
        req.setAttribute("bookingDTOs", bdtos);
        rd = getServletContext().getRequestDispatcher("/admin/report.jsp?report=venue");
        rd.forward(req, resp);
    }

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");

        dbUtil = new DbUtil(dbUrl, dbUser, dbPassword);
        jsonUtil = new JsonUtil();
        venueDB = new VenueDAO(dbUrl, dbUser, dbPassword);
        bookingDB = new BookingDAO(dbUrl, dbUser, dbPassword);
    }

    public JsonObject countRecent12MonthsBookings(int venueId) {
        int countMonthly = 0;
        JsonObject jsonMonthly = new JsonObject("recent 12 months bookings");

        // monthly in the past 12 months
        for (int i = 0; i <= 11; i++) {
            String sql = "SELECT COUNT(*) FROM venue_timeslot WHERE venueId = ? AND BookingId IS NOT NULL AND MONTH(date) = MONTH(CURRENT_DATE()) - ?;";
            ArrayList<Object>  params = new ArrayList<>();
            params.add(venueId);
            params.add(i);
            ArrayList<Map<String, Object>> rs = dbUtil.findRecord(sql, params);
            int countMonthlyI = Math.round(Float.parseFloat(rs.get(0).get("COUNT(*)").toString()));

            jsonMonthly.add(Integer.toString(i), countMonthlyI);

            params.clear();
            rs.clear();
        }

        return jsonMonthly;
    }

}
