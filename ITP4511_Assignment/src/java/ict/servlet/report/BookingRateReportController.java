package ict.servlet.report;

import java.io.IOException;
import java.sql.Date;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ict.bean.Booking;
import ict.bean.Venue;
import ict.bean.view.BookingDTO;
import ict.bean.view.BookingReportDTO;
import ict.db.BookingDAO;
import ict.db.VenueDAO;
import ict.servlet.ReportController;
import ict.util.CsvUtil;
import ict.util.DbUtil;
import ict.util.JsonArray;
import ict.util.JsonObject;
import ict.util.JsonUtil;
import ict.util.JsonResponse;

@WebServlet(name = "BookingRateReportController", urlPatterns = { "/api/report/bookingrate" })
public class BookingRateReportController extends HttpServlet {

    private DbUtil dbUtil;

    private JsonUtil jsonUtil;

    private VenueDAO venueDB;
    private BookingDAO bookingDB;

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

        String venueId = req.getParameter("venue");
        String time = req.getParameter("time");

        int venueIdInt = Integer.parseInt(venueId);
        JsonResponse jsonRes = null;


        if (time != null && time.equals("monthly")) {
            String fromYear = req.getParameter("fromYear");
            if (fromYear == null) {
                jsonRes = countRecent12MonthsOccupancyRate(venueIdInt );
            } else {
                jsonRes = coun12MonthsOccupancyRateOfYear(venueIdInt,Integer.parseInt(fromYear));
            }

        } else if (time != null && time.equals("yearly")) {

            jsonRes = countYearlyOccRate(venueIdInt);

        } else if (time != null && time.equals("daily")) {
            String monthStr = req.getParameter("month");
            String yearStr = req.getParameter("year");

            int year = Integer.parseInt(yearStr);
            int month = Month.valueOf(monthStr.toUpperCase()).getValue();

            
            jsonRes = countDailyOccRateOfMonth(month, year , venueIdInt);
        } else {
            return;
        }

        jsonUtil.setHeader(resp);
        resp.getWriter().write(jsonRes.toValueString());

            
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

    public JsonArray countRecent12MonthsOccupancyRate(int venueId) {
            
        JsonArray jsonMonthlyOccupancy = new JsonArray("recent 12 months occupancy rate");

        // monthly in the past 12 months
        for (int i = 0; i <= 11; i++) {
            String sql = "SELECT COUNT(*) FROM venue_timeslot WHERE venueId = ? AND MONTH(date) = MONTH(CURRENT_DATE()) - ?;";
            ArrayList<Object>  params = new ArrayList<>();
            params.add(venueId);
            params.add(i);
            ArrayList<Map<String, Object>> rs = dbUtil.findRecord(sql, params);
            float countMonthlyO = Float.parseFloat(rs.get(0).get("COUNT(*)").toString());

            params.clear();
            rs.clear();

            sql = "SELECT COUNT(*) FROM venue_timeslot WHERE venueId = ? AND MONTH(date) = MONTH(CURRENT_DATE()) - ? AND BookingId IS NOT NULL;";
            params = new ArrayList<>();
            params.add(venueId);
            params.add(i);
            rs = dbUtil.findRecord(sql, params);
            float countMonthlyBooked = Float.parseFloat(rs.get(0).get("COUNT(*)").toString());

            params.clear();
            rs.clear();

            double occupancyMonthly = 0;
            if (countMonthlyO != 0) {
                occupancyMonthly = countMonthlyBooked / countMonthlyO ;
            }

            // calculate the month of i
            // get the current month
            Calendar cal = Calendar.getInstance();
            int currentMonth = cal.get(Calendar.MONTH);
            int currentYear = cal.get(Calendar.YEAR);
            boolean isLastYear = false;

            if ( currentMonth - i < 0 ) {
                currentMonth += 12;
                isLastYear = true;
            }

            int resMonth =  Math.abs(currentMonth - i) + 1;
            String monthString = Month.of(resMonth).name();
            String yearString = isLastYear ? String.valueOf(currentYear - 1) : String.valueOf(currentYear);

            JsonObject res = new JsonObject("");
            res.add("month", monthString);
            res.add("year", yearString);
            res.add("occupancy", occupancyMonthly);

            jsonMonthlyOccupancy.add(res.toValueString());
        }

        return jsonMonthlyOccupancy;
    
    }

