/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.User;
import ict.bean.Venue;
import ict.bean.view.VenueDTO;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author jyuba
 */
public class VenueDAO extends BaseDAO {

    public VenueDAO(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    public void createTable() {
        String sql
                = "CREATE TABLE IF NOT EXISTS venue ("
                + "id INT(11) NOT NULL AUTO_INCREMENT,"
                + "name varchar(50) NOT NULL,"
                + "district varchar(50) NOT NULL,"
                + "address varchar(255) NOT NULL,"
                + "capacity INT(11) NOT NULL,"
                + "type tinyint(2) NOT NULL,"
                + "img varchar(255) NOT NULL,"
                + "description varchar(255) NULL,"
                + "userId INT(11) DEFAULT NULL,"
                + "hourlyRate DOUBLE NOT NULL,"
                + "enable tinyint(1) NOT NULL DEFAULT 1,"
                + "FOREIGN KEY (userId) REFERENCES user(id) ON DELECT SET NULL,"
                + "PRIMARY KEY (id)"
                + ")";
        dbUtil.executeByPreparedStatement(sql);
    }

    public boolean addRecord(String name, String district, String address, int capacity, int type, String img, String description, int userId, double hourlyRate) {
        boolean isSuccess = false;
        if (queryRecordByName(name) == null) {
            ArrayList<Object> params = new ArrayList<>();
            params.add(name);
            params.add(district);
            params.add(address);
            params.add(capacity);
            params.add(type);
            params.add(img);
            params.add(description);
            params.add(userId);
            params.add(hourlyRate);
            String sql = "INSERT INTO venue (name, district, address, capacity, type, img, description, userId, hourlyRate) VALUES (?,?,?,?,?,?,?,?,?)";
            isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        };
        return isSuccess;
    }

    public Venue queryRecordById(int id) {
        String sql = "SELECT * FROM venue WHERE id=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        Venue v = null;
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            v = new Venue();
            v.setId((int) m.get("id"));
            v.setName((String) m.get("name"));
            v.setDistrict((String) m.get("district"));
            v.setAddress((String) m.get("address"));
            v.setCapacity((int) m.get("capacity"));
            v.setType((int) m.get("type"));
            v.setImg((String) m.get("img"));
            v.setDescription((String) m.get("description"));
            v.setUserId((int) m.get("userId"));
            v.setHourlyRate((double) m.get("hourlyRate"));
            v.setEnable((int) m.get("enable") > 0);
        }
        return v;
    }

    public ArrayList<VenueDTO> queryRecordToDTO() {
        String sql = "SELECT * FROM venue";
        ArrayList<Object> params = new ArrayList<>();
        Venue v = null;
        VenueDTO vdto = null;
        UserDAO udao = new UserDAO(dbUrl, dbUser, dbPassword);
        ArrayList<VenueDTO> vdtos = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        if (ls.size() != 0) {
            for (Map<String, Object> m : ls) {
                User u = null;
                v = new Venue();
                vdto = new VenueDTO();
                v.setId((int) m.get("id"));
                v.setName((String) m.get("name"));
                v.setDistrict((String) m.get("district"));
                v.setAddress((String) m.get("address"));
                v.setCapacity((int) m.get("capacity"));
                v.setType((int) m.get("type"));
                v.setImg((String) m.get("img"));
                v.setDescription((String) m.get("description"));
                v.setHourlyRate((double) m.get("hourlyRate"));
                v.setEnable((int) m.get("enable") > 0);
                if (m.get("userId") != null) {
                    v.setUserId((int) m.get("userId"));
                    u = udao.queryRecordById((int) m.get("userId"));
                }
                vdto.setUser(u);
                vdto.setVenue(v);
                vdtos.add(vdto);
            }
        }
        return vdtos;
    }

