package exam.ps;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class EmployeeController {
ArrayList<Employee> employeeArrayList = new ArrayList<>();

@GetMapping("/showEMP")
public String showEmp() {


    return "redirect/showEMP";

}

@GetMapping("/CreateEmployes")
    public String createEmpl (Model model) {
    model.addAttribute("Employee",new Employee());
    employeeArrayList.add(new Employee());

    return "CreateEmployes";
}
@PostMapping("/CreateEmployes")
    public String createEmpl (@ModelAttribute Employee employee) {


    return "redirect/Employes";
}

@GetMapping("/editEMPl")
public String editEmpl(){


    return "redirect/editEMPl";
}
@GetMapping("/delEmp")
    public String delEMP(){

    return"redirect/delEM";
}

}
