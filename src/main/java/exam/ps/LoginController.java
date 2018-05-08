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
public class LoginController {
    ArrayList<login> loginArrayList = new ArrayList<>();

    @GetMapping("/loginside")
    public String login(Model model) {
        model.addAttribute("login", new login());
        return "loginside";
    }

    @PostMapping("/loginside")
    public String usernamepassword(@RequestParam("username") String username, @RequestParam("password") String password) throws FileNotFoundException {

        PrintStream w = new PrintStream(new File("src/main/resources/templates/password.txt"));

        for (login login : loginArrayList) {


            if (username.equals(login.getUserName()) && password.equals(login.getPassWord())) {

                return "/forside";

            }


        }
        return "loginside";

    }

    @GetMapping("/opretbruger")
    public String opretbruger(Model model) {
     model.addAttribute("login",new login());

        return "opretbruger";


    }

    @PostMapping("/opretbruger")
    public String opretbruger(@ModelAttribute login login) throws FileNotFoundException {
        loginArrayList.add(new login());
        writelogin(loginArrayList);
        return "redirect:/loginside";
    }

    public static void writelogin(ArrayList<login> loginArrayList) throws FileNotFoundException {
        PrintStream ps = new PrintStream(new File("src/main/resources/templates/login.txt"));
        for (login login : loginArrayList) {
            ps.print(login.toString());
        }

    }

    public ArrayList<login> getlogins() {
        ArrayList<login> loginArrayList = new ArrayList<>();
        Scanner scan = new Scanner("src/main/resources/templates/login.txt");

        while(scan.hasNextLine()) {
            String line = scan.nextLine();
            Scanner readline = new Scanner(line).useDelimiter("#");
            login login =new login();
            login.setUserName(readline.next());
            login.setPassWord(readline.next());
            loginArrayList.add(login);
        }
        return loginArrayList;

    }
}