package exam.ps;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;

@Controller
public class EmployeeController {
    int employeid = 0;

    @GetMapping("/VisMedarbejdere")
    public String VisMedarbejdere(Model model) {
        model.addAttribute("employeeArrayList", selectAllEmployees());


        return "VisMedarbejdere";

    }

    @GetMapping("/Opretmedarbejdere")
    public String Opretmedarbjedere(Model model) {
        model.addAttribute("employee", new Employee());


        return "Opretmedarbejdere";
    }

    @PostMapping("/Opretmedarbejdere")
    public String Opretmedarbjedere(@ModelAttribute Employee employee)  {
        dbConn db = dbConn.getInstance();

        insertEmployee(employee);

        return "redirect:/VisMedarbejdere";
    }


    @GetMapping("/redigerMedarbejdere")
    public String redigerAnsat(@RequestParam(value = "ID", defaultValue = "1") int ID, Model model) {
        for (Employee employee:selectAllEmployees()) {
            if (employee.getID() == ID)
                model.addAttribute("employee", employee);


        }


                    employeid=ID;



        return"redigerMedarbejdere";
}

    @PostMapping("/redigerMedarbejdere")
    public String redigerAnsat(Employee employee)   {
        updateployee(employee);
        return "redirect:/VisMedarbejdere";
   }

    @GetMapping("/SletMedarbejder")
    public String SletMedarbejder(@RequestParam(value = "ID", defaultValue = "1") int id, Employee employee) throws FileNotFoundException {
        deleteemployee(employee);
        return "redirect:/VisMedarbejdere";
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
    public void deleteemployee(Employee employee) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM employees WHERE employee_id = ?");
            ps.setInt(1, employee.getID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Employee> selectAllEmployees(){
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        Statement s = null;
        ArrayList<Employee> allEmployees = new ArrayList<>();
        try{
            s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT *  FROM employees INNER JOIN zipcodes ON zipcode = zipcodes_zipcode ");
            while(rs.next()){
                try{
                    Employee employee = new Employee();
                    employee.setID(rs.getInt("employee_id"));
                    employee.setFirstName(rs.getString("employee_firstName"));
                    employee.setLastName(rs.getString("employee_lastName"));
                    employee.setAddress(rs.getString("employee_address"));
                    employee.setPhoneNumber(rs.getString("employee_phone"));
                    employee.setCpr(rs.getString("employee_cpr"));
                    employee.setZipcode(rs.getInt("zipcodes_zipcode"));
                    employee.setCity(rs.getString("zipcode_city"));
                    employee.setJobPosition(rs.getString("employee_jobPosition"));
                    allEmployees.add(employee);
                } catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return allEmployees;
    }

    public void updateployee(Employee employee) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE employees INNER JOIN zipcodes ON zipcode = zipcodes_zipcode SET employee_firstName = ?,employee_lastName=?,employee_cpr=?,employee_address=?,employee_phone=?,employee_jobPosition=?,zipcodes_zipcode=?,zipcode_city=? WHERE employee_id = ?");
            ps.setInt(9, employeid);
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getCpr());
            ps.setString(4, employee.getAddress());
            ps.setString(5, employee.getPhoneNumber());
            ps.setString(6,employee.getJobPosition());
            ps.setInt(7,employee.getZipcode());
            ps.setString(8,employee.getCity());


            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
