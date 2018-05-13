package exam.ps;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;

@Controller
public class ActivityController {
    int activityID = 0;

    @GetMapping("/visAktivitet")
    public String ShowActivities(Model model) {
        model.addAttribute("activityArrayList", selectAllActivities());
        return "visAktivitet";
    }

    @GetMapping("/tilføjAktivitet")
    public String createActivity(Model model) {
        model.addAttribute("activity", new Activity());
        return "tilføjAktivitet";
    }

    @PostMapping("/tilføjAktivitet")
    public String createActivity(@ModelAttribute Activity activity) {
        dbConn db = dbConn.getInstance();
        insertActivity(activity);
        return "redirect:/visAktivitet";
    }

    public void insertActivity(Activity activity) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO activities (activity_name, activity_description, activity_startDatetime, activity_endDatetime) VALUES(?, ?, ?, ?)");
            ps.setString(1, activity.getName());
            ps.setString(2, activity.getDescription());
            ps.setDate(3, new java.sql.Date(activity.getStartDateTime().getTime()));
            ps.setDate(4, new java.sql.Date(activity.getEndDateTime().getTime()));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/redigerAktivitet")
    public String editActivity(@RequestParam(value = "id", defaultValue = "1") int id, Model model) {
        for(Activity activity:selectAllActivities()) {
            if(activity.getId() == id)
                model.addAttribute("activity", activity);
        }
        activityID = id;
        return "redigerAktivitet";
    }

    public ArrayList<Activity> selectAllActivities(){
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        Statement s = null;
        ArrayList<Activity> allActivities = new ArrayList<>();
        try{
            s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT *  FROM activities ");
            while(rs.next()){
                try{
                    Activity activity = new Activity();
                    activity.setId(rs.getInt("activity_id"));
                    activity.setName(rs.getString("activity_name"));
                    activity.setDescription(rs.getString("activity_description"));
                    activity.setStartDateTime(rs.getTime("activity_startDatetime"));
                    activity.setEndDateTime(rs.getTime("activity_endDatetime"));
                    allActivities.add(activity);
                } catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return allActivities;
    }

    @PostMapping("/redigerAktivitet")
    public String editActivity(Activity activity) {
        updateActivity(activity);
        return "redirect:/visAktivitet";
    }

    public void updateActivity(Activity activity) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement("UPDATE activities SET activity_name = ?, activity_description = ?, activity_startDatetime = ?, activity_endDatetime = ? WHERE activity_id = ? ");
            ps.setInt(4, activityID);
            ps.setString(1, activity.getName());
            ps.setString(2, activity.getDescription());
            ps.setDate(3, new java.sql.Date(activity.getStartDateTime().getTime()));
            ps.setDate(4, new java.sql.Date(activity.getEndDateTime().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/sletAktivitet")
    public String deleteActivity(@RequestParam(value = "id", defaultValue = "0") int id, Activity activity) throws FileNotFoundException {
        deleteActivity(activity);
        return "redirect:/visAktivitet";
    }

    public void deleteActivity(Activity activity) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM activities WHERE activity_id = ?");
            ps.setInt(1, activity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
