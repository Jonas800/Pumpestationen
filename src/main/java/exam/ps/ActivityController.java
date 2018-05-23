package exam.ps;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;

import static exam.ps.EmployeeController.selectAllEmployees;
import static exam.ps.MemberController.selectMembers;

@Controller
public class ActivityController {
    int activityID = 0;

    @GetMapping("/visAktivitet")
    public String ShowActivities(Model model, HttpServletRequest request) {
        if (commonMethods.isSessionInvalid(request)) {
            return "redirect:/login";
        }
        model.addAttribute("activityArrayList", selectAllActivities());
        return "visAktivitet";
    }

    @GetMapping("/tilføjAktivitet")
    public String createActivity(Model model, HttpServletRequest request) {
        if (commonMethods.isSessionInvalid(request)) {
            return "redirect:/login";
        }
        model.addAttribute("activity", new Activity());
        return "tilføjAktivitet";
    }

    @PostMapping("/tilføjAktivitet")
    public String createActivity(@ModelAttribute Activity activity) {
        insertActivity(activity);
        return "redirect:/visAktivitet";
    }


    @GetMapping("/tilføjMedarbejderTilAktivitet")
    public String tilføjMedAkt(@RequestParam(value = "id", defaultValue = "0") int urlID, Model model, HttpServletRequest request) {
        if (commonMethods.isSessionInvalid(request)) {
            return "redirect:/login";
        }
        activityID = urlID; //Vi erhverver os en værdi fra hjemmesiden, som så bliver gemt i variablen UrlID, som vi sætter sim activityID
        model.addAttribute("employeeArrayList", selectAllEmployees()); //
        model.addAttribute("fkEmployeeId", getOrganizers(activityID));
        return ("tilføjMedarbejderTilAktivitet");
    }

    @PostMapping("/tilføjMedarbejderTilAktivitet")
    public String vælgmedarbejdere(@RequestParam(value = "employeeId[]", required = false) int[] employeeIds) {
        if (employeeIds != null) {
            addEmployeesToActivity(employeeIds, activityID);
        } else {
            deleteParticipantsOrOrganizers(activityID, "organizers");
        }
        return "redirect:/visAktivitet";
    }

    @GetMapping("/tilføjMedlemTilAktivitet")
    public String vælgmedlem(@RequestParam(value = "id") int urlID, Model model, HttpServletRequest request) {
        if (commonMethods.isSessionInvalid(request)) {
            return "redirect:/login";
        }
        activityID = urlID;
        model.addAttribute("memberArray", selectMembers());
        model.addAttribute("fkMemberId", getParticipants(activityID));
        return ("tilføjMedlemTilAktivitet");
    }


    @PostMapping("/tilføjMedlemTilAktivitet")
    public String vælgmedlem(@RequestParam(value = "memberId[]", required = false) int[] memberIds) {
        if (memberIds != null) {
            addMembersToActivity(memberIds, activityID);
        } else {
            deleteParticipantsOrOrganizers(activityID, "participants");
        }
        return "redirect:/visAktivitet";
    }

    @GetMapping("/redigerAktivitet")
    public String editActivity(@RequestParam(value = "id", defaultValue = "1") int id, Model model, HttpServletRequest request) {
        if (commonMethods.isSessionInvalid(request)) {
            return "redirect:/login";
        }
        for (Activity activity : selectAllActivities()) {
            if (activity.getId() == id)
                model.addAttribute("activity", activity);
        }
        activityID = id;
        return "redigerAktivitet";
    }
    @PostMapping("/redigerAktivitet")
    public String editActivity(Activity activity) {
        updateActivity(activity);
        return "redirect:/visAktivitet";


    }


    @GetMapping("/sletAktivitet")
    public String deleteActivity(@RequestParam(value = "id", defaultValue = "0") int id, Activity activity, HttpServletRequest request) {
        if (commonMethods.isSessionInvalid(request)) {
            return "redirect:/login";
        }
        deleteActivity(activity);
        return "redirect:/visAktivitet";
    }


