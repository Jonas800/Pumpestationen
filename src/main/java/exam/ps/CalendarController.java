package exam.ps;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CalendarController {


    //TO DO: IMPLEMENT secondary Model from DB //Week navigation//
    @GetMapping("/vagtplan")
    public static String vagtplan(@RequestParam(value = "year", defaultValue = "-1") int year, @RequestParam(value = "weekNumber", defaultValue = "-1") int weekNumber,  Model model) {
        ArrayList<Day> days = new ArrayList<>();
        ArrayList<Day> weekNavigation = new ArrayList<>();
        Day currentWeek = new Day();
        Calendar cal = getCal(year, weekNumber);
        Calendar cal2 = getCal(year, weekNumber);

        //Week navigation
        for (int i = -1; i < 3; i++) {
            if(i != 0 && i != 1) {
                cal2.add(Calendar.DAY_OF_WEEK, i * 7);
                Day firstDay = new Day();
                firstDay.setDayOfWeek(cal2.get(Calendar.DAY_OF_MONTH));
                firstDay.setNameOfMonth(cal2.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.forLanguageTag("da-DK")));
                firstDay.setNameOfDay(cal2.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.forLanguageTag("da-DK")));
                firstDay.setYear(cal2.getWeekYear());
                firstDay.setWeekNumber(cal2.get(Calendar.WEEK_OF_YEAR));
                weekNavigation.add(firstDay);
            }
        }
        //get current week
        currentWeek.setDayOfWeek(cal.get(Calendar.DAY_OF_MONTH));
        currentWeek.setNameOfMonth(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.forLanguageTag("da-DK")));
        currentWeek.setNameOfDay(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.forLanguageTag("da-DK")));
        currentWeek.setYear(cal.getWeekYear());
        currentWeek.setWeekNumber(cal.get(Calendar.WEEK_OF_YEAR));

        //Weekly schedule
        for (int i = 0; i < 7; i++) {
            Day week = new Day();
            week.setDayOfWeek(cal.get(Calendar.DAY_OF_MONTH));
            week.setNameOfMonth(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.forLanguageTag("da-DK")));
            week.setNameOfDay(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.forLanguageTag("da-DK")));
            week.setYear(cal.getWeekYear());
            week.setWeekNumber(cal.get(Calendar.WEEK_OF_YEAR));
            week.setHourOfDay(cal.get(Calendar.HOUR_OF_DAY));
            week.setMonthNumber(cal.get(Calendar.MONTH));
            //NEW
            week.setDate(cal.getTime());
            //week.setEmployeeAndJobs(dummyEmployeeAndJob());

            //OLD
            days.add(week);

            cal.add(Calendar.DAY_OF_WEEK, 1);
        }
        model.addAttribute("weekDays", days);
        model.addAttribute("weekNavigation", weekNavigation);
        model.addAttribute("currentWeek", currentWeek);
        //model.addAttribute("jobs", dummyJobs());
        //model.addAttribute("employees", dummyEmployees());
        return "vagtplan";
    }

    @GetMapping("/tilføjVagtplan")
    public String tilføjVagtplan(
            @RequestParam(value = "år", defaultValue = "-1") int year,
            @RequestParam(value = "måned", defaultValue = "-1") int month,
            @RequestParam(value = "dag", defaultValue = "-1") int day,
            @RequestParam(value = "tid", defaultValue = "") String time,
            Model model, @ModelAttribute("selectedWrapper") ScheduleWrapper selectedWrapper){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = df.parse(year + "-" + month + "-" + day);
        }
        catch(ParseException pe){

        }

        ScheduleWrapper wrapper = new ScheduleWrapper();
        wrapper.setEmployees(selectAllEmployees());
        wrapper.setJobs(Job.selectAllJobs());

        model.addAttribute("wrapper", wrapper);
        //model.addAttribute("wrapper2", new ScheduleWrapper());
        //model.addAttribute("employees", selectAllEmployees());

        //ArrayList<EmployeeAndJob> eaj = dummyEmployees(date, time);

        //model.addAttribute("employees", selectAllEmployees());
        //model.addAttribute("jobs", Job.selectAllJobs());
        //model.addAttribute("employeeAndJobs", eaj);
        return "tilføjVagtplan";
    }
