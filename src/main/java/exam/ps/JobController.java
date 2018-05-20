package exam.ps;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;

@Controller
public class JobController {

    boolean edit;

    @GetMapping("/job")
    public String job(@RequestParam(value = "ret", defaultValue = "false") boolean editParam,
                      @RequestParam(value = "slet", defaultValue = "false") boolean delete,
                      @RequestParam(value = "id", defaultValue = "0") int id,
                      Model model,
                      HttpServletRequest request) {
        if (commonMethods.isSessionInvalid(request)) {
            return "redirect:/login";
        }

        edit = editParam;

        ArrayList<Job> allJobs = Job.selectAllJobs();
        if (id != 0) {
            for (Job job : allJobs) {
                if (job.getId() == id) {
                    if (edit) {
                        model.addAttribute("job", job);
                    } else if (delete) {
                        dbConn db = dbConn.getInstance();
                        deleteJob(job);

                        return "redirect:/job";
                    }
                }
            }
        } else {
            model.addAttribute("job", new Job());
        }
        model.addAttribute("jobs", allJobs);

        return "job";
    }

    @PostMapping("/job")
    public String createJob(@ModelAttribute Job job) {
        dbConn db = dbConn.getInstance();
        if (edit) {
            updateJob(job);
        } else {
            insertJob(job);
        }
        return "redirect:/job";
    }


    public void insertJob(Job job) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO jobs (job_title) VALUES(?)");
            ps.setString(1, job.getTitle());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateJob(Job job) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE jobs SET job_title = ? WHERE job_id = ?");
            ps.setString(1, job.getTitle());
            ps.setInt(2, job.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteJob(Job job) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM jobs WHERE job_id = ?");
            ps.setInt(1, job.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
