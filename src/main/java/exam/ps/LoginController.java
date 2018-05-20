package exam.ps;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;//Hvis den algoritme man skal bruge til hashe koden med ikke eksisterer så thrower man den exception.
import java.security.spec.InvalidKeySpecException;
import java.sql.*;

@Controller
public class LoginController {//Controllerens formål er at tage imod requests fra hjemmesiden, og sender det man har requestet over til Model

    public LoginController() {
    }

    @GetMapping("/opretbruger")
    public String login(Model model, HttpServletRequest request) {
        model.addAttribute("login", new Employee());
        return "opretbruger";
    }

    @PostMapping("/opretbruger")
    public String opretbruger(@ModelAttribute Login login) throws InvalidKeySpecException, NoSuchAlgorithmException {
        insertLogin(login);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String usernamepassword(Model model) {
        model.addAttribute("login", new Login());
        return "login";

    }

    @PostMapping("/login")
    public String usernamepassword(@ModelAttribute Login login, HttpServletRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Login user = selectUser(login.getUserName());

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        if (PasswordMatcher.validatepassword(login.getPassWord(), user.getPassWord())) {
            return "redirect:/forside";
        }


        return "redirect:/";
    }

    @GetMapping("/logaf")
    public String logaf(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login";
    }


    public void insertLogin(Login login) throws InvalidKeySpecException, NoSuchAlgorithmException {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        String hashed = PasswordHasher.generateStorngPasswordHash(login.getPassWord());

        try {
            ps = con.prepareStatement("INSERT INTO users(user_email,user_password) VALUES(?, ?)");
            ps.setString(1, login.getUserName());
            ps.setString(2, hashed);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Login selectUser(String email) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        Login user = new Login();

        try {
            ps = con.prepareStatement("SELECT * FROM users WHERE user_email = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                try {
                    user.setUserName(rs.getString("user_email"));
                    user.setPassWord(rs.getString("user_password"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}

