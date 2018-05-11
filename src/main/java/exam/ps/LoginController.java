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
import java.util.ArrayList;
import java.util.Scanner;

@Controller
public class LoginController  {
    ArrayList<login> loginArrayList = getlogins();

    public LoginController() throws FileNotFoundException {
    }

    @GetMapping("/opretbruger")
    public String login(Model model) {
        model.addAttribute("login", new login());
        return "opretbruger";
    }

    @PostMapping("/opretbruger")
    public String opretbruger(@ModelAttribute login login) throws FileNotFoundException {
        loginArrayList.add(login);
        writelogin(loginArrayList);
        return "redirect:/loginside";
    }

    @GetMapping("/loginside")
    public String usernamepassword (){

        return "loginside";
    }

    @PostMapping("/loginside")
    public String usernamepassword(@RequestParam("username") String username, @RequestParam("password") String password) throws FileNotFoundException {

        PrintStream w = new PrintStream("src/main/resources/templates/password.txt");

        for (login login : loginArrayList) {


            if (username.equals(login.getUserName()) && password.equals(login.getPassWord())) {

                return "/loginside";

            }


        }
        return "loginside";

    }







}