package ict.test;

import java.time.LocalDate;
import java.util.ArrayList;

import ict.bean.VenueTimeslot;
import ict.db.VenueTimeslotDAO;
import ict.util.DbUtil;

public class GenerateVenueTimeslot {

    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_ASM_DB";
        String dbUser = "root";
        String dbPassword = "";
        DbUtil dbUtil = new DbUtil(dbUrl, dbUser, dbPassword);
        VenueTimeslotDAO vtsDB = new VenueTimeslotDAO(dbUrl, dbUser, dbPassword);
        LocalDate date = vtsDB.queryMaxDate();

        String sql = "INSERT INTO `venue_timeslot` (`id`, `venueId`, `timeslotId`, `bookingId`, `date`) VALUES "
                + "(NULL, '1', '1', NULL, ?)";
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 12; j++) {
                sql = "INSERT INTO `venue_timeslot` (`id`, `venueId`, `timeslotId`, `bookingId`, `date`) VALUES "
                        + "(NULL, ?, ?, NULL, ?)";
                ArrayList<Object> params = new ArrayList<>();
                params.add(i);
                params.add(j);
                date = date.plusDays(1);
                params.add(date);
                dbUtil.updateByPreparedStatement(sql, params);
            }
        }
    }   
}
