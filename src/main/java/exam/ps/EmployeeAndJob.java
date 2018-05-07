package exam.ps;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeAndJob {

    private Job job;
    private Employee employee;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date = new Date();
    private String timeOfDay;


    public EmployeeAndJob() {
    }

    public EmployeeAndJob(Job job, Employee employee, String dateString, String timeOfDay) {
        this.job = job;
        this.employee = employee;
        this.timeOfDay = timeOfDay;
        setDateWithString(dateString);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDateWithString(String dateString) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = df.parse(dateString);
        }
        catch(ParseException pe){

        }
        setDate(date);

    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }
}
