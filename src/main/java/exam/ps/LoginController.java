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
    public String opretbruger(@ModelAttribute Employee login) throws InvalidKeySpecException, NoSuchAlgorithmException {
        insertActivity(login);
        return "redirect:/loginside";
    }

    @GetMapping("/loginside")
    public String usernamepassword(Model model) {
        model.addAttribute("login", new Login());
        return "loginside";

    }

    @PostMapping("/loginside")
    public String usernamepassword(@ModelAttribute Login login) throws InvalidKeySpecException, NoSuchAlgorithmException {
        ArrayList<Employee> loginArray = selectAlllogins();
        for (Employee employeelogin : loginArray) {
            boolean validatepassword = passwordvalidation.validatepassword(login.getPassWord(), employeelogin.getPassWord());
            String validation = String.valueOf(validatepassword);
            if (employeelogin.getUserName() == login.getUserName() && validation == "true") {
                return "forside";
            }
        }

        return "loginside";
    }


    public void insertActivity(Employee login) throws InvalidKeySpecException, NoSuchAlgorithmException {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        String hashed = passwordhasher.generateStorngPasswordHash(login.getPassWord());

        try {
            ps = con.prepareStatement("INSERT INTO login(email,passWord) VALUES(?, ?)");
            ps.setString(1, login.getUserName());
            ps.setString(2, hashed);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Employee> selectAlllogins() {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        Statement s = null;
        ArrayList<Employee> allpasswords = new ArrayList<>();
        try {
            s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT *  FROM login ");
            while (rs.next()) {
                try {
                    Employee employee = new Employee();
                    employee.setFirstName(rs.getString("email"));
                    employee.setLastName(rs.getString("passWord"));
                    allpasswords.add(employee);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allpasswords;
    }

}

