package exam.ps;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;

@Controller
public class LoginController {

    public LoginController() {
    }

    @GetMapping("/opretbruger")
    public String login(Model model) {
        model.addAttribute("login", new Employee());
        return "opretbruger";
    }

    @PostMapping("/opretbruger")
    public String opretbruger(@ModelAttribute Login login) throws InvalidKeySpecException, NoSuchAlgorithmException {
        insertLogin(login);
        return "redirect:/loginside";
    }

    @GetMapping("/loginside")
    public String usernamepassword(Model model) {
        model.addAttribute("login", new Login());
        return "loginside";

    }

    @PostMapping("/loginside")
    public String usernamepassword(@ModelAttribute Login login) throws InvalidKeySpecException, NoSuchAlgorithmException {
        System.out.println(login.getUserName() + "" + login.getPassWord());
        Login user = selectUser(login.getUserName());
        System.out.println(user.getUserName() + " " + user.getPassWord());

        if (passwordvalidation.validatepassword(login.getPassWord(), user.getPassWord())) {
            return "forside";
        }


        return "loginside";
    }


    public void insertLogin(Login login) throws InvalidKeySpecException, NoSuchAlgorithmException {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        String hashed = passwordhasher.generateStorngPasswordHash(login.getPassWord());

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

