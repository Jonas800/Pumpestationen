package exam.ps;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class JobController {

    boolean edit;

    @GetMapping("/job")
    public String job(@RequestParam(value = "ret", defaultValue = "false") boolean editParam,
                      @RequestParam(value = "slet", defaultValue = "false") boolean delete,
                      @RequestParam(value = "id", defaultValue = "0") int id,
                      Model model) {

        edit = editParam;

        dbConn dbconn = dbConn.getInstance();
        ArrayList<Job> allJobs = dbconn.selectAllJobs();
        if (id != 0) {
            for (Job job : allJobs) {
                if (job.getId() == id) {
                    if (edit) {
                        model.addAttribute("job", job);
                    } else if (delete) {
                        dbConn db = dbConn.getInstance();
                        db.deleteJob(job);

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
            db.updateJob(job);
        } else {
            db.insertJob(job);
        }
        return "redirect:/job";
    }
}
