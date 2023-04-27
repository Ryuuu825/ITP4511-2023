package ict.bean.view;

import ict.bean.Timeslot;
import ict.bean.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class MemberAttendanceDTO implements java.io.Serializable {
    
    public HashMap< User , Integer > attendance;

    public MemberAttendanceDTO() {
    }

    public HashMap<User, Integer> getAttendance() {
        return attendance;
    }

    public void setAttendance(HashMap<User, Integer> attendance) {
        this.attendance = attendance;
    }


}