    public JsonArray countDailyOccRateOfMonth(int month , int year, int venueId) {

        JsonArray dailyOccRate = new JsonArray("daily occupancy rate of month" + month + "year" + year);
        // daily in the month
        // get the number of days in the month
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i <= daysInMonth; i++) {
            String sql = "SELECT COUNT(*) FROM venue_timeslot WHERE MONTH(date) = ? AND DAY(date) = ? AND venueId = ?;";
            ArrayList<Object>  params = new ArrayList<>();
            params.add(month);
            params.add(i);
            params.add(venueId);
            ArrayList<Map<String, Object>> rs = dbUtil.findRecord(sql, params);
            float countDailyO = Float.parseFloat(rs.get(0).get("COUNT(*)").toString());

            params.clear();
            rs.clear();

            sql = "SELECT COUNT(*) FROM venue_timeslot WHERE MONTH(date) = ? AND DAY(date) = ? AND BookingId IS NOT NULL AND venueId = ?;";
            params = new ArrayList<>();
            params.add(month);
            params.add(i);
            params.add(venueId);
            rs = dbUtil.findRecord(sql, params);
            float countDailyBooked = Float.parseFloat(rs.get(0).get("COUNT(*)").toString());

            params.clear();
            rs.clear();

            double occupancyDaily = 0;
            if (countDailyO != 0) {
                occupancyDaily = countDailyBooked / countDailyO ;
            }

            JsonObject res = new JsonObject("");
            res.add("day", i);
            res.add("occupancy", occupancyDaily);

            dailyOccRate.add(res.toValueString());
        }

        return dailyOccRate;

    }

    public JsonObject countYearlyOccRate(int venueId) {

        JsonObject jsonYearlyOccupancy = new JsonObject("");

        // get the least year
        String sql = "SELECT MIN(YEAR(date)) FROM venue_timeslot WHERE venueId = ?;";
        ArrayList<Object>  params = new ArrayList<>();
        params.add(venueId);

        ArrayList<Map<String, Object>> rs = dbUtil.findRecord(sql, params);
        int minYear = Integer.parseInt(rs.get(0).get("MIN(YEAR(date))").toString());

        params.clear();

        jsonYearlyOccupancy.add("minYear", minYear);
        jsonYearlyOccupancy.add("systemYear", Calendar.getInstance().get(Calendar.YEAR));

        // calculate the current year to the least year
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);

        JsonArray re = new JsonArray("data");

        for (int i = currentYear; i >= minYear; i--) {
            sql = "SELECT COUNT(*) FROM venue_timeslot WHERE YEAR(date) = ? AND venueId = ?;";
            params = new ArrayList<>();
            params.add(i);
            params.add(venueId);
            rs = dbUtil.findRecord(sql, params);
            float countYearlyO = Float.parseFloat(rs.get(0).get("COUNT(*)").toString());

            params.clear();
            rs.clear();

            sql = "SELECT COUNT(*) FROM venue_timeslot WHERE YEAR(date) = ? AND BookingId IS NOT NULL AND venueId = ?;";
            params = new ArrayList<>();
            params.add(i);
            params.add(venueId);
            rs = dbUtil.findRecord(sql, params);
            float countYearlyBooked = Float.parseFloat(rs.get(0).get("COUNT(*)").toString());

            params.clear();
            rs.clear();

            double occupancyYearly = 0;
            if (countYearlyO != 0) {
                occupancyYearly = countYearlyBooked / countYearlyO ;
            }

            JsonObject res = new JsonObject("");
            res.add("occupancy", occupancyYearly);
            res.add("year", i);
            re.add(res.toValueString());
        }


        jsonYearlyOccupancy.add(re);


        return jsonYearlyOccupancy;
    }


    public JsonArray coun12MonthsOccupancyRateOfYear(int venueId , int year) {
            
        JsonArray jsonMonthlyOccupancy = new JsonArray("12 months occupancy rate");

        // monthly in the past 12 months
        for (int i = 1; i <= 12; i++) {
            String sql = "SELECT COUNT(*) FROM venue_timeslot WHERE venueId = ? AND YEAR(date) = ? AND MONTH(date) = ?;";
            ArrayList<Object>  params = new ArrayList<>();
            params.add(venueId);
            params.add(year);
            params.add(i);
            ArrayList<Map<String, Object>> rs = dbUtil.findRecord(sql, params);
            float countMonthlyO = Float.parseFloat(rs.get(0).get("COUNT(*)").toString());

            params.clear();
            rs.clear();

            sql = "SELECT COUNT(*) FROM venue_timeslot WHERE venueId = ? AND YEAR(date) = ? AND MONTH(date) = ? AND BookingId IS NOT NULL;";
            params = new ArrayList<>();
            params.add(venueId);
            params.add(year);
            params.add(i);
            rs = dbUtil.findRecord(sql, params);
            float countMonthlyBooked = Float.parseFloat(rs.get(0).get("COUNT(*)").toString());

            params.clear();
            rs.clear();

            double occupancyMonthly = 0;
            if (countMonthlyO != 0) {
                occupancyMonthly = countMonthlyBooked / countMonthlyO ;
            }

            // calculate the month of i
            // get the current month
            Calendar cal = Calendar.getInstance();
            String monthString = Month.of(i).name();
            String yearString = String.valueOf(year);

            JsonObject res = new JsonObject("");
            res.add("month", monthString);
            res.add("year", yearString);
            res.add("occupancy", occupancyMonthly);

            jsonMonthlyOccupancy.add(res.toValueString());
        }

        return jsonMonthlyOccupancy;
    
    }



    

}
