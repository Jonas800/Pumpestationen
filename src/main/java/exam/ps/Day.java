package exam.ps;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Day {

    private int dayOfWeek;
    private String nameOfDay;
    private String nameOfMonth;
    private int year;
    private int weekNumber;
    private Date date = new Date();
    private ArrayList<EmployeeAndJob> employeeAndJobs = new ArrayList<>();

    public Day(){}

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getNameOfDay() {
        return nameOfDay;
    }

    public void setNameOfDay(String nameOfDay) {
        this.nameOfDay = nameOfDay;
    }

    public String getNameOfMonth() {
        return nameOfMonth;
    }

    public void setNameOfMonth(String nameOfMonth) {
        this.nameOfMonth = nameOfMonth;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<EmployeeAndJob> getEmployeeAndJobs() {
        return employeeAndJobs;
    }

    public void setEmployeeAndJobs(ArrayList<EmployeeAndJob> employeeAndJobs) {
        for (EmployeeAndJob eaj:employeeAndJobs) {
            if(dateEquals(this.getDate(), eaj.getDate())) {
                this.employeeAndJobs.add(eaj);
            }
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public boolean dateEquals(Date date1, Date date2){

        if (date1.getYear() == date2.getYear()) {
            if (date1.getMonth() == date2.getMonth()) {
                if (date1.getDate() == date2.getDate()) {
                    return true;
                }
            }
        }

        return false;
    }
}