    public VenueDTO queryRecordToDTOById(int id) {
        String sql = "SELECT * FROM venue WHERE id = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        Venue v = null;
        VenueDTO vdto = null;
        UserDAO udao = new UserDAO(dbUrl, dbUser, dbPassword);
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        if (ls.size() != 0) {
            Map<String, Object> m = ls.get(0);
            User u = null;
            v = new Venue();
            vdto = new VenueDTO();
            v.setId((int) m.get("id"));
            v.setName((String) m.get("name"));
            v.setDistrict((String) m.get("district"));
            v.setAddress((String) m.get("address"));
            v.setCapacity((int) m.get("capacity"));
            v.setType((int) m.get("type"));
            v.setImg((String) m.get("img"));
            v.setDescription((String) m.get("description"));
            v.setHourlyRate((double) m.get("hourlyRate"));
            v.setEnable((int) m.get("enable") > 0);
            if (m.get("userId") != null) {
                v.setUserId((int) m.get("userId"));
                u = udao.queryRecordById((int) m.get("userId"));
            }
            vdto.setUser(u);
            vdto.setVenue(v);
        }
        return vdto;
    }

    public ArrayList<VenueDTO> queryRecordToDTOByKeyword(String keyword) {
        String sql = "SELECT v.id as venueId, v.*, u.*, u.id as userId "
                + "FROM Venue v "
                + "JOIN User u ON v.userId = u.id "
                + "Where v.id = ? "
                + "or v.district LIKE ? "
                + "or v.address LIKE ? "
                + "or v.name LIKE ? "
                + "or u.firstName LIKE ? "
                + "or u.lastName LIKE ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(keyword);
        params.add("%" + keyword + "%");
        params.add("%" + keyword + "%");
        params.add("%" + keyword + "%");
        params.add("%" + keyword + "%");
        params.add("%" + keyword + "%");
        Venue v = null;
        VenueDTO vdto = null;
        ArrayList<VenueDTO> vdtos = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        UserDAO udao = new UserDAO(dbUrl, dbUser, dbPassword);
        if (ls.size() != 0) {
            for (Map<String, Object> m : ls) {
                User u = null;
                v = new Venue();
                vdto = new VenueDTO();
                v.setId((int) m.get("id"));
                v.setName((String) m.get("name"));
                v.setDistrict((String) m.get("district"));
                v.setAddress((String) m.get("address"));
                v.setCapacity((int) m.get("capacity"));
                v.setType((int) m.get("type"));
                v.setImg((String) m.get("img"));
                v.setDescription((String) m.get("description"));
                v.setHourlyRate((double) m.get("hourlyRate"));
                v.setEnable((int) m.get("enable") > 0);
                if (m.get("userId") != null) {
                    v.setUserId((int) m.get("userId"));
                    u = udao.queryRecordById((int) m.get("userId"));
                }
                vdto.setUser(u);
                vdto.setVenue(v);
                vdtos.add(vdto);
            }
        }
        return vdtos;
    }

