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

@WebServlet(name = "VenueIncomeReportController", urlPatterns = { "/api/report/vincome" })
public class VenueIncomeReportController extends HttpServlet {

    private DbUtil dbUtil;

    private JsonUtil jsonUtil;

    private VenueDAO venueDB;
    private BookingDAO bookingDB;


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
        String action = req.getParameter("action");
        String year = req.getParameter("year");

        JsonResponse jsonRes = null;

        if (action != null && action.equals("list")) { // list out all year available
           jsonRes = getAllYear(venueId);
        } else if (action != null && action.equals("year")) {
            if (venueId.equals("all")) {
                jsonRes = getVenueIncomeYearly();
            } else {
                jsonRes = getVenueIncomeYearly(Integer.parseInt(venueId) );
            }
        } else {
            if (venueId.equals("all")) {
                jsonRes = getVenueIncomeMonthly( Integer.parseInt(year));
            } else {
                jsonRes = getVenueIncomeMonthly(Integer.parseInt(venueId) , Integer.parseInt(year));
            }
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


   
    public JsonArray getVenueIncomeMonthly(int venueId , int year) {
        JsonArray json = new JsonArray("");

        float hourlyRate = getVenueHourlyRate(venueId);

        // monthly in the past 12 months
        for (int i = 1; i <= 12; i++) {
            String sql = "SELECT COUNT(*) FROM booking b " +
                    "JOIN venue_timeslot vt ON b.id = vt.bookingId " +
                    "WHERE vt.venueId = ? AND MONTH(vt.date) = ? AND YEAR(vt.date) = ? AND b.status IN (4,5,7);";
            ArrayList<Object>  params = new ArrayList<>();
            params.add(venueId);
            params.add(i);
            params.add(year);
            ArrayList<Map<String, Object>> rs = dbUtil.findRecord(sql, params);
            int countMonthly = Math.round(Float.parseFloat(rs.get(0).get("COUNT(*)").toString()));

            params.clear();
            rs.clear();

            double totalIncome = hourlyRate * countMonthly;

            JsonObject obj = new JsonObject("");
            obj.add("month", Month.of(i).name());
            obj.add("count", countMonthly);
            obj.add("income", totalIncome);

            json.addJsonObject(obj);
        }

        return json;
    }

    public JsonArray getVenueIncomeMonthly(int year) {
        JsonArray json = new JsonArray("");

        JsonArray allVenue = getAllVenue();
        JsonObject allYear = getAllYear("all");

        int minYear = Integer.parseInt(allYear.get("minYear").toString());
        int maxYear = Integer.parseInt(allYear.get("systemYear").toString());

        ArrayList<JsonArray> allVenueIncome = new ArrayList<>();

        for (int i = 0; i < allVenue.size(); i++) {
            JsonObject venue = (JsonObject) allVenue.get(i);
            int venueId = Integer.parseInt(venue.get("id").toString());
            String venueName = venue.get("name").toString();

            JsonArray venueIncome = getVenueIncomeMonthly(venueId, year);
            allVenueIncome.add(venueIncome);
        }

        // calculate total income for each month
        for (int i = 0; i < 12; i++) {
            double totalIncome = 0;
            for (int j = 0; j < allVenueIncome.size(); j++) {
                JsonObject obj = (JsonObject) allVenueIncome.get(j).get(i);
                totalIncome += Double.parseDouble(obj.get("income").toString());
            }

            JsonObject obj = new JsonObject("" , true);
            obj.add("month", Month.of(i + 1).name());
            obj.add("income", totalIncome);

            json.addJsonObject(obj);
        }

        
        return json;
    }

    public JsonArray getAllVenue() {

        JsonArray json = new JsonArray("allvenue");
        String sql = "SELECT id , name FROM venue";
        ArrayList<Map<String, Object>> rs = dbUtil.findRecord(sql, new ArrayList<>());

        for (int i = 0; i < rs.size(); i++) {
            JsonObject venue = new JsonObject("" , "" , true);
            venue.add("id", rs.get(i).get("id").toString());
            venue.add("name", rs.get(i).get("name").toString());
            json.addJsonObject(venue);
        }
        return json;
    }



    public float getVenueHourlyRate(int venueId) {
        String sql = "SELECT hourlyRate FROM venue WHERE id = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(venueId);
        ArrayList<Map<String, Object>> rs = dbUtil.findRecord(sql, params);
        int hourlyRate = Math.round(Float.parseFloat(rs.get(0).get("hourlyRate").toString()));

        params.clear();
        rs.clear();

        return hourlyRate;
    }

    public JsonObject getAllYear(String venueId ) {
        JsonObject jsonRes = new JsonObject("");
        String sql = "";
        ArrayList<Object>  params = new ArrayList<>();
        // get the least year
        if (! venueId.equals("all")) {
            sql = "SELECT MIN(YEAR(date)) FROM venue_timeslot WHERE venueId = ?;";
            params.add(venueId);
        } else {
            sql = "SELECT MIN(YEAR(date)) FROM venue_timeslot;";
        }


        ArrayList<Map<String, Object>> rs = dbUtil.findRecord(sql, params);
        int minYear = Integer.parseInt(rs.get(0).get("MIN(YEAR(date))").toString());

        params.clear();

        ((JsonObject)jsonRes).add("minYear", minYear);
        ((JsonObject)jsonRes).add("systemYear", Calendar.getInstance().get(Calendar.YEAR));

        return jsonRes;
    }

    public JsonArray getVenueIncomeYearly(int venueId) {

        JsonArray json = new JsonArray("");

        String sql = "SELECT COUNT(*) FROM booking b " +
        "JOIN venue_timeslot vt ON b.id = vt.bookingId " +
        "WHERE vt.venueId = ? AND YEAR(vt.date) = ? AND b.status IN (4,5,7);";

        JsonObject allYearJson = getAllYear(String.valueOf(venueId));

        int minYear = Integer.parseInt(allYearJson.get("minYear").toString());
        int systemYear = Integer.parseInt(allYearJson.get("systemYear").toString());

        double hourlyRate = getVenueHourlyRate(venueId);

        for (int i = minYear; i <= systemYear; i++) {
            ArrayList<Object>  params = new ArrayList<>();
            params.add(venueId);
            params.add(i);
            ArrayList<Map<String, Object>> rs = dbUtil.findRecord(sql, params);
            int countYearly = Math.round(Float.parseFloat(rs.get(0).get("COUNT(*)").toString()));

            params.clear();
            rs.clear();

            JsonObject obj = new JsonObject("" , true);
            obj.add("year", i);
            obj.add("income", countYearly * hourlyRate);
            obj.add("count", countYearly );

            json.addJsonObject(obj);
        }

        return json;
    }

    public JsonArray getVenueIncomeYearly() {

        JsonArray json = new JsonArray("");

        JsonArray allVenue = getAllVenue();
        JsonObject allYear = getAllYear("all");

        int minYear = Integer.parseInt(allYear.get("minYear").toString());
        int maxYear = Integer.parseInt(allYear.get("systemYear").toString());

        ArrayList<JsonArray> allVenueIncome = new ArrayList<>();

        for (int i = 0; i < allVenue.size(); i++) {
            JsonObject venue = (JsonObject) allVenue.get(i);
            int venueId = Integer.parseInt(venue.get("id").toString());
            String venueName = venue.get("name").toString();

            JsonArray venueIncome = getVenueIncomeYearly(venueId);
            allVenueIncome.add(venueIncome);
        }

        // calculate total income for each month
        for (int i = minYear; i <= maxYear; i++) {
            double totalIncome = 0;
            for (int j = 0; j < allVenueIncome.size(); j++) {
                JsonObject obj = (JsonObject) allVenueIncome.get(j).get(i - minYear);
                totalIncome += Double.parseDouble(obj.get("income").toString());
            }

            JsonObject obj = new JsonObject("" , true);
            obj.add("year", i);
            obj.add("income", totalIncome);

            json.addJsonObject(obj);
        }

        return json;

    }


}
