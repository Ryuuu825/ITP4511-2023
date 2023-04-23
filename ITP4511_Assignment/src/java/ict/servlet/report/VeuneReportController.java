package ict.servlet.report;

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

import ict.bean.Booking;
import ict.bean.Venue;
import ict.bean.view.BookingDTO;
import ict.bean.view.BookingReportDTO;
import ict.db.BookingDAO;
import ict.db.VenueDAO;
import ict.util.CsvUtil;
import ict.util.DbUtil;
import ict.util.JsonArray;
import ict.util.JsonObject;
import ict.util.JsonUtil;

@WebServlet(name = "VeuneReportController", urlPatterns = { "/api/report/venue" })
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

        String venueId = req.getParameter("venue");
        String action = req.getParameter("export"); // csv or json ( not implemented yet )
        ArrayList<BookingDTO> bdtos;

        if (venueId == null || venueId.equals("all")) {
            bdtos = bookingDB.queryRecordToDTO();
        } else {
            bdtos = bookingDB.queryRecordByVenueId(Integer.parseInt(venueId));
            // get the venue name
            Venue venue = venueDB.queryRecordById(Integer.parseInt(venueId));
            req.setAttribute("venueName", venue.getName());
        }

        if (action == null) {
            String redirectBackTo = getServletContext().getContextPath() + "/admin/reports/report_venue.jsp";
            RequestDispatcher rd;
            req.setAttribute("bookingDTOs", bdtos);
            rd = getServletContext().getRequestDispatcher("/admin/report.jsp?report=venue");
            rd.forward(req, resp);
        } else if (action.equals("true")) {

            CsvUtil<BookingReportDTO> csvUtil = new CsvUtil<>(BookingReportDTO.class);
            for (BookingDTO b : bdtos) {
                BookingReportDTO br = new BookingReportDTO(
                        b.getBooking().getId(), 
                        b.getMember().getFirstName() + " " + b.getMember().getLastName(),
                        b.getVenueTimeslotses().size(), 
                        b.getBooking().getAmount(), 
                        b.getBooking().getCreateDate(),
                        b.getBooking().getStatusString()
                    );
                csvUtil.add(br);
            }

            csvUtil.setHeader(resp);
            resp.getWriter().write(csvUtil.getCSV());

        } else {
            resp.setStatus(400);
            resp.getWriter().write("Invalid action");
        }
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

}
