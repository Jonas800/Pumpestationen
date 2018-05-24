package exam.ps;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;//Hvis den algoritme man skal bruge til hashe koden med ikke eksisterer så thrower man den exception.
import java.security.spec.InvalidKeySpecException;
import java.sql.*;

@Controller
public class LoginController {//Controllerens formål er at tage imod requests fra hjemmesiden, og sender det man har requestet over til Model

    String error = "";

    public LoginController() {
    }

    @GetMapping("/opretbruger")
    public String login(@RequestParam(value = "ID", defaultValue = "-1") int employeeId, Model model, HttpServletRequest request) {
        if (commonMethods.isSessionInvalid(request)) {
            return "redirect:/login";
        }
        Employee employee = selectUser(employeeId);
        if (selectUser(employeeId) == null) {
            employee.setID(employeeId);
        }
        employee.setPassword("");
        model.addAttribute("employee", employee);

        return "opretbruger";
    }

    @PostMapping("/opretbruger")
    public String opretbruger(@ModelAttribute Employee login) throws InvalidKeySpecException, NoSuchAlgorithmException {
        insertLogin(login);
        return "redirect:/VisMedarbejdere";
    }

    @GetMapping("/login")
    public String usernamepassword(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("error", error);

        error = "";
        return "login";

    }

    @PostMapping("/login")
    public String usernamepassword(@ModelAttribute Employee login, HttpServletRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Employee user = selectUser(login.getEmail());

        if (!user.getEmail().isEmpty()) {

            if (PasswordMatcher.validatepassword(login.getPassword(), user.getPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                return "redirect:/forside";
            }
        }

        error = "Ugyldigt login";

        return "redirect:/login";

    }

    @GetMapping("/logaf")
    public String logaf(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login";
    }


    public void insertLogin(Employee login) throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (login.getID() > 0) {
            dbConn db = dbConn.getInstance();
            Connection con = db.createConnection();
            PreparedStatement ps = null;
            String hashed = PasswordHasher.generateStrongPasswordHash(login.getPassword());
            if (login.getHasUser() == false) {
                try {
                    ps = con.prepareStatement("INSERT INTO users(user_email,user_password,employees_employee_id) VALUES(?, ?, ?)");
                    ps.setString(1, login.getEmail());
                    ps.setString(2, hashed);
                    ps.setInt(3, login.getID());
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    if (login.getPassword().isEmpty()) {
                        ps = con.prepareStatement("UPDATE users SET user_email=? WHERE employees_employee_id=?");
                        ps.setString(1, login.getEmail());
                        ps.setInt(2, login.getID());
                    } else {
                        ps = con.prepareStatement("UPDATE users SET user_email=?, user_password=? WHERE employees_employee_id=?");
                        ps.setString(1, login.getEmail());
                        ps.setString(2, hashed);
                        ps.setInt(3, login.getID());
                    }
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        } else {
            error = "Ugyldig medarbejder";
        }
    }

    public Employee selectUser(String email) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        Employee user = new Employee();

        try {
            ps = con.prepareStatement("SELECT * FROM users LEFT JOIN employees ON employees_employee_id = employee_id WHERE user_email = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                try {
                    user.setEmail(rs.getString("user_email"));
                    user.setPassword(rs.getString("user_password"));
                    if (rs.getString("employee_firstName") != null) {
                        user.setFirstName(rs.getString("employee_firstName"));
                        user.setID(rs.getInt("employee_id"));
                        user.setLastName(rs.getString("employee_lastName"));
                        user.setJobPosition(rs.getString("employee_jobPosition"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Employee selectUser(int employeeId) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        Employee user = new Employee();

        try {
            ps = con.prepareStatement("SELECT * FROM employees LEFT JOIN users ON employees_employee_id = employee_id WHERE employee_id = ?");
            ps.setInt(1, employeeId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                try {
                    //Tjekker om medarbejderen har et login
                    user.setHasUser(rs.getString("user_email") != null);

                    user.setEmail(rs.getString("user_email"));
                    user.setPassword(rs.getString("user_password"));
                    user.setFirstName(rs.getString("employee_firstName"));
                    user.setID(rs.getInt("employee_id"));
                    user.setLastName(rs.getString("employee_lastName"));
                    user.setJobPosition(rs.getString("employee_jobPosition"));
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

