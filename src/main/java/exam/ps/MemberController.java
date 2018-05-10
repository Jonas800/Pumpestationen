package exam.ps;
import java.sql.Date;
import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.*;
import java.sql.*;
@Controller
public class MemberController {
    int memberID = 0;

    public MemberController() {

    }

    @GetMapping("/vismedlem")
    public String showMember(Model model) {
        model.addAttribute("memberArray", selectMembers());
        return "vismedlem";
    }

    @GetMapping("/tilføjmedlem")
    public String tilføjmedlem(Model model) {
        model.addAttribute("member", new Member());

        return "tilføjmedlem";
    }

    @PostMapping("/tilføjmedlem")
    public String tilføjmedlem(@ModelAttribute Member member)  {
        dbConn db = dbConn.getInstance();
        insertMember(member);
        return "redirect:/tilføjmedlem";
    }

    @GetMapping("/redigerMedlem")
    public String redigerMedlem(@RequestParam(value = "id", defaultValue = "1") int id, Model model) {
        for (Member member : selectMembers()) {
           if (member.getId() == id)
                model.addAttribute("member", member);

        }
        memberID = id;

         return "redigerMedlem";
    }

    @PostMapping("/redigerMedlem")
    public String redigerMedlem(Member member) {
        /*member.setId(memberID);
        memberArray.set(memberID - 1, member);
        saveMemberToFile(memberArray);*/
        updateMember(member);

        return "redirect:/vismedlem";
    }


    @GetMapping("/sletmedlem")
    public String deleteMember(@RequestParam(value = "id", defaultValue = "1") int id, Member member) throws FileNotFoundException  {
        deleteMember(member);

        return "redirect:/vismedlem";



    }


    /*public static void saveMemberToFile(ArrayList<Member> memberArray) throws FileNotFoundException {
        PrintStream ps = new PrintStream("src/main/resources/templates/Member.txt");
        String s = "";
        for (Member m : memberArray) {
            s += m.toString() + "\r\n";

        }
        ps.print(s);
        ps.close();
    }
*/
   /* public ArrayList<Member> getMemberArray() throws FileNotFoundException {
        ArrayList<Member> ArrayMember = new ArrayList<>();

        Scanner readFile = new Scanner(new File("src/main.resources/templates/Member.txt"));
        while (readFile.hasNextLine()) {
            String line = readFile.nextLine();
            Scanner readLine = new Scanner(line).useDelimiter("#");

            Member member = new Member();
            member.setFirstName(readLine.next());
            member.setLastName(readLine.next());
            member.setAge(readLine.nextInt());
            member.setCPR(readLine.next());
            member.setId(readLine.nextInt());
            member.setKontingent(readLine.nextInt());
            ArrayMember.add(member);



        }
        return ArrayMember;
    }
*/
    private void insertMember(Member member){
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        Statement s = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            ps = con.prepareStatement("SELECT COUNT(*) AS count FROM zipcodes WHERE zipcode = ?");
            ps.setInt(1, member.getZipcode());

            ResultSet rs = ps.executeQuery();
            rs.next();
            if(rs.getInt("count") == 0){
                ps = con.prepareStatement("INSERT INTO zipcodes(zipcode, zipcode_city) VALUES(?,?)");
                ps.setInt(1, member.getZipcode());
                ps.setString(2, member.getCity());

                ps.executeUpdate();
            }
            ps.close();
            ps2 = con.prepareStatement("INSERT INTO members(member_firstName, member_lastName, member_dateOfBirth, member_CPR, member_address, zipcodes_zipcode) VALUES(?,?,?,?,?,?)");
            ps2.setString(1,member.getFirstName());
            ps2.setString(2,member.getLastName());
            ps2.setDate(3, new java.sql.Date(member.getDateOfBirth().getTime()));
            ps2.setString(4, member.getCPR());
            ps2.setString(5, member.getAddress());
            ps2.setInt(6, member.getZipcode());
            ps2.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }



    }

    public ArrayList<Member> selectMembers() {
        ArrayList<Member> memberSelect = new ArrayList<>();
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        Statement s = null;
        try {
            s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT *  FROM employees INNER JOIN zipcodes ON zipcode = zipcodes_zipcode ");
            while (rs.next()) {
                try {
                    Member member = new Member();
                    member.setFirstName(rs.getString("member_firstName"));
                    member.setLastName(rs.getString("member_lastName"));
                    member.setAge(rs.getInt("member_age"));
                    member.setCPR(rs.getString("member_CPR"));
                    member.setId(rs.getInt("member_Id"));
                    member.setKontingent(rs.getInt("member_kontingent"));
                    member.setZipcode(rs.getInt("zipcodes_zipcode"));
                    member.setCity(rs.getString("member_city"));
                    memberSelect.add(member);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return memberSelect;
    }


    public void updateMember(Member member){
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement("UPDATE members INNER JOIN zipcode ON zipcode = zipcodes_zipcodes SET member_firstName =?,member_lastName =?,member_age =?,member_cpr =?,member_id =?,member_kontingent =?, member_dateOfBirth =?,member_address =?,member_zipcode,member_city =? WHERE member_id =?");
            ps.setInt(5, memberID);
            ps.setString(1, member.getFirstName());
            ps.setString(2, member.getLastName());
            ps.setInt(3, member.getAge());
            ps.setString(4, member.getCPR());
            ps.setInt(5, member.getKontingent());
            ps.setDate(6, (Date) member.getDateOfBirth());
            ps.setString(7, member.getAddress());
            ps.setInt(8, member.getZipcode());
            ps.setString(9, member.getCity());

            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteMember(Member member) {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM members WHERE member_id = ?");
            ps.setInt(1, member.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

