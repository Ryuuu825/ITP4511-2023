package ict.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ict.bean.Booking;
import ict.bean.Venue;
import ict.bean.VenueTimeslot;
import ict.db.BookingDAO;
import ict.db.VenueDAO;
import ict.db.VenueTimeslotDAO;
import ict.util.DbUtil;


// generate record that are from 2018-01-01 to 2023-04-xx ( today ) ( not all )
public class GenerateUpcomingBooking {

    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_ASM_DB?tinyInt1isBit=false";
        String dbUser = "root";
        String dbPassword = "";
        DbUtil dbUtil = new DbUtil(dbUrl, dbUser, dbPassword);
        VenueTimeslotDAO vtsDB = new VenueTimeslotDAO(dbUrl, dbUser, dbPassword);
        BookingDAO bookingDB = new BookingDAO(dbUrl, dbUser, dbPassword);
        VenueDAO venueDB = new VenueDAO(dbUrl, dbUser, dbPassword);


        int[] users = { 3  };
        int[] status = { 3 };

        LocalDate today = LocalDate.now();

        String sql;

        for ( int i = 0 ; i < 4 ; i++ ) {
            sql = "SELECT id FROM venue_timeslot WHERE date = DATE_ADD( CURDATE() , INTERVAL " + ( i + 1 ) + " DAY )";
            ArrayList<Map<String, Object>> res = dbUtil.findRecord(sql, new ArrayList<>());
            int id  = (int) res.get(0).get("id");

            sql = "INSERT INTO booking ( userId , amount , createDate , status ) VALUES ( ? , ? , ? , ? )";
            ArrayList<Object> param = new ArrayList<>();
            param.add( 3 );
            param.add( 1000 );
            param.add( today.plusDays( i + 1 ) );
            param.add( status[0] );
            dbUtil.updateByPreparedStatement(sql, param);

            sql = "UPDATE venue_timeslot SET bookingId = ? WHERE id = ?";
            param = new ArrayList<>();
            param.add(40 + i);
            param.add(id);
            dbUtil.updateByPreparedStatement(sql, param);

        }


       
    }   
}
