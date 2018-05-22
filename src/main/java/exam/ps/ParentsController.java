package exam.ps;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;

@Controller
public class ParentsController {
Parent parent =selectparents();
    int ParentID=0;


@GetMapping("visforældre")
public String visforældre(Model model) {
    model.addAttribute("parent",selectparents());

return "visforældre";
}

    @GetMapping("/opretForældre")
    public String opretForældre(Model model) {
      model.addAttribute("parent",new Parent());

      return("opretForældre");
    }
@PostMapping("/opretForældre")
    public String opretForældre (@ModelAttribute Parent parent) {
    insertEmployee(parent);
        return "redirect:/vismedlem";
}


    @GetMapping("/redigereforældre")
    public String redigereforældre(@RequestParam(value = "id", defaultValue = "1") int id, Model model) {
        model.addAttribute("parent",parent);
     return "redigereforældre";
}

    @PostMapping("/redigereforældre")
    public String redigereforældre(Parent parent){
        updateParrent(parent);
        return "redirect:/Visforældre";
    }

    public static Parent selectparents() {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        Statement s = null;
        Parent Currentparent = new Parent();

        try {
            s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT *  FROM Parrents INNER JOIN zipcodes ON zipcode = zipcodes_zipcode ");
            while (rs.next()) {
                try {
                    Currentparent.setID(rs.getInt("Parrents_id"));
                    Currentparent.setFirstName(rs.getString("Parrent_firstName"));
                    Currentparent.setLastName(rs.getString("Parrent_lastName"));
                    Currentparent.setAddress(rs.getString("Parrent_address"));
                    Currentparent.setZipcode(rs.getInt("Parrent_phone"));
                    Currentparent.setCity(rs.getString("zipcode_zipcode"));
                    Currentparent.setPhoneNumber(rs.getString("Parrent_OneNumber"));
                    Currentparent.setCity(rs.getString("Parrent_TwoNumber"));

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Currentparent;
    }

    private void insertEmployee(Parent parent) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        Statement s = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            ps = con.prepareStatement("SELECT COUNT(*) AS count FROM zipcodes WHERE zipcode = ?");
            ps.setInt(1, parent.getZipcode());

            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getInt("count") == 0) {
                ps = con.prepareStatement("INSERT INTO zipcodes(zipcode, zipcode_city) VALUES(?,?)");
                ps.setInt(1, parent.getZipcode());
                ps.setString(2, parent.getCity());

                ps.executeUpdate();
            }
            ps.close();
            ps2 = con.prepareStatement("INSERT INTO parents (parent_firstName, parent_lastName, parent_address, parent_phone, zipcodes_zipcode) VALUES(?, ?, ?, ?, ?)");
            ps2.setString(1, parent.getFirstName());
            ps2.setString(2, parent.getLastName());
            ps2.setString(3, parent.getAddress());
            ps2.setString(4, parent.getPhoneNumber());
            ps2.setInt(5, parent.getZipcode());

            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

}

    public void updateParrent(Parent parent){
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT COUNT(*) AS count FROM zipcodes WHERE zipcode = ?");
            ps.setInt(1, parent.getZipcode());

            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getInt("count") == 0) {
                ps = con.prepareStatement("INSERT INTO zipcodes(zipcode, zipcode_city) VALUES(?,?)");
                ps.setInt(1, parent.getZipcode());
                ps.setString(2, parent.getCity());

                ps.executeUpdate();
            }

            ps = con.prepareStatement("UPDATE Parrents INNER JOIN zipcodes ON zipcode = zipcodes_zipcode SET Parrent_Firstname =?,Parrent_LastName =?,Parrent_address =?,Parrent_zipcode =? ,parrentOneNumber =? WHERE Parrents_ID =?");
            ps.setInt(7, ParentID);
            ps.setString(1, parent.getFirstName());
            ps.setString(2, parent.getLastName());
            ps.setString(3, parent.getAddress());
            ps.setInt(4, parent.getZipcode());
            ps.setString(5, parent.getPhoneNumber());

            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}

