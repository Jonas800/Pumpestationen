package exam.ps;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CalendarController {
    //TO DO: IMPLEMENT secondary Model from DB //Week navigation//
    @GetMapping("/vagtplan")
    public static String vagtplan(@RequestParam(value = "year", defaultValue = "-1") int year, @RequestParam(value = "weekNumber", defaultValue = "-1") int weekNumber,  Model model, Model model2, Model model3, Model model4) {
        ArrayList<Day> weekDays = new ArrayList<>();
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
        for (int i = 0; i < 14; i++) {
            Day week = new Day();
            week.setDayOfWeek(cal.get(Calendar.DAY_OF_MONTH));
            week.setNameOfMonth(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.forLanguageTag("da-DK")));
            week.setNameOfDay(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.forLanguageTag("da-DK")));
            week.setYear(cal.getWeekYear());
            week.setWeekNumber(cal.get(Calendar.WEEK_OF_YEAR));

            //NEW
            week.setDate(cal.getTime());
            week.setEmployeeAndJobs(dummyEmployeeAndJob());

            //OLD
            weekDays.add(week);

            cal.add(Calendar.HOUR_OF_DAY, 12);
        }
        model.addAttribute("weekDays", weekDays);
        model2.addAttribute("weekNavigation", weekNavigation);
        model3.addAttribute("currentWeek", currentWeek);
        model4.addAttribute("jobs", dummyJobs());
        return "vagtplan";
    }

    private static Calendar getCal(int year, int weekNumber) {
        Calendar cal = new GregorianCalendar(Locale.forLanguageTag("da-DK"));

        if(year <= 0 && weekNumber <= 0){
            cal.setTime(new Date());
        }
        else{
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.WEEK_OF_YEAR, weekNumber);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

        return cal;
    }
    public static ArrayList<EmployeeAndJob> dummyEmployeeAndJob(){
        ArrayList<EmployeeAndJob> eaj = new ArrayList<>();

        eaj.add(new EmployeeAndJob(1, "Hans", 1, "Kok", "2018-04-30"));
        eaj.add(new EmployeeAndJob(2, "Bent", 6, "Vagt", "2018-04-29"));
        eaj.add(new EmployeeAndJob(3, "Kim", 7, "Yakuza", "2018-05-02"));
        eaj.add(new EmployeeAndJob(4, "Henrik", 8, "Batman", "2018-04-30"));


        return eaj;
    }

    public static ArrayList<Job> dummyJobs(){
        ArrayList<Job> jobs = new ArrayList<>();

        jobs.add(new Job(1, "Kok"));
        jobs.add(new Job(2, "Vagt"));
        jobs.add(new Job(3, "Job3"));

        return jobs;
    }

    /*public static ArrayList<Job> dummyEmployees(){
        ArrayList<Employee> employees = new ArrayList<>();

        employees.add(new Employee(1, "Jens", "Jensen"));
        employees.add(new Employee(2, "Bent", "Hansen"));
        employees.add(new Employee(3, "Kim", "Olsen"));

        return employees;
    }
    */
}
