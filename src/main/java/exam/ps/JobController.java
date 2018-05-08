package exam.ps;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class JobController {

    @GetMapping("/job")
    public String job(Model model){
        dbConn dbconn = dbConn.getInstance();
        ArrayList<Job> allJobs =  dbconn.selectAllJobs();

        model.addAttribute("jobs", allJobs);
        model.addAttribute("job", new Job());
        return "job";
    }

    @PostMapping("/job")
    public String createJob(@ModelAttribute Job job){

        dbConn db = dbConn.getInstance();
        db.insertJob(job);

        return "redirect:/job";
    }
}
