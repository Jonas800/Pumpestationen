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
        return "redirect:/vismedlem";
    }

    @GetMapping("/redigerMedlem")
    public String redigerMedlem(@RequestParam(value = "id", defaultValue = "1") int id, Model model) {
        for (Member member : selectMembers()) {//requester et id, går arraylist igennem med metodekald og sammenligner id i objektet member med id som er indtastet
           if (member.getId() == id)//hvis de er ens, printes medlemmet ud på hjemmesiden
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
        PreparedStatement ps = null;//subInterface, en parameteriseret forespørgsel
        PreparedStatement ps2 = null;//samme
        try { //count returnerer et antal rækker, der matcher et kriterie,
            ps = con.prepareStatement("SELECT COUNT(*) AS count FROM zipcodes WHERE zipcode = ?");
            ps.setInt(1, member.getZipcode());

            ResultSet rs = ps.executeQuery();
            rs.next();
            if(rs.getInt("count") == 0){ // hvis der ikke er nogen rækker med det man lige har indtastet så indsætter vi postNr og by.
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
        Statement s = null; // erklærer en tom statement variable
        try {
            s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT *  FROM members INNER JOIN zipcodes ON zipcode = zipcodes_zipcode ");
            while (rs.next()) {
                try {
                    Member member = new Member();
                    member.setFirstName(rs.getString("member_firstName"));
                    member.setLastName(rs.getString("member_lastName"));
                    member.setDateOfBirth(rs.getDate("member_dateOfBirth"));
                    member.setCPR(rs.getString("member_CPR"));
                    member.setAddress(rs.getString("member_address"));
                    member.setId(rs.getInt("member_Id"));
                    member.setZipcode(rs.getInt("zipcodes_zipcode"));//joiner for at kunne få fat i tabellen zipcodes og hente by og postnr fra zipcodes
                    member.setCity(rs.getString("zipcode_city"));


                    memberSelect.add(member);//grunden til vi opretter et member objekt er fordi vi gerne vil ha medlemsobjekterne vist i form af arraylist
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
        PreparedStatement ps2 = null;

        try {
            ps2 = con.prepareStatement("SELECT COUNT(*) AS count FROM zipcodes WHERE zipcode = ?");
            ps2.setInt(1, member.getZipcode());

            ResultSet rs = ps2.executeQuery();
            rs.next();
            if (rs.getInt("count") == 0) {
                ps2 = con.prepareStatement("INSERT INTO zipcodes(zipcode, zipcode_city) VALUES(?,?)");
                ps2.setInt(1, member.getZipcode());
                ps2.setString(2, member.getCity());

                ps.executeUpdate();
            }

            ps = con.prepareStatement("UPDATE members INNER JOIN zipcodes ON zipcode = zipcodes_zipcode SET member_firstName =?,member_lastName =?,member_dateOfBirth =?,member_cpr =? ,member_address =?,zipcodes_zipcode=? WHERE member_id =?");
            ps.setInt(7, memberID);//får det ID som er blevet requestet fra hjemmesiden, og den viser rækken hvis id'et eksisterer i tabellen
            ps.setString(1, member.getFirstName());
            ps.setString(2, member.getLastName());
            ps.setDate(3, new java.sql.Date(member.getDateOfBirth().getTime()));
            ps.setString(4, member.getCPR());
            ps.setString(5, member.getAddress());
            ps.setInt(6, member.getZipcode());

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

