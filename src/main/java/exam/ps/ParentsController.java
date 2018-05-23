package exam.ps;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
import java.util.ArrayList;

@Controller
public class ParentsController {
    int ParentID = 0;
    int medlemsID = 0;

    @GetMapping("visforaeldre")
    public String visforældre(Model model, @RequestParam(value = "id", defaultValue = "1") int id) {
        medlemsID = id;
        model.addAttribute("parents", selectparents(medlemsID));
        model.addAttribute("medlemid",medlemsID);
        return "visforaeldre";
    }

    @GetMapping("/opretForaeldre")
    public String opretForældre(Model model, @RequestParam(value = "id", defaultValue = "1") int id) {
        medlemsID = id;
        model.addAttribute("parent", new Parent());
        return ("opretForaeldre");
    }

    @PostMapping("/opretForaeldre")
    public String opretForældre(@ModelAttribute Parent parent) {
        insertparents(parent, medlemsID);
        return "redirect:/vismedlem";
    }


    @GetMapping("/redigereforaeldre")
    public String redigereforældre(@RequestParam(value = "id", defaultValue = "1") int id, Model model) {
        ParentID = id;
        for (Parent parent: selectparents(medlemsID)) {
            if(parent.getID()==ParentID);
            model.addAttribute("parent", parent);
        }

        return "redigereforaeldre";
    }

    @PostMapping("/redigereforaeldre")
    public String redigereforældre(Parent parent) {
        updateparent(parent);
        return "redirect:/Visforaeldre";
    }

    public ArrayList<Parent> selectparents(int medlemsId) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        ArrayList<Parent> parents = new ArrayList<>();

        try {
            ps = con.prepareStatement("SELECT * FROM parents " +
                    "INNER JOIN zipcodes ON zipcode = zipcodes_zipcode " +
                    "INNER JOIN members ON member_id = members_member_id " +
                    "WHERE member_id = ?");
            ps.setInt(1, medlemsId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                try {
                    Parent Currentparent = new Parent();
                    Currentparent.setID(rs.getInt("parent_id"));
                    Currentparent.setFirstName(rs.getString("parent_firstName"));
                    Currentparent.setLastName(rs.getString("parent_lastName"));
                    Currentparent.setAddress(rs.getString("parent_address"));
                    Currentparent.setZipcode(rs.getInt("parent_phone"));
                    Currentparent.setCity(rs.getString("zipcodes_zipcode"));
                    Currentparent.setPhoneNumber(rs.getString("parent_phone"));

                    parents.add(Currentparent);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parents;
    }

    private void insertparents(Parent parent, int medlemsId) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        Statement s = null;
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
            ps.close();
            ps = con.prepareStatement("INSERT INTO parents (parent_firstName, parent_lastName, parent_address, parent_phone, zipcodes_zipcode,members_member_id) VALUES(?, ?, ?, ?, ?,?) ");
            ps.setString(1, parent.getFirstName());
            ps.setString(2, parent.getLastName());
            ps.setString(3, parent.getAddress());
            ps.setString(4, parent.getPhoneNumber());
            ps.setInt(5, parent.getZipcode());
            ps.setInt(6, medlemsId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateparent(Parent parent) {
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

            ps = con.prepareStatement("UPDATE Parrents INNER JOIN zipcodes ON zipcode = zipcodes_zipcode SET Parrent_Firstname =?,Parrent_LastName =?,Parrent_address =?,Parrent_zipcode =? ,parrentOneNumber =? WHERE  =?");
            ps.setInt(7, ParentID);
            ps.setString(1, parent.getFirstName());
            ps.setString(2, parent.getLastName());
            ps.setString(3, parent.getAddress());
            ps.setInt(4, parent.getZipcode());
            ps.setString(5, parent.getPhoneNumber());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

