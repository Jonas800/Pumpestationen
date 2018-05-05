package exam.ps;
import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {
    ArrayList<Member> memberArray = new ArrayList<>();
    int memberID = 0;
    public MemberController(){

    }
    @GetMapping("/tilføjMedlem")
    public String Member(Model model){
        model.addAttribute("member", new Member());
        return "tilføjMedlem";
    }

    @PostMapping("createMember")
    public String createMember(@ModelAttribute Member member){
        int ID = memberArray.size() +1;
        member.setId(ID);
        memberArray.add(member);
        return "createMember";
    }

    @GetMapping("/editMember")
    public String editMember(@RequestParam(value = "ID", defaultValue = "1")int ID, Model model){
        if(model != null) {
            model.addAttribute("medlem", memberArray.get((ID) - 1));

        }
        memberID = ID;
        return "editMember";
    }

    @PostMapping("editMember")
    public String editMember(@ModelAttribute Member member){
        member.setId(memberID);
        memberArray.set(memberID -1, member);
        return "editMember";
    }

    @GetMapping("deleteMember")
    public String deleteMember(Model model){

        return "deleteMember";
    }

    @PostMapping("deleteMember")
    public String deleteMember(@ModelAttribute Member member){

        return "deleteMember";
    }
    @GetMapping("showMember")
    public String showMember(Model model){

        return "showMember";
    }

    @PostMapping("showMember")
    public String showMember(@ModelAttribute Member member){
        for (int i = 0; i <memberArray.size() ; i++) {

        }


        return "showMember";
    }
}
