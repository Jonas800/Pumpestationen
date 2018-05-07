package exam.ps;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

@Controller
public class EmployeeController {
ArrayList<Employee> employeeArrayList = new ArrayList<>();
int employeid=0;

@GetMapping("/VisMedarbejdere")
public String VisMedarbejdere(Model model) {
    model.addAttribute("EmployeeArrayList", employeeArrayList);


    return "VisMedarbejdere";

}

@GetMapping("/Opretmedarbejdere")
    public String Opretmedarbjedere (Model model) {
    model.addAttribute("employee",new Employee());



     return "Opretmedarbejdere";
}
@PostMapping("/Opretmedarbejdere")
    public String Opretmedarbjedere (@ModelAttribute Employee employee) throws FileNotFoundException {
    int id=employeeArrayList.size()+1;
    employee.setID(id);
     employeeArrayList.add(employee);
     saveEmployee(employeeArrayList);
     return "redirect:/VisMedarbejdere";


}

@GetMapping("/redigerAnsat")
public String redigerAnsat(@RequestParam( value = "ID", defaultValue = "1") int ID, Model model){
    employeid = ID;
    if (model != null) {
        model.addAttribute("EmployeeArrayList", employeeArrayList.get(ID - 1));
    }


    return "redigerAnsat";
}

@PostMapping("/redigerAnsat")
public String redigerAnsat () {

    return "redirect:/VisMedarbjedere";
}
@PostMapping("/editEMP")
public String SletMedarbejder(@ModelAttribute Employee employee) {
        employee.setID(employeid);
        employeeArrayList.set(employeid-1,employee);
        return "redirect:/Medarbejdere";

    }
@GetMapping("/SletMedarbejder")
    public String SletMedarbejder(@RequestParam(value = "id",defaultValue = "1")  int id){
    employeeArrayList.remove(id-1);
    return"SletMedarbejder";
}
    public static void saveEmployee(ArrayList<Employee> employeeArrayList) throws FileNotFoundException {
        PrintStream ps = new PrintStream(new File("src/main/resources/templates/Employee.txt"));
        String s = "";
        for (Employee E : employeeArrayList) {
            s += E.toString();

        }
        ps.print(s);
        ps.close();
    }

}
