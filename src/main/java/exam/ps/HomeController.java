package exam.ps;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller

public class HomeController {

    @GetMapping("/")
    public String forside(){

        return "forside";
    }
}
