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
import java.sql.*;
import java.util.ArrayList;

@Controller
public class EmployeeController {
    ArrayList<Employee> employeeArrayList = new ArrayList<>();
    int employeid = 0;

    @GetMapping("/VisMedarbejdere")
    public String VisMedarbejdere(Model model) {
        model.addAttribute("employeeArrayList", employeeArrayList);


        return "VisMedarbejdere";

    }

    @GetMapping("/Opretmedarbejdere")
    public String Opretmedarbjedere(Model model) {
        model.addAttribute("employee", new Employee());


        return "Opretmedarbejdere";
    }

    @PostMapping("/Opretmedarbejdere")
    public String Opretmedarbjedere(@ModelAttribute Employee employee) throws FileNotFoundException {
        dbConn db = dbConn.getInstance();

        insertEmployee(employee);

        return "redirect:/visMedarbejder";
    }

    private void insertEmployee(Employee employee) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        Statement s = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            ps = con.prepareStatement("SELECT COUNT(*) AS count FROM zipcodes WHERE zipcode = ?");
            ps.setInt(1, employee.getZipcode());

            ResultSet rs = ps.executeQuery();

            rs.next();
            if(rs.getInt("count") == 0){
                ps = con.prepareStatement("INSERT INTO zipcodes(zipcode, zipcode_city) VALUES(?,?)");
                ps.setInt(1, employee.getZipcode());
                ps.setString(2, employee.getCity());

                ps.executeUpdate();
            }
            ps.close();
            ps2 = con.prepareStatement("INSERT INTO employees (employee_firstName, employee_lastName, employee_address, employee_phone, employee_cpr, zipcodes_zipcode,employee_jobPosition) VALUES(?, ?, ?, ?, ?, ?,?)");
            ps2.setString(1, employee.getFirstName());
            ps2.setString(2, employee.getLastName());
            ps2.setString(3, employee.getAddress());
            ps2.setString(4, employee.getPhoneNumber());
            ps2.setString(5, employee.getCpr());
            ps2.setInt(6, employee.getZipcode());
            ps2.setString(7, employee.getJobPosition());

            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @GetMapping("/redigerMedarbejdere")
    public String redigerAnsat(@RequestParam(value = "ID", defaultValue = "1") int ID, Model model) {
        for (Employee employee : employeeArrayList) {
            if (employee.getID() == ID)
                model.addAttribute("employee", employee);
        }
        employeid = ID;
        return "redigerMedarbejdere";
    }

    @PostMapping("/redigerMedarbejdere")
    public String redigerAnsat(Employee employee) throws FileNotFoundException {
        employee.setID(employeid);
        employeeArrayList.set(employeid - 1, employee);
        saveEmployee(employeeArrayList);

        return "redirect:/VisMedarbejdere";
    }

    @GetMapping("/SletMedarbejder")
    public String SletMedarbejder(@RequestParam(value = "ID", defaultValue = "1") int id) throws FileNotFoundException {
        employeeArrayList.remove(id - 1);
        saveEmployee(employeeArrayList);
        return "redirect:/VisMedarbejdere";
    }

    public static void saveEmployee(ArrayList<Employee> employeeArrayList) throws FileNotFoundException {
        PrintStream ps = new PrintStream("src/main/resources/templates/Employee.txt");
        String s = "";
        for (Employee E : employeeArrayList) {
            s += E.toString() + "\r\n";

        }
        ps.print(s);
        ps.close();
    }

}
