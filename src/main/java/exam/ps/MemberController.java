package exam.ps;
import java.util.*;

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
        int ID = memberArray.size() + 1;
        member.setId(ID);
        memberArray.add(member);
        saveMemberToFile(memberArray);
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
}

