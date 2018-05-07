package exam.ps;
import java.util.*;

import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.*;
@Controller
public class MemberController {
    ArrayList<Member> memberArray = new ArrayList<>();
    int memberID = 0;

    public MemberController() throws FileNotFoundException {

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
        int ID = memberArray.size() + 1;
        member.setId(ID);
        memberArray.add(member);
        saveMemberToFile(memberArray);
        return "redirect:/vismedlem";
    }

    @GetMapping("/redigermedlem")
    public String editMember(@RequestParam(value = "ID", defaultValue = "1") int ID, Model model) {
        if (model != null) {
            model.addAttribute("medlem", memberArray.get((ID) - 1));

        }
        memberID = ID;
        return "redirect:/editMember";
    }

    @PostMapping("/redigermedlem")
    public String editMember(@ModelAttribute Member member) {
        member.setId(memberID);
        memberArray.set(memberID - 1, member);
        return "redirect:/editMember";
    }


    @GetMapping("/sletmedlem")
    public String deleteMember(@RequestParam(value = "ID", defaultValue = "0") int ID) throws FileNotFoundException {
        memberArray.remove(ID - 1);
        saveMemberToFile(memberArray);
        return "redirect:/vismedlem";

    }




    public static void saveMemberToFile(ArrayList<Member> memberArray) throws FileNotFoundException {
        PrintStream ps = new PrintStream("/src/main/resources/Member.txt");
        String s = "";
        for (Member m : memberArray) {
            s += m.toString();
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
            ArrayMember.add(member);



        }
        return ArrayMember;


    }
}