    /*metode til at hente alle aktiviter fra databasen*/
    public ArrayList<Activity> selectAllActivities() {
        dbConn db = dbConn.getInstance(); //dbConn objekt som laver metode kald til dbConn klassen og metoden getInstance()
        Connection con = db.createConnection();//connection objekt til at oprette forbindelse til databasen
        Statement s = null; // en tom statement varible
        ArrayList<Activity> allActivities = new ArrayList<>();
        try {
            s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM activities");
            while (rs.next()) {
                try {
                    Activity activity = new Activity(); // Activity objekt
                    activity.setId(rs.getInt("activity_id")); // sæt activity id ligmed hvad den finder på pladsen fra databasen
                    activity.setName(rs.getString("activity_name"));
                    activity.setDescription(rs.getString("activity_description"));
                    activity.setStartDate(rs.getTimestamp("activity_startDate"));
                    activity.setStartTime(rs.getTime("activity_startTime").toLocalTime());
                    activity.setEndDate(rs.getTimestamp("activity_endDate"));
                    activity.setEndTime(rs.getTime("activity_endTime").toLocalTime());
                    allActivities.add(activity); // gemmer alle værdier i en vores arraylist som vi har kaldt allActivities
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allActivities;
    }

    /*metode til at opdatere en række i tabellen aktivities i databasen*/
    public void updateActivity(Activity activity) {
        dbConn db = dbConn.getInstance(); //dbConn objekt som laver metode kald til dbConn klassen og metoden getInstance()
        Connection con = db.createConnection(); //connection objekt til at oprette forbindelse til databasen
        PreparedStatement ps = null; // en tom statement varible
        try {
            ps = con.prepareStatement("UPDATE activities SET activity_name = ?, activity_description = ?, activity_startDate = ?, activity_endDate = ?,activity_startTime = ?, activity_endtime = ? WHERE activity_id = ?");
            ps.setString(1, activity.getName()); // sæt ps til hvad den finder på pladsen ?.
            ps.setString(2, activity.getDescription());
            ps.setDate(3, new java.sql.Date(activity.getStartDate().getTime()));
            ps.setDate(4, new java.sql.Date(activity.getEndDate().getTime()));
            Time startTime = Time.valueOf(activity.getStartTime());
            ps.setTime(5, startTime);
            Time endTime = Time.valueOf(activity.getEndTime());
            ps.setTime(6, endTime);
            ps.setInt(7, activityID);

            ps.executeUpdate(); // beder ps om at opdatere oplysningerne
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
            ps.setString(1, activity.getName()); // sæt ps lig med hvad den finder på activity.getName() pladsen
            ps.setString(2, activity.getDescription());
            ps.setDate(3, new java.sql.Date(activity.getStartDate().getTime()));
            ps.setDate(4, new java.sql.Date(activity.getEndDate().getTime()));
            Time startTime = Time.valueOf(activity.getStartTime()); //
            ps.setTime(5, startTime);
            Time endTime = Time.valueOf(activity.getEndTime());
            ps.setTime(6, endTime);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addEmployeesToActivity(int[] employeeIds, int activityId) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        try {
            deleteParticipantsOrOrganizers(activityId, "organizers");

            for (int i = 0; employeeIds.length > i; i++) {
                ps = con.prepareStatement("INSERT INTO organizers (activities_activity_id, employees_employee_id) VALUES(?, ?)");
                ps.setInt(1, activityId);
                ps.setInt(2, employeeIds[i]);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addMembersToActivity(int[] memberIds, int activityID) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        try {
            deleteParticipantsOrOrganizers(activityID, "participants");

            for (int i = 0; memberIds.length > i; i++) {
                ps = con.prepareStatement("INSERT INTO participants (activities_activity_id, members_member_id) VALUES(?,?)");
                ps.setInt(1, activityID);
                ps.setInt(2, memberIds[i]);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*Metode til at slette en aktivitet fra databasen*/
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

    public static ArrayList<Integer> getOrganizers(int activityID) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        ArrayList<Integer> intList = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT employee_id, activities_activity_id FROM employees INNER JOIN organizers ON employee_id = employees_employee_id WHERE activities_activity_id = ?");
            ps.setInt(1, activityID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try {
                    int employeeId = rs.getInt("employee_id");
                    intList.add(employeeId);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return intList;
    }

    public static ArrayList<Integer> getParticipants(int activityID) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        ArrayList<Integer> intList = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT member_id, activities_activity_id FROM members INNER JOIN participants ON member_id = members_member_id WHERE activities_activity_id = ?");
            ps.setInt(1, activityID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try {
                    int member_id = rs.getInt("member_id");
                    intList.add(member_id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return intList;
    }

    public static void deleteParticipantsOrOrganizers(int activityID, String table) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        Statement s = null;
        try {
            s = con.createStatement();
            s.executeUpdate("DELETE FROM " + table + " WHERE activities_activity_id = " + activityID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
