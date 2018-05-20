
package exam.ps;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller

public class HomeController {

    @GetMapping("/")
    public String forside(HttpServletRequest request) {
        if (commonMethods.isSessionInvalid(request)) {
            return "redirect:/login";
        }
        return "forside";
    }

    @GetMapping("/forside")
    public String forside2(HttpServletRequest request) {
        if (commonMethods.isSessionInvalid(request)) {
            return "redirect:/login";
        }
        return "forside";
    }
}






