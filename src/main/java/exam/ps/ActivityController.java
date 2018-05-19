package exam.ps;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
import java.util.ArrayList;

import static exam.ps.EmployeeController.selectAllEmployees;
import static exam.ps.MemberController.selectMembers;

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
        insertActivity(activity);
        return "redirect:/visAktivitet";
    }


    @GetMapping("/tilføjMedAkt")
    public String tilføjMedAkt(@RequestParam(value = "id[]", defaultValue = "0") int urlID, Model model) {
        model.addAttribute("employeeArrayList", selectAllEmployees());
        activityID = urlID;
        return ("tilføjMedAkt");
    }
    @PostMapping("/tilføjMedAkt")
    public String vælgmedarbejdere (@RequestParam(value = "id[]") int[] ids) {

        for (int i = 0;  i<ids.length ; i++) {
            int id=ids[i];
            System.out.println(id);
            addEmployeeToActivty(id, activityID);
        }


        return "redirect:/visAktivitet";
    }

    @GetMapping("/tilføjMemAkt")
    public String vælgmedlem(@RequestParam(value = "id") int urlID, Model model) {
        model.addAttribute("memberArray",selectMembers());
        activityID = urlID;
        return ("tilføjMemAkt");
    }


    @PostMapping("/tilføjMemAkt")
    public String vælgmedlem (@RequestParam(value="id") int[] ids) {

        for (int i = 0; i < ids.length; i++) {
            int id = ids[i];
            System.out.println(id);
            addMemberToActivity(id,activityID);

        }
         return "redirect:visAktivitet";
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
    @GetMapping("/sletAktivitet")
    public String deleteActivity(@RequestParam(value = "id", defaultValue = "0") int id, Activity activity)  {
        deleteActivity(activity);
        return "redirect:/visAktivitet";
    }
    @PostMapping("/redigerAktivitet")
    public String editActivity(Activity activity) {
        updateActivity(activity);
        return "redirect:/visAktivitet";



        }





    public ArrayList<Activity> selectAllActivities(){
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        Statement s = null;
        ArrayList<Activity> allActivities = new ArrayList<>();
        try{
            s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM activities");
            while(rs.next()){
                try{
                    Activity activity = new Activity();
                    activity.setId(rs.getInt("activity_id"));
                    activity.setName(rs.getString("activity_name"));
                    activity.setDescription(rs.getString("activity_description"));
                    activity.setStartDate(rs.getTimestamp("activity_startDate"));
                    activity.setStartTime(rs.getTime("activity_startTime").toLocalTime());
                    activity.setEndDate(rs.getTimestamp("activity_endDate"));
                    activity.setEndTime(rs.getTime("activity_endTime").toLocalTime());
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



    public void updateActivity(Activity activity) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement("UPDATE activities SET activity_name = ?, activity_description = ?, activity_startDate = ?, activity_endDate = ?,activity_startTime = ?, activity_endtime = ? WHERE activity_id = ?");
            ps.setString(1, activity.getName());
            ps.setString(2, activity.getDescription());
            ps.setDate(3, new java.sql.Date(activity.getStartDate().getTime()));
            ps.setDate(4, new java.sql.Date(activity.getEndDate().getTime()));
            Time startTime = Time.valueOf(activity.getStartTime());
            ps.setTime(5, startTime);
            Time endTime = Time.valueOf(activity.getEndTime());
            ps.setTime(6, endTime);
            ps.setInt(7, activityID);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertActivity(Activity activity) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO activities (activity_name, activity_description, activity_startDate, activity_endDate, activity_startTime,  activity_endTime) VALUES(?, ?, ?, ?,?,?)");
            ps.setString(1, activity.getName());
            ps.setString(2, activity.getDescription());
            ps.setDate(3, new java.sql.Date(activity.getStartDate().getTime()));
            ps.setDate(4, new java.sql.Date(activity.getEndDate().getTime()));
            Time startTime = Time.valueOf(activity.getStartTime());
            ps.setTime(5, startTime);
            Time endTime = Time.valueOf(activity.getEndTime());
            ps.setTime(6, endTime);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addEmployeeToActivty(int employeeId, int activityId) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("INSERT INTO organizers (employees_employee_id, activities_activity_id) VALUES(?, ?)");
            ps.setInt(1, employeeId);
            ps.setInt(2, activityId);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void addMemberToActivity(int memberId, int activityID) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("INSERT INTO participants (members_member_id,activities_activity_id) VALUES(?,?)");
            ps.setInt(1,memberId);
            ps.setInt(2,activityID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
