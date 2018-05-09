package exam.ps;
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
    ArrayList<Member> memberArray = new ArrayList<>();
    int memberID = 0;

    public MemberController() {

    }

    @GetMapping("/vismedlem")
    public String showMember(Model model) {
        model.addAttribute("memberArray", memberArray);
        return "vismedlem";
    }

    @GetMapping("/tilføjmedlem")
    public String tilføjmedlem(Model model) {
        model.addAttribute("member", new Member());

        return "tilføjmedlem";
    }

    @PostMapping("/tilføjmedlem")
    public String tilføjmedlem(@ModelAttribute Member member) throws FileNotFoundException {

        insertMember(member);
        return "redirect:/vismedlem";
    }

    @GetMapping("/redigerMedlem")
    public String redigerMedlem(@RequestParam(value = "id", defaultValue = "1") int id, Model model) {
        for (Member member : memberArray) {
            if (member.getId() == id)
                model.addAttribute("member", member);

        }
        memberID = id;
        return "redigerMedlem";
    }

    @PostMapping("/redigerMedlem")
    public String redigerMedlem(@ModelAttribute Member member) throws FileNotFoundException {
        member.setId(memberID);
        memberArray.set(memberID - 1, member);
        saveMemberToFile(memberArray);
        return "redirect:/vismedlem";
    }


    @GetMapping("/sletmedlem")
    public String deleteMember(@RequestParam(value = "id", defaultValue = "1") int id) throws FileNotFoundException {
        memberArray.remove(id - 1);
        saveMemberToFile(memberArray);
        return "redirect:/vismedlem";

    }


    public static void saveMemberToFile(ArrayList<Member> memberArray) throws FileNotFoundException {
        PrintStream ps = new PrintStream("src/main/resources/templates/Member.txt");
        String s = "";
        for (Member m : memberArray) {
            s += m.toString() + "\r\n";

        }
        ps.print(s);
        ps.close();
    }

    public ArrayList<Member> getMemberArray() throws FileNotFoundException {
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

    private void insertMember(Member member){
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;
        try{
            ps = con.prepareStatement("INSERT INTO members(member_firstName, member_lastName, member_dateOfBirth, member_CPR) VALUES(?,?,?,?)");
            ps.setString(1,member.getFirstName());
            ps.setString(2,member.getLastName());
            ps.setInt(3, member.getAge());
            ps.setString(4, member.getCPR());
            ps.setString(5, member.getAddress());
            ps.setInt(6, member.getZipcode());
            ps.setString(7, member.getCity());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }



    }

    private ArrayList<Member> selectMembers(Member member){
        ArrayList<Member> memberSelect = new ArrayList<>();
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        PreparedStatement ps = null;

        try{
            con.prepareStatement("SELECT * FROM members(member_firstName, member_lastName, member_age, member_CPR, member_id, member_kontingent) VALUES(?,?,?,?,?,?)");

            ps.setString(1, member.getFirstName());
            ps.setString(2, member.getLastName());
            ps.setInt(3, member.getAge());
            ps.setString(4, member.getCPR());
            ps.setInt(5, member.getId());
            ps.setInt(6, member.getKontingent());

        }catch(SQLException e){
            e.printStackTrace();
        }
        return memberSelect;
    }

}

