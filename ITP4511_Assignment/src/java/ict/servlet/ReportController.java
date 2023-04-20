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
import ict.db.VenueDAO;
import ict.util.DbUtil;
import ict.util.JsonObject;
import ict.util.JsonUtil;

@WebServlet(name = "ReportController", urlPatterns = {"/api/report"})
public class ReportController extends HttpServlet {


    private DbUtil dbUtil;

    private JsonUtil jsonUtil;

    private VenueDAO venueDB;

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
        String action = req.getParameter("action");


        if (action != null && action.equals("usercount")) {
            jsonUtil.clear();
            jsonUtil.addJsonObject(getUserVisited());
            setHttpHeader(resp);
            resp.getWriter().write(jsonUtil.getJsonString());
            return;
        }

        // get all the venue
        ArrayList<Venue> venues = venueDB.queryRecord();
        String sql;
        ArrayList<Object> params;
        ArrayList<Map<String, Object>> rs;

        boolean dbAvailable = dbUtil.testConnectionWithDB();
        if (!dbAvailable) {
            resp.setStatus(500);
            resp.getWriter().write("Database is not available");
            return;
        }

        
        jsonUtil.clear();
 
        for(Venue venue : venues) {

            JsonObject json = new JsonObject(venue.getName());

            // get the number of bookings foreach venue [monthly, yearly, total]

            // total [ card ]
            json.add("totalbookings" , this.countTotalBookings(venue.getId()));

            
            // weekly ( recent 14 weeks )
            json.add(this.countRecent14WeeksBookings(venue.getId()));


            // get the occupancy rate foreach venue [weekly, monthly, yearly] . This is the number of bookings / total number of timeslots
            json.add(this.countRecent14WeeksOccupancyRate(venue.getId()));

            json.add(this.countRecent12MonthsOccupancyRate(venue.getId()));

            // // get total venue income, foreach venue [weekly, monthly, yearly]
            json.add("Total Income" , this.getTotalVenueIncome(venue.getId()));
            json.add( this.getVenueIncomeMonthly( venue.getId() ) );
            json.add( this.getVenueIncomeWeekly( venue.getId() ) );

            jsonUtil.addJsonObject(json);
            
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonUtil.getJsonString());
    }

    @Override
    public void init() throws ServletException {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");

        dbUtil = new DbUtil(dbUrl, dbUser, dbPassword);
        jsonUtil = new JsonUtil();
        venueDB = new VenueDAO(dbUrl, dbUser, dbPassword);
    }

    // return data in json format for the frond-end to plot some charts
    // for example: 
    // the number of bookings foreach venue [monthly, yearly, total]
    // the occupancy rate foreach venue [weekly, monthly, yearly]
    // total venue income, foreach venue [weekly, monthly, yearly]

    public int countTotalBookings(int venueId) {
        JsonObject json = new JsonObject("totalbooking");


        String sql = "SELECT COUNT(*) FROM venue_timeslot WHERE venueId = ? AND BookingId IS NOT NULL";
        ArrayList<Object> params = new ArrayList<>();
        params.add(venueId);
        ArrayList<Map<String, Object>> rs = dbUtil.findRecord(sql, params);
        int count = Math.round(Float.parseFloat(rs.get(0).get("COUNT(*)").toString()));

        params.clear();
        rs.clear();

        return count;
    }

    public JsonObject countRecent14WeeksBookings(int venueId) {
        int countMonthly = 0;
        JsonObject jsonMonthly = new JsonObject("recent 14 weeks bookings");

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

    public JsonObject countRecent14WeeksOccupancyRate(int venueId) {

        JsonObject jsonMonthlyOccupancy = new JsonObject("recent 14 weeks occupancy rate");

        // monthly in the past 12 months
        for (int i = 0; i <= 11; i++) {
            String sql = "SELECT COUNT(*) FROM venue_timeslot WHERE venueId = ? AND WEEK(date) = WEEK(CURRENT_DATE()) - ?;";
            ArrayList<Object>  params = new ArrayList<>();
            params.add(venueId);
            params.add(i);
            ArrayList<Map<String, Object>> rs = dbUtil.findRecord(sql, params);
            float countMonthlyO = Float.parseFloat(rs.get(0).get("COUNT(*)").toString());

            params.clear();
            rs.clear();

            sql = "SELECT COUNT(*) FROM venue_timeslot WHERE venueId = ? AND WEEK(date) = WEEK(CURRENT_DATE()) - ? AND BookingId IS NOT NULL;";
            params = new ArrayList<>();
            params.add(venueId);
            params.add(i);
            rs = dbUtil.findRecord(sql, params);
            float countMonthlyBooked = Float.parseFloat(rs.get(0).get("COUNT(*)").toString());

            params.clear();
            rs.clear();

            double occupancyMonthly = 0;
            if (countMonthlyO != 0) {
                occupancyMonthly = countMonthlyBooked / countMonthlyO * 100;
            }

            jsonMonthlyOccupancy.add(Integer.toString(i), occupancyMonthly );

        }

        return jsonMonthlyOccupancy;

    }

    public JsonObject countRecent12MonthsOccupancyRate(int venueId) {
            
        JsonObject jsonMonthlyOccupancy = new JsonObject("recent 12 months occupancy rate");

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
                occupancyMonthly = countMonthlyBooked / countMonthlyO * 100;
            }

            jsonMonthlyOccupancy.add(Integer.toString(i), occupancyMonthly );

        }

        return jsonMonthlyOccupancy;
    
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


    public double getTotalVenueIncome(int venueId) {
        JsonObject json = new JsonObject("totalvenueincome");


        float hourlyRate = getVenueHourlyRate(venueId);

        String sql = "SELECT COUNT(*) FROM venue_timeslot WHERE venueId = ? AND BookingId IS NOT NULL";
        ArrayList<Object> params = new ArrayList<>();
        params.add(venueId);
        ArrayList<Map<String, Object>> rs = dbUtil.findRecord(sql, params);
        int count = Math.round(Float.parseFloat(rs.get(0).get("COUNT(*)").toString()));

        params.clear();
        rs.clear();

        double totalIncome = hourlyRate * count;

        return  totalIncome;
    }

    public JsonObject getVenueIncomeMonthly(int venueId) {
        JsonObject json = new JsonObject("Total Income (weekly)");

        float hourlyRate = getVenueHourlyRate(venueId);

        // monthly in the past 12 months
        for (int i = 0; i <= 11; i++) {
            String sql = "SELECT COUNT(*) FROM venue_timeslot WHERE venueId = ? AND MONTH(date) = MONTH(CURRENT_DATE()) - ? AND BookingId IS NOT NULL;";
            ArrayList<Object>  params = new ArrayList<>();
            params.add(venueId);
            params.add(i);
            ArrayList<Map<String, Object>> rs = dbUtil.findRecord(sql, params);
            int countMonthly = Math.round(Float.parseFloat(rs.get(0).get("COUNT(*)").toString()));

            params.clear();
            rs.clear();

            double totalIncome = hourlyRate * countMonthly;

            json.add(Integer.toString(i), totalIncome);

        }

        return json;
    }

    public JsonObject getVenueIncomeWeekly(int venueId) {
        JsonObject json = new JsonObject("Total Income (weekly)");

        float hourlyRate = getVenueHourlyRate(venueId);

        // weekly in the past 12 months
        for (int i = 0; i <= 11; i++) {
            String sql = "SELECT COUNT(*) FROM venue_timeslot WHERE venueId = ? AND WEEK(date) = WEEK(CURRENT_DATE()) - ? AND BookingId IS NOT NULL;";
            ArrayList<Object>  params = new ArrayList<>();
            params.add(venueId);
            params.add(i);
            ArrayList<Map<String, Object>> rs = dbUtil.findRecord(sql, params);
            int countMonthly = Math.round(Float.parseFloat(rs.get(0).get("COUNT(*)").toString()));

            params.clear();
            rs.clear();

            double totalIncome = hourlyRate * countMonthly;

            json.add(Integer.toString(i), totalIncome);

        }

        return json;
    }

    public JsonObject getUserVisited() {

        //if ( application.getAttribute("uservisited") == null ) {
        //     application.setAttribute("uservisited", 1);
        // } else {
        //     int count = java.lang.Integer.parseInt(application.getAttribute("uservisited").toString());
        //     application.setAttribute("uservisited", count + 1);
        // }
        try {
            int count = Integer.parseInt( getServletContext().getAttribute("uservisited").toString() );
        } catch (Exception e) {
            getServletContext().setAttribute("uservisited", 1);
        }
        JsonObject json = new JsonObject("uservisited" ,  getServletContext().getAttribute("uservisited").toString() , true);

        return json;
    }






}