//@ModelAttribute("selectedWrapper") ScheduleWrapper wrapper, @ModelAttribute ArrayList<Employee> employees, BindingResult bindingResult
    @PostMapping("/createDaySchedule")
    public String createDaySchedule(@ModelAttribute ScheduleWrapper wrapper, @RequestParam("job[][]") int[][] id){
        System.out.println(Arrays.deepToString(id));
        System.out.println("FirstHey");

        if(wrapper.getJobs().size() != 0){
            System.out.println("JobTest");
            for(Job job : wrapper.getJobs()) {
                System.out.println("" + job.getId());
            }
        }

        /*if(wrapper.getEmployees().size() != 0) {
            System.out.println("empTest");
            for (Employee employee : wrapper.getEmployees()) {
                System.out.println("ThirdHey");

                System.out.println("" + employee.getID());
            }
        }
        if(employees.size() != 0) {
            System.out.println("empTestSUPER");
            for (Employee employee : employees) {
                System.out.println("ThirdHeySUPER");

                System.out.println("" + employee.getID());
            }
        }*/
        return "redirect:/vismedlem";
    }

    private static Calendar getCal(int year, int weekNumber) {
        Calendar cal = new GregorianCalendar(Locale.forLanguageTag("da-DK"));

        if(year <= 0 && weekNumber <= 0){
            cal.setTime(new Date());
        }
        else{
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.WEEK_OF_YEAR, weekNumber);
        }
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

        return cal;
    }

    public ArrayList<Employee> selectAllEmployees() {
        dbConn db = dbConn.getInstance();
        Connection con = db.createConnection();
        Statement s = null;
        ArrayList<Employee> allEmployees = new ArrayList<>();
        try {
            s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT *  FROM employees INNER JOIN zipcodes ON zipcode = zipcodes_zipcode ");
            while (rs.next()) {
                try {
                    Employee employee = new Employee();
                    employee.setID(rs.getInt("employee_id"));
                    employee.setFirstName(rs.getString("employee_firstName"));
                    employee.setLastName(rs.getString("employee_lastName"));
                    employee.setAddress(rs.getString("employee_address"));
                    employee.setPhoneNumber(rs.getString("employee_phone"));
                    employee.setCpr(rs.getString("employee_cpr"));
                    employee.setZipcode(rs.getInt("zipcodes_zipcode"));
                    employee.setCity(rs.getString("zipcode_city"));
                    employee.setJobPosition(rs.getString("employee_jobPosition"));
                    allEmployees.add(employee);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allEmployees;
    }
/*
    public static ArrayList<EmployeeAndJob> dummyEmployeeAndJob(){
        ArrayList<EmployeeAndJob> eaj = new ArrayList<>();

        eaj.add(new EmployeeAndJob(new Job(1, "Kok"),new Employee(1, "Jens", "Jensen"), "2018-04-30"));
        eaj.add(new EmployeeAndJob(new Job(2, "Vagt"),new Employee(5, "Hans", "Jensen"), "2018-04-29"));
        eaj.add(new EmployeeAndJob(new Job(3, "Vagt3"),new Employee(2, "Bent", "Jensen"), "2018-05-02"));
        eaj.add(new EmployeeAndJob(new Job(4, "Vagt4"),new Employee(3, "Ole", "Jensen"), "2018-04-30"));


        return eaj;
    }
*/

    public static ArrayList<Job> dummyJobs(){
        ArrayList<Job> jobs = new ArrayList<>();

        jobs.add(new Job(1, "Kok"));
        jobs.add(new Job(2, "Vagt"));
        jobs.add(new Job(3, "Job3"));

        return jobs;
    }
    public static ArrayList<EmployeeAndJob> dummyEmployees(Date date, String timeOfDay) {
        ArrayList<EmployeeAndJob> firstArray = new ArrayList<>();
        ArrayList<EmployeeAndJob> secondArray = new ArrayList<>();

        firstArray.add(new EmployeeAndJob(new Job(1, "Kok"), new Employee(1, "Jens", "Jensen"), "2018-04-30", "Formiddag"));
        firstArray.add(new EmployeeAndJob(new Job(2, "Vagt"), new Employee(5, "Hans", "Jensen"), "2018-04-29", "Eftermiddag"));
        firstArray.add(new EmployeeAndJob(new Job(3, "Vagt3"), new Employee(2, "Bent", "Jensen"), "2018-05-02", "Formiddag"));

        for (EmployeeAndJob eaj : firstArray) {
            //if(commonMethods.dateEquals(eaj.getDate(), date) && eaj.getTimeOfDay().equals(timeOfDay)){
            secondArray.add(eaj);
            //}
        }
        return secondArray;
    }
}
