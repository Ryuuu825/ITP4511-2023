package ict.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import ict.bean.Booking;
import ict.bean.Venue;
import ict.bean.VenueTimeslot;
import ict.db.BookingDAO;
import ict.db.VenueDAO;
import ict.db.VenueTimeslotDAO;
import ict.util.DbUtil;


// generate record that are from 2018-01-01 to 2023-04-xx ( today ) ( not all )
public class GenerateRecord {

    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_ASM_DB?tinyInt1isBit=false";
        String dbUser = "root";
        String dbPassword = "";
        DbUtil dbUtil = new DbUtil(dbUrl, dbUser, dbPassword);
        VenueTimeslotDAO vtsDB = new VenueTimeslotDAO(dbUrl, dbUser, dbPassword);
        BookingDAO bookingDB = new BookingDAO(dbUrl, dbUser, dbPassword);
        VenueDAO venueDB = new VenueDAO(dbUrl, dbUser, dbPassword);

        Random random = new Random();

        int[] users = { 3 ,5 ,7, 9 };
        int[] status = { 2 , 5 , 6 , 7};

        // generate around 100 date ans sort it 
        ArrayList<LocalDate> dates = new ArrayList<>();

        LocalDate date = LocalDate.of(2018, 1, 1);
        LocalDate today = LocalDate.now();
        while (date.isBefore(today)) {
            dates.add(date);
            date = date.plusDays( random.nextInt(10) + 1 );
        }

        // generate around 100 ( 30 have booking , 70 not have booking ) venueTimeslot
        ArrayList<VenueTimeslot> venueTimeslots = new ArrayList<>();
        ArrayList<VenueTimeslot> venueTimeslots1 = new ArrayList<>();

        HashMap<Integer, ArrayList<VenueTimeslot>> venueTimeslotsMap = new HashMap<>();

        for (int i = 1; i < 100; i++) {
            VenueTimeslot venueTimeslot = new VenueTimeslot();
            venueTimeslot.setVenueId( random.nextInt(5) + 1 );
            venueTimeslot.setTimeslotId( random.nextInt(12) + 1 );
            venueTimeslot.setDate( dates.get( i - 1 ) );
            venueTimeslots.add(venueTimeslot);
            venueTimeslots1.add(venueTimeslot);
        }

        // generate around 30 booking ( start from 10 )
        ArrayList<Booking> bookings = new ArrayList<>();
        for (int i = 10; i <= 40; i++) {
            Booking booking = new Booking();
            booking.setId(i);
            booking.setUserId( users[ random.nextInt(4) ] );
            booking.setStatus( status[ random.nextInt(4) ] );

            // get associated venueTimeslot
            int index = random.nextInt(venueTimeslots1.size() - 1);
            VenueTimeslot venueTimeslot = venueTimeslots1.get(index);
            // get the venue 
            int venueId = venueTimeslot.getVenueId();
            // get the hourlyRate from the 
            Venue v = venueDB.queryRecordById(venueId);
            double hourlyRate = v.getHourlyRate();
            // default booked one session, and one venue only
            booking.setAmount(hourlyRate);
            LocalDate d = venueTimeslot.getDate();
            // randomly plus some days
            d = d.plusDays( random.nextInt(10) + 1 );
            booking.setCreateDate(d);

            // add to bookings
            bookings.add(booking);

            // add to venueTimeslotsMap
            if (venueTimeslotsMap.containsKey(venueId)) {
                ArrayList<VenueTimeslot> venueTimeslots2 = venueTimeslotsMap.get(venueId);
                venueTimeslots2.add(venueTimeslot);
                venueTimeslotsMap.put(venueId, venueTimeslots2);
            } else {
                ArrayList<VenueTimeslot> venueTimeslots2 = new ArrayList<>();
                venueTimeslots2.add(venueTimeslot);
                venueTimeslotsMap.put(venueId, venueTimeslots2);
            }

            // remove venueTimeslot from ve nueTimeslots
            venueTimeslots1.remove(index);

        }

        // insert into booking
        for (Booking booking : bookings) {
            ArrayList<Object> params = new ArrayList<>();
            params.add(booking.getUserId());
            params.add(booking.getAmount());
            params.add(booking.getStatus());
            params.add(booking.getCreateDate());
            String sql = "INSERT INTO booking (userId, amount, status, createDate) VALUES (?,?,?,?)";
            dbUtil.updateByPreparedStatement(sql, params);
        }


        // insert into venueTimeslot
        for (VenueTimeslot venueTimeslot : venueTimeslots) {
            int venueId = venueTimeslot.getVenueId();
            LocalDate d = venueTimeslot.getDate();
            if (venueTimeslotsMap.containsKey(venueId)) {
                ArrayList<VenueTimeslot> venueTimeslots2 = venueTimeslotsMap.get(venueId);
                boolean flag = false;
                for (VenueTimeslot venueTimeslot2 : venueTimeslots2) {
                    if (venueTimeslot2.getDate().equals(d)) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    venueTimeslot.setBookingId( random.nextInt(30) + 10 );
                }
            }
            vtsDB.addRecord(venueId, venueTimeslot.getTimeslotId(), venueTimeslot.getBookingId() , venueTimeslot.getDate().toString());
        }
    }   
}