    public ArrayList<Venue> queryRecordByName(String name) {
        String sql = "SELECT * FROM venue WHERE name LIKE ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add("%" + name + "%");
        ArrayList<Venue> vs = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            Venue v = new Venue();
            v.setId((int) m.get("id"));
            v.setName((String) m.get("name"));
            v.setDistrict((String) m.get("district"));
            v.setAddress((String) m.get("address"));
            v.setCapacity((int) m.get("capacity"));
            v.setType((int) m.get("type"));
            v.setImg((String) m.get("img"));
            v.setDescription((String) m.get("description"));
            v.setUserId((int) m.get("userId"));
            v.setHourlyRate((double) m.get("hourlyRate"));
            v.setEnable((int) m.get("enable") > 0);
            vs.add(v);
        }
        return vs;
    }
    
    public ArrayList<Venue> queryRecordByKeyword(String keyword) {
        String sql = "SELECT * FROM venue WHERE name LIKE ? or location LIKE ? or address LIKE ? or type LIKE ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add("%" + keyword + "%");
        params.add("%" + keyword + "%");
        params.add("%" + keyword + "%");
        params.add("%" + keyword + "%");
        ArrayList<Venue> vs = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            Venue v = new Venue();
            v.setId((int) m.get("id"));
            v.setName((String) m.get("name"));
            v.setDistrict((String) m.get("district"));
            v.setAddress((String) m.get("address"));
            v.setCapacity((int) m.get("capacity"));
            v.setType((int) m.get("type"));
            v.setImg((String) m.get("img"));
            v.setDescription((String) m.get("description"));
            v.setUserId((int) m.get("userId"));
            v.setHourlyRate((double) m.get("hourlyRate"));
            v.setEnable((int) m.get("enable") > 0);
            vs.add(v);
        }
        return vs;
    }

    public ArrayList<Venue> queryRecordByUserId(int userId) {
        String sql = "SELECT * FROM venue WHERE userId = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(userId);
        Venue v = null;
        ArrayList<Venue> vs = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            v = new Venue();
            v.setId((int) m.get("id"));
            v.setName((String) m.get("name"));
            v.setDistrict((String) m.get("district"));
            v.setAddress((String) m.get("address"));
            v.setCapacity((int) m.get("capacity"));
            v.setType((int) m.get("type"));
            v.setImg((String) m.get("img"));
            v.setDescription((String) m.get("description"));
            v.setUserId((int) m.get("userId"));
            v.setHourlyRate((double) m.get("hourlyRate"));
            v.setEnable((int) m.get("enable") > 0);
            vs.add(v);
        }
        return vs;
    }

    public ArrayList<Venue> queryRecord() {
        String sql = "SELECT * FROM venue";
        ArrayList<Object> params = new ArrayList<>();
        Venue v = null;
        ArrayList<Venue> vs = new ArrayList<>();
        ArrayList<Map<String, Object>> ls = dbUtil.findRecord(sql, params);
        for (Map<String, Object> m : ls) {
            v = new Venue();
            v.setId((int) m.get("id"));
            v.setName((String) m.get("name"));
            v.setDistrict((String) m.get("district"));
            v.setAddress((String) m.get("address"));
            v.setCapacity((int) m.get("capacity"));
            v.setType((int) m.get("type"));
            v.setImg((String) m.get("img"));
            v.setDescription((String) m.get("description"));
            v.setUserId((int) m.get("userId"));
            v.setHourlyRate((double) m.get("hourlyRate"));
            v.setEnable((int) m.get("enable") > 0);
            vs.add(v);
        }
        return vs;
    }

    public boolean delRecord(int id) {
        String sql = "DELETE FROM venue WHERE id=?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        boolean isSuccess = false;
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public boolean editRecord(Venue v) {
        String sql = "UPDATE venue "
                + "SET name = ?, district = ?, address = ?, capacity = ?, type = ?, img = ?, description = ?, userId = null, hourlyRate = ? "
                + "WHERE id = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(v.getName());
        params.add(v.getDistrict());
        params.add(v.getAddress());
        params.add(v.getCapacity());
        params.add(v.getType());
        params.add(v.getImg());
        params.add(v.getDescription());
        if (v.getUserId() != 0) {
            sql = "UPDATE venue "
                    + "SET name = ?, district = ?, address = ?, capacity = ?, type = ?, img = ?, description = ?, userId = ?, hourlyRate = ?, enable = ? "
                    + "WHERE id = ?";
            params.add(v.getUserId());
        }
        params.add(v.getHourlyRate());
        params.add(v.getEnable() == true ? 1 : 0);
        params.add(v.getId());
        boolean isSuccess = false;
        isSuccess = dbUtil.updateByPreparedStatement(sql, params);
        return isSuccess;
    }

    public void dropTable() {
        String sql = "DROP TABLE venue";
        dbUtil.executeByPreparedStatement(sql);
    }
}
