package exam.ps;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

@Controller
public class ActivityController {
    ArrayList<Activity> activitiesArray = new ArrayList<>();
    int activityID = 0;

    @GetMapping("/visAktivitet")
    public String ShowActivities(Model model) {
        model.addAttribute("activitiesArray", activitiesArray);
        return "visAktivitet";
    }

    @GetMapping("/tilføjAktivitet")
    public String createActivity(Model model) {
        model.addAttribute("activity", new Activity());

        return "tilføjAktivitet";
    }

    @PostMapping("/tilføjAktivitet")
    public String createActivity(@ModelAttribute Activity activity) throws FileNotFoundException {
        dbConn db = dbConn.getInstance();

        insertActivity(activity);

        return "redirect:/visAktivitet";
    }

    @GetMapping("/redigerAktivitet")
    public String editActivity(@RequestParam(value = "id", defaultValue = "1") int id, Model model) {
        if (model != null) {
            model.addAttribute("activity", activitiesArray.get((id) - 1));
        }
        activityID = id;
        return "redigerAktivitet";
    }

    @PostMapping("/redigerAktivitet")
    public String editActivity(@ModelAttribute Activity activity) throws FileNotFoundException {
        activity.setId(activityID);
        activitiesArray.set(activityID-1, activity);
        saveToFile(activitiesArray);
        return "redirect:/visAktivitet";
    }

    @GetMapping("/sletAktivitet")
    public String deleteActivity(@RequestParam(value = "id", defaultValue = "0") int id) throws FileNotFoundException {
        for (int i = 0; i < activitiesArray.size(); i++) {
            if (activitiesArray.get(i).getId() == id) {
                activitiesArray.remove(i);
            }
        }
        saveToFile(activitiesArray);
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



    public static void saveToFile(ArrayList<Activity> activitiesArray) throws FileNotFoundException {
        PrintStream ps = new PrintStream(new File("src/main/resources/templates/Activity.txt"));

        String s = "";
        for (Activity c :activitiesArray) {
            s += c.toString() + "\r\n";
        }
        ps.print(s);
        ps.close();

    }
}
